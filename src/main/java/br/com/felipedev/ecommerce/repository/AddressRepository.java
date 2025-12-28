package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.enums.AddressType;
import br.com.felipedev.ecommerce.model.Address;
import br.com.felipedev.ecommerce.model.Category;
import br.com.felipedev.ecommerce.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressByPersonAndType(Person person, AddressType type);

    boolean existsByPersonAndType(Person person, AddressType type);
}
