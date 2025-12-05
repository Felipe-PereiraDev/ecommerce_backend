package br.com.felipedev.ecommerce.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ProductUpdateDTO(
        @Length(max = 100)
        String name,
        @Positive
        BigDecimal price,
        @Length(max = 50)
        String unitType,
        @Length(max = 2000)
        String description,
        @Positive
        Double weight,
        @Positive
        Double width,
        @Positive
        Double height,
        @Positive
        Double depth,
        @Positive
        Integer stockQuantity,
        @Positive
        Integer stockAlertQuantity,
        @Length(max = 1000)
        String youtubeLink,
        Long brandId,
        Long categoryId
) {
    
}
