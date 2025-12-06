package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.Category;
import br.com.felipedev.ecommerce.model.DiscountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, Long> {
    boolean existsByCode(String code);
    Optional<DiscountCoupon> findByCode(String code);
}
