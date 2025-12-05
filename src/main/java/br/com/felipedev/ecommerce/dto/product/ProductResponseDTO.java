package br.com.felipedev.ecommerce.dto.product;

import br.com.felipedev.ecommerce.dto.brand.BrandResponseDTO;
import br.com.felipedev.ecommerce.dto.category.CategoryResponseDTO;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        BigDecimal price,
        String unitType,
        String description,
        Double weight,
        Double width,
        Double height,
        Double depth,
        Integer stockQuantity,
        Integer stockAlertQuantity,
        String youtubeLink,
        BrandResponseDTO brand,
        CategoryResponseDTO category
) {
    
}
