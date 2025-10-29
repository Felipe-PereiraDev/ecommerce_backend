package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.role.RoleResponseDTO;
import br.com.felipedev.ecommerce.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleMapper {

    public RoleResponseDTO toRoleResponseDTO(Role role) {
        return new RoleResponseDTO(role.getId(), role.getRole().name());
    }

    public List<RoleResponseDTO> toRoleResponseDTOs(List<Role> role) {
        return role.stream()
                .map(this::toRoleResponseDTO)
                .toList();
    }
}
