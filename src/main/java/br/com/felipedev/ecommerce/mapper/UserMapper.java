package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.user.UserResponseDTO;
import br.com.felipedev.ecommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    @Autowired
    private RoleMapper roleMapper;

    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), "******", roleMapper.toResponseDTOList(user.getRoles()));
    }

    public List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return users.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
