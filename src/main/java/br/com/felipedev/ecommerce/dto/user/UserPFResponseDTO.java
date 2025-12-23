package br.com.felipedev.ecommerce.dto.user;

import br.com.felipedev.ecommerce.dto.role.RoleResponseDTO;
import br.com.felipedev.ecommerce.enums.UserStatusEnum;

import java.time.LocalDate;
import java.util.List;

public record UserPFResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        LocalDate dateOfBirth,
        UserStatusEnum status,
        List<RoleResponseDTO> roles
) {
}
