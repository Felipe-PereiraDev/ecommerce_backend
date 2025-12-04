package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<Long, ProductReview> {
}
