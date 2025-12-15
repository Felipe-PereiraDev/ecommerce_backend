package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.config.SecurityService;
import br.com.felipedev.ecommerce.dto.user.UserPJRequestDTO;
import br.com.felipedev.ecommerce.exception.DuplicateResourceException;
import br.com.felipedev.ecommerce.mapper.PersonJuridicaMapper;
import br.com.felipedev.ecommerce.model.Person;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.model.User;
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

    public PersonJuridica getPersonJuridica() {
        User user = securityService.getAuthenticatedUser();
        Person person = user.getPerson();

        if (person == null) {
            throw new AccessDeniedException("Usuário não possui pessoa associada");
        }

        if (!(person instanceof PersonJuridica)) {
            throw new AccessDeniedException("Você não tem permissão pra acessar este recurso");
        }
        return (PersonJuridica) person;
    }
}
