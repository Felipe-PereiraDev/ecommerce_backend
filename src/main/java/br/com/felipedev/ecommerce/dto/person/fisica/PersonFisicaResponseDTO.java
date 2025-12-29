package br.com.felipedev.ecommerce.dto.person.fisica;

import br.com.felipedev.ecommerce.dto.address.AddressResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record PersonFisicaResponseDTO(
        String cpf,
        String name,
        String email,
        String phone,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth,
        List<AddressResponseDTO> addresses
) {
}
