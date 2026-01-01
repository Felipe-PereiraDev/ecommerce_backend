package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.person.fisica.PersonFisicaResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserPFRequestDTO;
import br.com.felipedev.ecommerce.model.PersonFisica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                AddressMapper.class
        }
)
public interface PersonFisicaMapper {

    @Mapping(target = "id", ignore = true)
    PersonFisica toEntity(UserPFRequestDTO request);


    @Mapping(source = "user.email", target = "email")
    PersonFisicaResponseDTO toResponse(PersonFisica personFisica);
}
