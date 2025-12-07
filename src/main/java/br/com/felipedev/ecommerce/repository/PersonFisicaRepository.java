package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.PersonFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonFisicaRepository extends JpaRepository<PersonFisica, Long> {
    boolean existsByCpf(String cpf);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String phone);
}
