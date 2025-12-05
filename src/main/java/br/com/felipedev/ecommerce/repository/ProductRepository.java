package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
