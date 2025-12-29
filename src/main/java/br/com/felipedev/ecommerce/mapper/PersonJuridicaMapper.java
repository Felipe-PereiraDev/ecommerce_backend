package br.com.felipedev.ecommerce.mapper;


import br.com.felipedev.ecommerce.dto.person.juridica.PersonJuridicaResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserPJRequestDTO;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonJuridicaMapper {

    private final AddressMapper addressMapper;

    public PersonJuridica toEntity(UserPJRequestDTO request) {
        return PersonJuridica.builder()
                .name(request.name())
                .phone(request.phone())
                .category(request.category())
                .cnpj(request.cnpj())
                .corporateName(request.corporate_name())
                .stateRegistration(request.state_registration())
                .municipalRegistration(request.municipal_registration())
                .tradeName(request.trade_name())
                .build();
    }


    public PersonJuridicaResponseDTO toResponse(PersonJuridica person) {
        return new PersonJuridicaResponseDTO(
                person.getName(),
                person.getUser().getEmail(),
                person.getPhone(),
                person.getCnpj(),
                person.getCategory(),
                person.getCorporateName(),
                person.getMunicipalRegistration(),
                person.getStateRegistration(),
                person.getTradeName(),
                addressMapper.toResponseList(person.getAddresses())
        );
    }
}
