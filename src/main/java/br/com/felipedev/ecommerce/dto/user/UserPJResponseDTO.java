package br.com.felipedev.ecommerce.dto.user;

import br.com.felipedev.ecommerce.dto.person.juridica.PersonJuridicaResponseDTO;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public record UserPJResponseDTO(
        String email,
        PersonJuridicaResponseDTO personJuridica
) {
}
