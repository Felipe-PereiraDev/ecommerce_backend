package br.com.felipedev.ecommerce.dto.user;

import br.com.felipedev.ecommerce.dto.role.RoleResponseDTO;

import java.util.List;

public record UserResponseDTO(
        Long id,
        String username,
        String password,
        List<RoleResponseDTO> roles
) {
}
