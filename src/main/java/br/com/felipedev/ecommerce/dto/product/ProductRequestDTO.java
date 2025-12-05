package br.com.felipedev.ecommerce.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank @Length(max = 100)
        String name,
        @NotNull @Positive
        BigDecimal price,
        @Length(max = 50)
        String unitType,
        @NotBlank @Length(max = 2000)
        String description,
        @NotNull @Positive
        Double weight,
        @NotNull @Positive
        Double width,
        @NotNull @Positive
        Double height,
        @NotNull @Positive
        Double depth,
        @NotNull @Positive
        Integer stockQuantity,
        @Positive
        Integer stockAlertQuantity,
        @Length(max = 1000)
        String youtubeLink,
        Long brandId,
        Long categoryId
) {
    
}
