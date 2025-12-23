package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.User;
import br.com.felipedev.ecommerce.model.UserVerifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserVerifierRepository extends JpaRepository<UserVerifier, Long> {

    Optional<UserVerifier> findByToken(String token);

    void deleteByUser(User user);
}
