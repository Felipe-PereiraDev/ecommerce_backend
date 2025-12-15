package br.com.felipedev.ecommerce.dto.person.juridica;

public record PersonJuridicaResponseDTO(
        String name,
        String phone,
        String cnpj,
        String category,
        String corporate_name,
        String municipal_registration,
        String state_registration,
        String trade_name
) {
}
