package br.com.felipedev.ecommerce.dto.user;

import br.com.felipedev.ecommerce.dto.role.RoleResponseDTO;

import java.time.LocalDate;
import java.util.List;

public record UserPFResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        LocalDate dateOfBirth,
        List<RoleResponseDTO> roles
) {
}
