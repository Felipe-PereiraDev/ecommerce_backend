package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.address.AddressRequestDTO;
import br.com.felipedev.ecommerce.dto.address.AddressResponseDTO;
import br.com.felipedev.ecommerce.dto.address.AddressUpdateDTO;
import br.com.felipedev.ecommerce.dto.viacep.ViaCepResponse;
import br.com.felipedev.ecommerce.enums.AddressType;
import br.com.felipedev.ecommerce.model.Address;
import br.com.felipedev.ecommerce.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressMapper {

    public Address toEntity(ViaCepResponse viaCepResponse, AddressRequestDTO requestDTO, AddressType addressType, Person person) {
        return Address.builder()
                .zipCode(viaCepResponse.cep())
                .street(viaCepResponse.logradouro())
                .number(requestDTO.number())
                .complement(requestDTO.complement())
                .neighborhood(viaCepResponse.bairro())
                .state(viaCepResponse.estado())
                .city(viaCepResponse.localidade())
                .person(person)
                .type(addressType)
                .build();
    }

    public AddressResponseDTO toResponse(Address address) {
        return new AddressResponseDTO(
                address.getId(),
                address.getPerson().getId(),
                address.zipCode,
                address.street,
                address.number,
                address.complement,
                address.neighborhood,
                address.state,
                address.city,
                address.type.getDescription()
        );
    }

    public List<AddressResponseDTO> toResponseList(List<Address> addressList) {
        return addressList.stream().map(
                this::toResponse
        ).toList();
    }


}
