package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("SELECT COUNT(b) > 0 From Brand b WHERE b.description = :description and b.seller.id = :sellerId")
    boolean existsByDescriptionAndSellerId(@Param("description") String description, @Param("sellerId") Long sellerId);

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.brand.id = :brandId")
    boolean hasProductsAssociated(@Param("brandId")Long brandId);
}
