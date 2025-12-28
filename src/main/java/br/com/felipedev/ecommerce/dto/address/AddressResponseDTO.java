package br.com.felipedev.ecommerce.dto.address;

public record AddressResponseDTO(
        Long id,
        Long personId,
        String zipCode,
        String street,
        String number,
        String complement,
        String neighborhood,
        String state,
        String city,
        String addressType
) {
}
