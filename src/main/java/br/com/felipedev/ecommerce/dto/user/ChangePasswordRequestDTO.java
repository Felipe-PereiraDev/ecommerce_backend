package br.com.felipedev.ecommerce.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequestDTO(

        @NotBlank
        String currentPassword,
        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])\\S{6,}$",
                message = "Password must be at least 6 characters long and include uppercase, lowercase, number and special character. Spaces are not allowed."
        )
        String newPassword
) {
}
