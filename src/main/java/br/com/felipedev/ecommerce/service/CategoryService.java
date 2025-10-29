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
import java.util.Optional;

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
        return categoryMapper.toCategoryResponseDTO(category);
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryMapper.toCategoryResponseDTOs(categoryRepository.findAll());
    }

    public CategoryResponseDTO updateDescription(Long id, CategoryRequestDTO request) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new EntityNotFoundException("The category with id %d not exists".formatted(id));
        }

        if (categoryRepository.existsByDescription(request.description())) {
            throw new DescriptionExistsException("The category %s already exists".formatted(request.description()));
        }

        category.get().setDescription(request.description());
        return categoryMapper.toCategoryResponseDTO(category.get());
    }


    public void deleteById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new EntityNotFoundException("The category with id %d not exists".formatted(id));
        }
        categoryRepository.delete(category.get());
    }


}
