package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT COUNT(c) > 0 From Category c WHERE c.description = :description and c.seller.id = :sellerId")
    boolean existsByDescriptionAndSellerId(@Param("description") String description, @Param("sellerId") Long sellerId);

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.category.id = :categoryId")
    boolean hasProductsAssociated(@Param("categoryId")Long categoryId);
}
