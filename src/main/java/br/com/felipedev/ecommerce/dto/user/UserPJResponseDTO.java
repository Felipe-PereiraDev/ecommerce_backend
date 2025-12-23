package br.com.felipedev.ecommerce.dto.user;

import br.com.felipedev.ecommerce.dto.person.juridica.PersonJuridicaResponseDTO;
import br.com.felipedev.ecommerce.dto.role.RoleResponseDTO;
import br.com.felipedev.ecommerce.enums.UserStatusEnum;

import java.util.List;

public record UserPJResponseDTO(
        Long id,
        String email,
        UserStatusEnum status,
        List<RoleResponseDTO> roles,
        PersonJuridicaResponseDTO personJuridica
) {
}
