package br.com.felipedev.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UserPJRequestDTO(
        @NotBlank @Length(min = 5, max = 100)
        String name,
        @NotBlank @Length(min = 10, max = 20)
        String phone,
        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])\\S{6,}$",
                message = "Password must be at least 6 characters long and include uppercase, lowercase, number and special character. Spaces are not allowed."
        )
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
