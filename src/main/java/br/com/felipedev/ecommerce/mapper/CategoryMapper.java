package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.category.CategoryResponseDTO;
import br.com.felipedev.ecommerce.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public CategoryResponseDTO toResponseDTO(Category category) {
        return new CategoryResponseDTO(category.getId(), category.getDescription());
    }

    public List<CategoryResponseDTO> toResponseDTOList(List<Category> categoryList) {
        return categoryList.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
