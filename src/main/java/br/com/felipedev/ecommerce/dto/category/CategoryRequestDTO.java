package br.com.felipedev.ecommerce.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        @NotBlank
        @Size(min = 2, max = 50)
        String description
) {
}
