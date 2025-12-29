package br.com.felipedev.ecommerce.controller;

import br.com.felipedev.ecommerce.dto.person.fisica.PersonFisicaResponseDTO;
import br.com.felipedev.ecommerce.dto.person.juridica.PersonJuridicaResponseDTO;
import br.com.felipedev.ecommerce.service.PersonFisicaService;
import br.com.felipedev.ecommerce.service.PersonJuridicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonFisicaService personFisicaService;

    private final PersonJuridicaService personJuridicaService;

    @GetMapping(value = "/fisica/{cpf}")
    public ResponseEntity<?> findPersonFisicaByCpf(@PathVariable("cpf") String cpf) {
        PersonFisicaResponseDTO personFisicaResponse = personFisicaService.findByCpf(cpf);
        return ResponseEntity.ok(personFisicaResponse);
    }

    @GetMapping(value = "/juridica/{cnpj}")
    public ResponseEntity<PersonJuridicaResponseDTO> findPersonJuridicaCnpj(@PathVariable("cnpj") String cnpj) {
        PersonJuridicaResponseDTO personJuridicaResponse = personJuridicaService.findByCnpj(cnpj);
        return ResponseEntity.ok(personJuridicaResponse);
    }
}
