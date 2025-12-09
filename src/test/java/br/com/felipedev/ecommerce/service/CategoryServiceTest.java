package br.com.felipedev.ecommerce.service;


import br.com.felipedev.ecommerce.dto.category.CategoryRequestDTO;
import br.com.felipedev.ecommerce.dto.category.CategoryResponseDTO;
import br.com.felipedev.ecommerce.exception.DescriptionExistsException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.mapper.CategoryMapper;
import br.com.felipedev.ecommerce.model.Category;
import br.com.felipedev.ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryMapper categoryMapper;

    @InjectMocks
    CategoryService categoryService;

    Category category;
    
    CategoryResponseDTO categoryResponseDTO;


    @BeforeEach
    void setUp() {
        category = new Category(1L, "ELECTRONICS");
        categoryResponseDTO = new CategoryResponseDTO(category.getId(), category.getDescription());
    }

    @Test
    void test_create_WhenCategoryDoesNotExist_ShouldCreateCategory() {
        CategoryRequestDTO request = new CategoryRequestDTO("ELECTRONICS");
        when(categoryRepository.existsByDescription(anyString())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toResponseDTO(any(Category.class))).thenReturn(categoryResponseDTO);

        var result = categoryService.create(request);

        assertEquals(category.getId(), result.id(),
                () -> String.format("O Resultado esperado era %d, mas veio %d", category.getId(), result.id()));
        assertEquals(category.getDescription(), result.description(),
                () -> String.format("O Resultado esperado era %s, mas veio %s", category.getDescription(), result.description()));
    }

    @Test
    void test_create_WhenCategoryAlreadyExist_ShouldThrowDescriptionExistsException() {
        CategoryRequestDTO request = new CategoryRequestDTO("ELECTRONICS");
        String expectedMessage = String.format("The category %s already exists", request.description());

        when(categoryRepository.existsByDescription(anyString())).thenReturn(true);

        Exception result = assertThrowsExactly(DescriptionExistsException.class,
                () -> categoryService.create(request));

        assertEquals(expectedMessage, result.getMessage());
        verify(categoryRepository, never()).save(any(Category.class));
        verifyNoInteractions(categoryMapper);
    }

    @Test
    void test_findById_WhenCategoryIdExists_ShouldTrow() {
        String expectedDescription = "ELECTRONICS";
        Long expectedId = 1L;

        when(categoryRepository.findById(expectedId)).thenReturn(Optional.of(category));

        Category result = categoryService.findById(expectedId);

        assertEquals(expectedId, result.getId());
        assertEquals(expectedDescription, result.getDescription());
    }

    @Test
    void test_findById_WhenCategoryIdDoesNotExist_ShouldThrowEntityNotFoundException() {
        Long expectedId = 1L;
        String expectedMessage = String.format("The category with id %d not exists", expectedId);

        when(categoryRepository.findById(expectedId)).thenReturn(Optional.empty());

        Exception result = assertThrowsExactly(EntityNotFoundException.class,
                () -> categoryService.findById(expectedId));


        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void test_findAll_WhenCalled_ShouldReturnCategoryResponseDTOList() {
        List<CategoryResponseDTO> categoryResponseList = List.of(
                categoryResponseDTO,
                new CategoryResponseDTO(2L, "CLOTHING"),
                new CategoryResponseDTO(3L, "HOME")
        );

        List<Category> categoryList = List.of(
                category,
                new Category(2L, "CLOTHING"),
                new Category(3L, "HOME")
        );
        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(categoryMapper.toResponseDTOList(eq(categoryList))).thenReturn(categoryResponseList);

        List<CategoryResponseDTO> resultList = categoryService.findAll();

        assertEquals(categoryResponseList.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            CategoryResponseDTO result = resultList.get(i);
            CategoryResponseDTO categoryResponse = categoryResponseList.get(i);
            assertNotNull(result);
            assertEquals(categoryResponse.description(), result.description());
            assertEquals(categoryResponse.id(), result.id());
        }
        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, times(1)).toResponseDTOList(categoryList);
        verifyNoMoreInteractions(categoryRepository, categoryMapper);
    }

    @Test
    void test_updateDescription_WhenCategoryIdExists_ShouldReturnUpdatedCategoryResponseDTO() {
        String expectedCategoryName = "CLOTHING";
        categoryResponseDTO = new CategoryResponseDTO(category.getId(), expectedCategoryName);
        CategoryRequestDTO request = new CategoryRequestDTO("CLOTHING");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByDescription(anyString())).thenReturn(false);
        when(categoryMapper.toResponseDTO(any(Category.class))).thenReturn(categoryResponseDTO);

        CategoryResponseDTO result = categoryService.updateDescription(1L, request);

        assertNotNull(result);
        assertEquals(category.getId(), result.id());
        assertEquals(expectedCategoryName, result.description());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).existsByDescription(expectedCategoryName);
        verify(categoryMapper, times(1)).toResponseDTO(category);
    }

    @Test
    void test_updateDescription_WhenCategoryIdDoesNotExist_ShouldThrowEntityNotFoundException() {
        Long expectedId = 1L;
        String expectedMessage = String.format("The category with id %d not exists", expectedId);
        CategoryRequestDTO request = new CategoryRequestDTO("CLOTHING");
        when(categoryRepository.findById(expectedId)).thenReturn(Optional.empty());

        Exception result = assertThrowsExactly(EntityNotFoundException.class,
                () -> categoryService.updateDescription(expectedId, request));


        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void test_updateDescription_WhenCategoryDescriptionAlreadyExists_ShouldThrowDescriptionExistsException() {
        Long expectedId = 1L;
        CategoryRequestDTO request = new CategoryRequestDTO("CLOTHING");
        String expectedMessage = String.format("The category %s already exists", request.description());
        when(categoryRepository.findById(expectedId)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByDescription(anyString())).thenReturn(true);

        Exception result = assertThrowsExactly(DescriptionExistsException.class,
                () -> categoryService.updateDescription(expectedId, request));


        assertEquals(expectedMessage, result.getMessage());
        verify(categoryMapper, never()).toResponseDTO(category);
    }


    @Test
    void test_deleteById_WhenCategoryIdExists_ShouldDeleteCategory() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(eq(category));

        categoryService.deleteById(id);

        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, times(1)).delete(category);
    }
    @Test
    void test_deleteById_WhenCategoryIdDoesNotExist_ShouldThrowEntityNotFoundException() {
        Long expectedId = 1L;
        String expectedMessage = String.format("The category with id %d not exists", expectedId);

        when(categoryRepository.findById(expectedId)).thenReturn(Optional.empty());

        Exception result = assertThrowsExactly(EntityNotFoundException.class,
                () -> categoryService.deleteById(expectedId));


        assertEquals(expectedMessage, result.getMessage());
        verify(categoryRepository, never()).delete(category);
    }
}