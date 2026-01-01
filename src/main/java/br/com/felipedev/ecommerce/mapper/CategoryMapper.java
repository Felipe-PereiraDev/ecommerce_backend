package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.category.CategoryResponseDTO;
import br.com.felipedev.ecommerce.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "seller.id", target = "sellerId")
    CategoryResponseDTO toResponseDTO(Category category);

    List<CategoryResponseDTO> toResponseDTOList(List<Category> categoryList);
}
