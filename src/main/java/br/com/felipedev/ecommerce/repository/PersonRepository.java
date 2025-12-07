package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
