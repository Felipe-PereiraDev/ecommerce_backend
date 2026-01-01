package br.com.felipedev.ecommerce.dto.person.juridica;

import br.com.felipedev.ecommerce.dto.address.AddressResponseDTO;

import java.util.List;

public record PersonJuridicaResponseDTO(
        String name,
        String email,
        String phone,
        String cnpj,
        String category,
        String corporateName,
        String municipalRegistration,
        String stateRegistration,
        String tradeName,
        List<AddressResponseDTO> addresses
) {
}
