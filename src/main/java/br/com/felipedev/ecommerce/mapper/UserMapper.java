package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.user.UserPFRequestDTO;
import br.com.felipedev.ecommerce.dto.user.UserPFResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserPJResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserResponseDTO;
import br.com.felipedev.ecommerce.model.Person;
import br.com.felipedev.ecommerce.model.PersonFisica;
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


    public UserPFResponseDTO toResponseDTO(User user, PersonFisica person) {
        return new UserPFResponseDTO(
                user.getId(),
                person.getName(),
                user.getEmail(),
                person.getPhone(),
                person.getCpf(),
                person.getDateOfBirth(),
                user.getStatus(),
                roleMapper.toResponseDTOList(user.getRoles())
        );
    }

    public UserPJResponseDTO toResponseDTO(User user, PersonJuridica person) {
        return new UserPJResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getStatus(),
                roleMapper.toResponseDTOList(user.getRoles()),
                personJuridicaMapper.toResponse(person)
        );
    }

}
