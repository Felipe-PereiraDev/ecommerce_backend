package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByDescription(String description);
}
