package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.config.SecurityService;
import br.com.felipedev.ecommerce.dto.user.UserPJRequestDTO;
import br.com.felipedev.ecommerce.exception.DuplicateResourceException;
import br.com.felipedev.ecommerce.mapper.PersonJuridicaMapper;
import br.com.felipedev.ecommerce.model.*;
import br.com.felipedev.ecommerce.repository.PersonJuridicaRepository;
import br.com.felipedev.ecommerce.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class PersonJuridicaService {

    @Autowired
    private PersonJuridicaRepository personJuridicaRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonJuridicaMapper personJuridica;

    @Autowired
    private SecurityService securityService;

    public PersonJuridica createPersonJuridica(UserPJRequestDTO request) {
        existsByPhone(request.phone());
        existsByCnpj(request.cnpj());
        PersonJuridica newPersonJuridica = personJuridica.toEntity(request);
        personJuridicaRepository.save(newPersonJuridica);
        return newPersonJuridica;
    }
    private void existsByCnpj(String cnpj) {
        if (personJuridicaRepository.existsByCnpj(cnpj)) {
            throw new DuplicateResourceException("The cnpj %s already exists".formatted(cnpj));
        }
    }

    private void existsByPhone(String phone) {
        if (personRepository.existsByPhone(phone)) {
            throw new DuplicateResourceException("The phone %s already exists".formatted(phone));
        }
    }

    public PersonJuridica getAuthenticatedPersonJuridica() {
        User user = securityService.getAuthenticatedUser();
        Person person = user.getPerson();

        if (person == null) {
            throw new AccessDeniedException("User entity has no linked person");
        }

        if (!(person instanceof PersonJuridica)) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
        return (PersonJuridica) person;
    }

    public boolean hasSellerOwnership(Long expectedSellerId, Long actualSellerId) {
        return expectedSellerId.equals(actualSellerId);
    }

    public boolean hasSellerOwnership(PersonJuridica seller, Brand brand, Category category) {
        return seller.getId().equals(brand.getSeller().getId())
                && seller.getId().equals(category.getSeller().getId());
    }
}
