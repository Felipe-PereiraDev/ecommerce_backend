package br.com.felipedev.ecommerce.service;


import br.com.felipedev.ecommerce.config.security.UserContextService;
import br.com.felipedev.ecommerce.dto.category.CategoryRequestDTO;
import br.com.felipedev.ecommerce.dto.category.CategoryResponseDTO;
import br.com.felipedev.ecommerce.exception.DescriptionExistsException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.exception.UnprocessableEntityException;
import br.com.felipedev.ecommerce.mapper.CategoryMapper;
import br.com.felipedev.ecommerce.model.Category;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final PersonJuridicaService personJuridicaService;
    private final UserContextService userContextService;

    public CategoryResponseDTO create(CategoryRequestDTO request) {
        Long selleIdId = userContextService.getAuthenticatedSellerId();
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
        Category category = findById(id);
        userContextService.ensureSellerOwnsResource(category.getSeller().getId());

        if (categoryRepository.existsByDescriptionAndSellerId(request.description(), category.getSeller().getId())) {
            throw new DescriptionExistsException("The category %s already exists".formatted(request.description()));
        }

        category.setDescription(request.description());
        categoryRepository.save(category);
        return categoryMapper.toResponseDTO(category);
    }


    public void deleteById(Long id) {
        Category category = findById(id);
        userContextService.ensureSellerOwnsResource(category.getSeller().getId());

        if (categoryRepository.hasProductsAssociated(id)) {
            throw new UnprocessableEntityException("It is not possible to delete the category with id %d".formatted(id));
        }
        categoryRepository.delete(category);
    }




}
