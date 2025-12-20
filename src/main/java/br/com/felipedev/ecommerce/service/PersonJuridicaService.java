package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.config.SecurityService;
import br.com.felipedev.ecommerce.dto.user.UserPJRequestDTO;
import br.com.felipedev.ecommerce.enums.RoleType;
import br.com.felipedev.ecommerce.exception.DuplicateResourceException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.mapper.PersonJuridicaMapper;
import br.com.felipedev.ecommerce.model.*;
import br.com.felipedev.ecommerce.repository.PersonJuridicaRepository;
import br.com.felipedev.ecommerce.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Long getIdAuthenticatedPersonJuridica() {
        Jwt token = securityService.getTokenAuthenticatedUser();
        List<String> roles = token.getClaimAsStringList("roles");
        Long userId = token.getClaim("personId");

        if (userId == null || roles == null) {
            throw new AccessDeniedException("Authentication credentials are incomplete or invalid");
        }

        if (!roles.contains(RoleType.ROLE_SELLER.name())) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
        return userId;
    }

    public boolean hasSellerOwnership(Long expectedSellerId, Long actualSellerId) {
        return expectedSellerId.equals(actualSellerId);
    }

    public boolean hasSellerOwnership(Long expectedSellerId, Brand brand, Category category) {
        return expectedSellerId.equals(brand.getSeller().getId())
                && expectedSellerId.equals(category.getSeller().getId());
    }

    public PersonJuridica findById(Long sellerId) {
        return personJuridicaRepository
                .findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Seller with id %d not found".formatted(sellerId)));
    }
}
