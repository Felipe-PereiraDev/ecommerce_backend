package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.user.UserPFRequestDTO;
import br.com.felipedev.ecommerce.model.PersonFisica;
import org.springframework.stereotype.Component;

@Component
public class PersonFisicaMapper {

    public PersonFisica toEntity(UserPFRequestDTO request) {
        return new PersonFisica(
                request.email(),
                request.name(),
                request.phone(),
                request.cpf(),
                request.dateOfBirth()
        );
    }


}
