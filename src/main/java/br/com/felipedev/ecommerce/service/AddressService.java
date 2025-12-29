package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.config.security.UserContextService;
import br.com.felipedev.ecommerce.dto.address.AddressRequestDTO;
import br.com.felipedev.ecommerce.dto.address.AddressResponseDTO;
import br.com.felipedev.ecommerce.dto.address.AddressUpdateDTO;
import br.com.felipedev.ecommerce.dto.viacep.ViaCepResponse;
import br.com.felipedev.ecommerce.enums.AddressType;
import br.com.felipedev.ecommerce.exception.BadRequestException;
import br.com.felipedev.ecommerce.exception.ConflictException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.integration.ViaCepClient;
import br.com.felipedev.ecommerce.mapper.AddressMapper;
import br.com.felipedev.ecommerce.model.Address;
import br.com.felipedev.ecommerce.model.Person;
import br.com.felipedev.ecommerce.model.User;
import br.com.felipedev.ecommerce.repository.AddressRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final ViaCepClient viaCepClient;

    private final AddressMapper addressMapper;

    private final AddressRepository addressRepository;

    private final UserContextService userContextService;

    public AddressResponseDTO create(AddressRequestDTO request) {
        ViaCepResponse viaCepResponse = getAddressByZipCode(request.zipCode());
        Person person = userContextService.getAuthenticatedPerson();

        AddressType addressType = AddressType.getAddressType(request.addressType());
        if (addressType == null) {
            throw new BadRequestException("tipo inválido, disponíveis:" + Arrays.toString(AddressType.values()));
        }

        person.getAddresses().forEach(ad -> {
            if ( ad.getType().equals(addressType)) {
                throw new ConflictException("There is already an address of this type");
            }
        });

        Address address = addressMapper.toEntity(viaCepResponse, request, addressType, person);
        addressRepository.save(address);
        return addressMapper.toResponse(address);
    }

    public AddressResponseDTO updateAddress(Long id, AddressUpdateDTO request) {
        Address address = findById(id);
        Person person = userContextService.getAuthenticatedPerson();

        if (!address.getPerson().getId().equals(person.getId())) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }

        ViaCepResponse viaCepResponse = getAddressByZipCode(request.zipCode());
        address.update(request, viaCepResponse);

        addressRepository.save(address);
        return addressMapper.toResponse(address);
    }

    public List<AddressResponseDTO> findAllAddressByPerson() {
        Person person = userContextService.getAuthenticatedPerson();
        return addressMapper.toResponseList(person.getAddresses());
    }

    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Address not found")
                );
    }

    private ViaCepResponse getAddressByZipCode(String cep) {
        cep  = cep.replace("\\s+", "");
        try {
            return viaCepClient.getAddressByZipCode(cep);
        } catch (FeignException.FeignClientException ex) {
            throw new BadRequestException("CEP NÃO ENCONTRADO");
        }
    }


}
