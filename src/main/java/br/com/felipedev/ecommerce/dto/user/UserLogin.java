package br.com.felipedev.ecommerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserLogin(
        @NotBlank @Length(min = 2, max = 30)
        String email,
        @NotBlank @Length(min = 6, max = 50)
        String password
) {
}
