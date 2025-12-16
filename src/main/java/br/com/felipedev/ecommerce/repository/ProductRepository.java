package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.active = false where p.id = :productId")
    void deactivateProduct(@Param("productId") Long productId);

    @Query("SELECT COUNT(p) > 0 From Product p WHERE UPPER(p.name) = UPPER(:productName) and p.seller.id = :sellerId")
    boolean existsByNameAndSellerId(@Param("productName") String productName, @Param("sellerId") Long sellerId);
}
