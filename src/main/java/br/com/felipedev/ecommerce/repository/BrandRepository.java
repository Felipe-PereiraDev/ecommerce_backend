package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.Brand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByDescription(String description);
}
