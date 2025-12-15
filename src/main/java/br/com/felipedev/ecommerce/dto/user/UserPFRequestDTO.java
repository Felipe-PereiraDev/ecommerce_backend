package br.com.felipedev.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UserPFRequestDTO(
        @NotBlank @Length(min = 6, max = 50)
        String password,
        @NotBlank @Length(min = 5, max = 100)
        String name,
        @Email @NotBlank
        String email,
        @NotBlank @Length(min = 10, max = 20)
        String phone,
        @NotBlank @Length(min = 11, max = 11) @CPF
        String cpf,
        @Past
        LocalDate dateOfBirth
) {
}
