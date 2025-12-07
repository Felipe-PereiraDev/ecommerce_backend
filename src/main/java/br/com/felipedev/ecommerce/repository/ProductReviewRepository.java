package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.ProductReview;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
}
