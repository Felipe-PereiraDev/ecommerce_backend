package br.com.felipedev.ecommerce.dto.address;

import br.com.felipedev.ecommerce.enums.AddressType;

public record AddressRequestDTO(
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
