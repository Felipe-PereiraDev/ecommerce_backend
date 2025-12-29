package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.PersonFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonFisicaRepository extends JpaRepository<PersonFisica, Long> {
    boolean existsByCpf(String cpf);

    Optional<PersonFisica> findByCpf(String cpf);
}
