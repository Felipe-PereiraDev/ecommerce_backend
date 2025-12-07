package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByDescription(String description);
}
