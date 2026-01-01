package br.com.felipedev.ecommerce.mapper;


import br.com.felipedev.ecommerce.dto.person.juridica.PersonJuridicaResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserPJRequestDTO;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface PersonJuridicaMapper {

    @Mapping(target = "id", ignore = true)
    PersonJuridica toEntity(UserPJRequestDTO request);

    @Mapping(source = "user.email", target = "email")
    PersonJuridicaResponseDTO toResponse(PersonJuridica person);
}
