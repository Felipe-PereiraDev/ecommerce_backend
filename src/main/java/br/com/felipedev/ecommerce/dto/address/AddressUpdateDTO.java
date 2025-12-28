package br.com.felipedev.ecommerce.dto.address;

public record AddressUpdateDTO(
        String zipCode,
        String street,
        String number,
        String complement,
        String neighborhood,
        String state,
        String city
) {
}
