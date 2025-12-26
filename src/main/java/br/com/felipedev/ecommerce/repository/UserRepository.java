package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE u.passwordUpdatedAt <= :expirationDate")
    List<User> findUsersWithExpiredPasswords(@Param("expirationDate") LocalDate expirationDate);
}
