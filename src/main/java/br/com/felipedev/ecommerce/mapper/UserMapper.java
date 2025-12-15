package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.user.UserPJResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserResponseDTO;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PersonJuridicaMapper personJuridicaMapper;

    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getEmail(), "******", roleMapper.toResponseDTOList(user.getRoles()));
    }

    public List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return users.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public UserPJResponseDTO toUserPJResponse(User user, PersonJuridica person) {
        return new UserPJResponseDTO(user.getEmail(), personJuridicaMapper.toResponse(person));
    }
}
