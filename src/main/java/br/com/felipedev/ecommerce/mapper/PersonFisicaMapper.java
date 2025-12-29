package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.person.fisica.PersonFisicaResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserPFRequestDTO;
import br.com.felipedev.ecommerce.model.PersonFisica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonFisicaMapper {
    private final AddressMapper addressMapper;

    public PersonFisica toEntity(UserPFRequestDTO request) {
        return new PersonFisica(
                request.name(),
                request.phone(),
                request.cpf(),
                request.dateOfBirth()
        );
    }


    public PersonFisicaResponseDTO toResponse(PersonFisica personFisica) {
        return new PersonFisicaResponseDTO(
                personFisica.getCpf(),
                personFisica.getName(),
                personFisica.getUser().getEmail(),
                personFisica.getPhone(),
                personFisica.getDateOfBirth(),
                addressMapper.toResponseList(personFisica.getAddresses())
        );
    }
}
