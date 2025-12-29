package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.config.security.UserContextService;
import br.com.felipedev.ecommerce.dto.person.juridica.PersonJuridicaResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserPJRequestDTO;
import br.com.felipedev.ecommerce.exception.DuplicateResourceException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.mapper.PersonJuridicaMapper;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.repository.PersonJuridicaRepository;
import br.com.felipedev.ecommerce.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonJuridicaService {

    private final PersonJuridicaRepository personJuridicaRepository;

    private final PersonRepository personRepository;

    private final PersonJuridicaMapper personJuridicaMapper;

    private final UserContextService userContextService;

    public PersonJuridica createPersonJuridica(UserPJRequestDTO request) {
        existsByPhone(request.phone());
        existsByCnpj(request.cnpj());
        PersonJuridica newPersonJuridica = personJuridicaMapper.toEntity(request);
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

    public PersonJuridica findById(Long sellerId) {
        return personJuridicaRepository
                .findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Seller with id %d not found".formatted(sellerId)));
    }

    public PersonJuridicaResponseDTO findByCnpj(String cnpj) {
        PersonJuridica personJuridica = personJuridicaRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userContextService.ensureUserOwnsResource(personJuridica.getUser().getId());
        return personJuridicaMapper.toResponse(personJuridica);
    }
}
