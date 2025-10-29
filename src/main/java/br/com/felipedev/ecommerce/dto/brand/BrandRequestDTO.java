package br.com.felipedev.ecommerce.dto.brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandRequestDTO(
        @NotBlank
        @Size(min = 2, max = 50)
        String description
) {
}
