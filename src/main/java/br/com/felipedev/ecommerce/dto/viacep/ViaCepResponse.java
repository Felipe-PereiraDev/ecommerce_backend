package br.com.felipedev.ecommerce.dto.viacep;

public record ViaCepResponse(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String estado
) {
}
