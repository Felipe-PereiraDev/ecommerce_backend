package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String username);

    Optional<User> findByEmail(String email);
}
