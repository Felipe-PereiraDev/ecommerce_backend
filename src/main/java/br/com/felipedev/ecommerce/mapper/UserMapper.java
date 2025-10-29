package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.brand.BrandResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserResponseDTO;
import br.com.felipedev.ecommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    @Autowired
    private RoleMapper roleMapper;

    public UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), "******", roleMapper.toRoleResponseDTOs(user.getRoles()));
    }

    public List<UserResponseDTO> toUserResponseDTOs(List<User> users) {
        return users.stream()
                .map(this::toUserResponseDTO)
                .toList();
    }
}
