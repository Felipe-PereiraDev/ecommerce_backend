package br.com.felipedev.ecommerce.dto.category;

public record CategoryResponseDTO(
        Long id,
        String description,
        Long sellerId
) {
}
