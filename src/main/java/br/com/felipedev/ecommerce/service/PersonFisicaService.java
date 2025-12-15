package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.dto.user.UserPFRequestDTO;
import br.com.felipedev.ecommerce.exception.DuplicateResourceException;
import br.com.felipedev.ecommerce.mapper.PersonFisicaMapper;
import br.com.felipedev.ecommerce.model.PersonFisica;
import br.com.felipedev.ecommerce.repository.PersonFisicaRepository;
import br.com.felipedev.ecommerce.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonFisicaService {

    @Autowired
    private PersonFisicaRepository personFisicaRepository;

    @Autowired
    private PersonFisicaMapper personFisicaMapper;

    @Autowired
    private PersonRepository personRepository;

    public PersonFisica createPersonFisica(UserPFRequestDTO request) {
        existsByPhone(request.phone());
        existsByCpf(request.cpf());
        PersonFisica newPersonFisica = personFisicaMapper.toEntity(request);
        personFisicaRepository.save(newPersonFisica);
        return newPersonFisica;
    }
    private void existsByCpf(String cpf) {
        if (personFisicaRepository.existsByCpf(cpf)) {
            throw new DuplicateResourceException("The cpf %s already exists".formatted(cpf));
        }
    }

    private void existsByPhone(String phone) {
        if (personRepository.existsByPhone(phone)) {
            throw new DuplicateResourceException("The phone %s already exists".formatted(phone));
        }
    }


}
