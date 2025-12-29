package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.PersonFisica;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonJuridicaRepository extends JpaRepository<PersonJuridica, Long> {

    boolean existsByCnpj(String cpf);

    Optional<PersonJuridica> findByCnpj(String cnpj);
}
