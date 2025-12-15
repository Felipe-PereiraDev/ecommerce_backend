package br.com.felipedev.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UserPJRequestDTO(
        @NotBlank @Length(min = 5, max = 100)
        String name,
        @NotBlank @Length(min = 10, max = 20)
        String phone,
        @NotBlank @Length(min = 6, max = 50)
        String password,
        @Email @NotBlank
        String email,
        @NotBlank @Length(min = 14, max = 14) @CNPJ
        String cnpj,
        @NotBlank
        String category,
        @NotBlank
        String corporate_name,
        @NotBlank
        String municipal_registration,
        @NotBlank
        String state_registration,
        @NotBlank
        String trade_name
) {
}
