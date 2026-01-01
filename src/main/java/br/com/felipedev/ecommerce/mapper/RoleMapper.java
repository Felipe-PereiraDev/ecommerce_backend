package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.role.RoleResponseDTO;
import br.com.felipedev.ecommerce.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponseDTO toResponseDTO(Role role);

    List<RoleResponseDTO> toResponseDTOList(List<Role> role);
}
