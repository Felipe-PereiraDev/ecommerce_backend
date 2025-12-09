package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.dto.brand.BrandRequestDTO;
import br.com.felipedev.ecommerce.dto.brand.BrandResponseDTO;
import br.com.felipedev.ecommerce.exception.DescriptionExistsException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.mapper.BrandMapper;
import br.com.felipedev.ecommerce.model.Brand;
import br.com.felipedev.ecommerce.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    BrandRepository brandRepository;

    @Mock
    BrandMapper brandMapper;

    @InjectMocks
    BrandService brandService;

    Brand brand;
    BrandResponseDTO brandResponseDTO;


    @BeforeEach
    void setUp() {
        brand = new Brand(1L, "ADIDAS");
        brandResponseDTO = new BrandResponseDTO(brand.getId(), brand.getDescription());
    }

    @Test
    void test_create_WhenBrandDoesNotExist_ShouldCreateBrand() {
        BrandRequestDTO request = new BrandRequestDTO("ADIDAS");
        when(brandRepository.existsByDescription(anyString())).thenReturn(false);
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);
        when(brandMapper.toResponseDTO(any(Brand.class))).thenReturn(brandResponseDTO);

        var result = brandService.create(request);

        assertEquals(brand.getId(), result.id(),
                () -> String.format("O Resultado esperado era %d, mas veio %d", brand.getId(), result.id()));
        assertEquals(brand.getDescription(), result.description(),
                () -> String.format("O Resultado esperado era %s, mas veio %s", brand.getDescription(), result.description()));
    }

    @Test
    void test_create_WhenBrandAlreadyExist_ShouldThrowDescriptionExistsException() {
        BrandRequestDTO request = new BrandRequestDTO("ADIDAS");
        String expectedMessage = String.format("The brand %s already exists", request.description());

        when(brandRepository.existsByDescription(anyString())).thenReturn(true);

        Exception result = assertThrowsExactly(DescriptionExistsException.class,
                () -> brandService.create(request));

        assertEquals(expectedMessage, result.getMessage());
        verify(brandRepository, never()).save(any(Brand.class));
        verifyNoInteractions(brandMapper);
    }

    @Test
    void test_findById_WhenBrandIdExists_ShouldTrow() {
        String expectedDescription = "ADIDAS";
        Long expectedId = 1L;

        when(brandRepository.findById(expectedId)).thenReturn(Optional.of(brand));

        Brand result = brandService.findById(expectedId);

        assertEquals(expectedId, result.getId());
        assertEquals(expectedDescription, result.getDescription());
    }

    @Test
    void test_findById_WhenBrandIdDoesNotExist_ShouldThrowEntityNotFoundException() {
        Long expectedId = 1L;
        String expectedMessage = String.format("The brand with id %d not exists", expectedId);

        when(brandRepository.findById(expectedId)).thenReturn(Optional.empty());

        Exception result = assertThrowsExactly(EntityNotFoundException.class,
                () -> brandService.findById(expectedId));


        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void test_findAll_WhenCalled_ShouldReturnBrandResponseDTOList() {
        List<BrandResponseDTO> brandResponseList = List.of(
                brandResponseDTO,
                new BrandResponseDTO(2L, "NIKE"),
                new BrandResponseDTO(3L, "NESTLE")
        );

        List<Brand> brandList = List.of(
                brand,
                new Brand(2L, "NIKE"),
                new Brand(3L, "NESTLE")
        );
        when(brandRepository.findAll()).thenReturn(brandList);
        when(brandMapper.toResponseDTOList(eq(brandList))).thenReturn(brandResponseList);

        List<BrandResponseDTO> resultList = brandService.findAll();

        assertEquals(brandResponseList.size(), resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            BrandResponseDTO result = resultList.get(i);
            BrandResponseDTO brandResponse = brandResponseList.get(i);
            assertNotNull(result);
            assertEquals(brandResponse.description(), result.description());
            assertEquals(brandResponse.id(), result.id());
        }
        verify(brandRepository, times(1)).findAll();
        verify(brandMapper, times(1)).toResponseDTOList(brandList);
        verifyNoMoreInteractions(brandRepository, brandMapper);
    }

    @Test
    void test_updateDescription_WhenBrandIdExists_ShouldReturnUpdatedBrandResponseDTO() {
        String expectedBrandName = "NIKE";
        brandResponseDTO = new BrandResponseDTO(brand.getId(), expectedBrandName);
        BrandRequestDTO request = new BrandRequestDTO("NIKE");

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(brandRepository.existsByDescription(anyString())).thenReturn(false);
        when(brandMapper.toResponseDTO(any(Brand.class))).thenReturn(brandResponseDTO);

        BrandResponseDTO result = brandService.updateDescription(1L, request);

        assertNotNull(result);
        assertEquals(brand.getId(), result.id());
        assertEquals(expectedBrandName, result.description());
        verify(brandRepository, times(1)).findById(1L);
        verify(brandRepository, times(1)).existsByDescription(expectedBrandName);
        verify(brandMapper, times(1)).toResponseDTO(brand);
    }

    @Test
    void test_updateDescription_WhenBrandIdDoesNotExist_ShouldThrowEntityNotFoundException() {
        Long expectedId = 1L;
        String expectedMessage = String.format("The brand with id %d not exists", expectedId);
        BrandRequestDTO request = new BrandRequestDTO("NIKE");
        when(brandRepository.findById(expectedId)).thenReturn(Optional.empty());

        Exception result = assertThrowsExactly(EntityNotFoundException.class,
                () -> brandService.updateDescription(expectedId, request));


        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void test_updateDescription_WhenBrandDescriptionAlreadyExists_ShouldThrowDescriptionExistsException() {
        Long expectedId = 1L;
        BrandRequestDTO request = new BrandRequestDTO("NIKE");
        String expectedMessage = String.format("The brand %s already exists", request.description());
        when(brandRepository.findById(expectedId)).thenReturn(Optional.of(brand));
        when(brandRepository.existsByDescription(anyString())).thenReturn(true);

        Exception result = assertThrowsExactly(DescriptionExistsException.class,
                () -> brandService.updateDescription(expectedId, request));


        assertEquals(expectedMessage, result.getMessage());
        verify(brandMapper, never()).toResponseDTO(brand);
    }


    @Test
    void test_deleteById_WhenBrandIdExists_ShouldDeleteBrand() {
        Long id = 1L;
        when(brandRepository.findById(id)).thenReturn(Optional.of(brand));
        doNothing().when(brandRepository).delete(eq(brand));

        brandService.deleteById(id);

        verify(brandRepository, times(1)).findById(id);
        verify(brandRepository, times(1)).delete(brand);
    }
    @Test
    void test_deleteById_WhenBrandIdDoesNotExist_ShouldThrowEntityNotFoundException() {
        Long expectedId = 1L;
        String expectedMessage = String.format("The brand with id %d not exists", expectedId);

        when(brandRepository.findById(expectedId)).thenReturn(Optional.empty());

        Exception result = assertThrowsExactly(EntityNotFoundException.class,
                () -> brandService.deleteById(expectedId));


        assertEquals(expectedMessage, result.getMessage());
        verify(brandRepository, never()).delete(brand);
    }


}