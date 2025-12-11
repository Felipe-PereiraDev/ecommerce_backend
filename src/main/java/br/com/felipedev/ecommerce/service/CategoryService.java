package br.com.felipedev.ecommerce.service;


import br.com.felipedev.ecommerce.dto.category.CategoryRequestDTO;
import br.com.felipedev.ecommerce.dto.category.CategoryResponseDTO;
import br.com.felipedev.ecommerce.exception.DescriptionExistsException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.mapper.CategoryMapper;
import br.com.felipedev.ecommerce.model.Category;
import br.com.felipedev.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;


    public CategoryResponseDTO create(CategoryRequestDTO request) {
        if (categoryRepository.existsByDescription(request.description())) {
            throw new DescriptionExistsException("The category %s already exists".formatted(request.description()));
        }
        Category category = new Category(null, request.description());
        categoryRepository.save(category);
        return categoryMapper.toResponseDTO(category);
    }

    protected Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The category with id %d not exists".formatted(id)));
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryMapper.toResponseDTOList(categoryRepository.findAll());
    }

    public CategoryResponseDTO updateDescription(Long id, CategoryRequestDTO request) {
        Category category = findById(id);

        if (categoryRepository.existsByDescription(request.description())) {
            throw new DescriptionExistsException("The category %s already exists".formatted(request.description()));
        }

        category.setDescription(request.description());
        categoryRepository.save(category);
        return categoryMapper.toResponseDTO(category);
    }


    public void deleteById(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }


}
