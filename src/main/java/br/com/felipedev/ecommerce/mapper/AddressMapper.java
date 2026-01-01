package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.address.AddressRequestDTO;
import br.com.felipedev.ecommerce.dto.address.AddressResponseDTO;
import br.com.felipedev.ecommerce.dto.viacep.ViaCepResponse;
import br.com.felipedev.ecommerce.enums.AddressType;
import br.com.felipedev.ecommerce.model.Address;
import br.com.felipedev.ecommerce.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(source = "viaCepResponse.cep", target = "zipCode")
    @Mapping(source = "viaCepResponse.logradouro", target = "street")
    @Mapping(source = "viaCepResponse.bairro", target = "neighborhood")
    @Mapping(source = "viaCepResponse.estado", target = "state")
    @Mapping(source = "viaCepResponse.localidade", target = "city")
    @Mapping(source = "requestDTO.number", target = "number")
    @Mapping(source = "requestDTO.complement", target = "complement")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "person", target = "person")
    Address toEntity(ViaCepResponse viaCepResponse, AddressRequestDTO requestDTO, AddressType type, Person person);

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "type.description", target = "addressType")
    AddressResponseDTO toResponse(Address address);

    List<AddressResponseDTO> toResponseList(List<Address> addressList);


}
