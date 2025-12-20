package br.com.felipedev.ecommerce.service;


import br.com.felipedev.ecommerce.dto.category.CategoryRequestDTO;
import br.com.felipedev.ecommerce.dto.category.CategoryResponseDTO;
import br.com.felipedev.ecommerce.exception.DescriptionExistsException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.exception.UnprocessableEntityException;
import br.com.felipedev.ecommerce.mapper.CategoryMapper;
import br.com.felipedev.ecommerce.model.Category;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private PersonJuridicaService personJuridicaService;

    public CategoryResponseDTO create(CategoryRequestDTO request) {
        Long selleIdId = personJuridicaService.getIdAuthenticatedPersonJuridica();
        PersonJuridica selleId = personJuridicaService.findById(selleIdId);

        if (categoryRepository.existsByDescriptionAndSellerId(request.description(), selleIdId)) {
            throw new DescriptionExistsException("The category %s already exists".formatted(request.description()));
        }

        Category category = new Category(null, request.description());
        category.setSeller(selleId);
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
        Long selleId = personJuridicaService.getIdAuthenticatedPersonJuridica();
        Category category = findById(id);
        if (!personJuridicaService.hasSellerOwnership(selleId, category.getSeller().getId())) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }

        if (categoryRepository.existsByDescriptionAndSellerId(request.description(), selleId)) {
            throw new DescriptionExistsException("The category %s already exists".formatted(request.description()));
        }

        category.setDescription(request.description());
        categoryRepository.save(category);
        return categoryMapper.toResponseDTO(category);
    }


    public void deleteById(Long id) {
        Long selleId = personJuridicaService.getIdAuthenticatedPersonJuridica();
        Category category = findById(id);

        if (!personJuridicaService.hasSellerOwnership(selleId, category.getSeller().getId())) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }

        if (categoryRepository.hasProductsAssociated(id)) {
            throw new UnprocessableEntityException("It is not possible to delete the category with id %d".formatted(id));
        }
        categoryRepository.delete(category);
    }




}
