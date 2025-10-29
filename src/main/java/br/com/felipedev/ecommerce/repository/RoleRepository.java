package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
