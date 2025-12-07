package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.DiscountCoupon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@Transactional
public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, Long> {
    boolean existsByCode(String code);
    Optional<DiscountCoupon> findByCode(String code);
}
