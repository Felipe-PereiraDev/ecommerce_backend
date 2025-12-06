package br.com.felipedev.ecommerce.dto.discountcoupon;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DiscountCouponUpdateDTO(
        @Length(min = 2, max = 50)
        String code,
        @Positive
        BigDecimal discountAmount,
        @Positive
        BigDecimal percentage,
        @Future
        LocalDateTime dueDate
) {
}
