package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.enums.RoleType;
import br.com.felipedev.ecommerce.model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleType roleType);
}
