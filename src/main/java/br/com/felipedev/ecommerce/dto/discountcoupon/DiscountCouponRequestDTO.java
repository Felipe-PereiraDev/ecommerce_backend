package br.com.felipedev.ecommerce.dto.discountcoupon;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DiscountCouponRequestDTO(
        @NotNull
        Long sellerId,
        @NotBlank @Length(max = 50)
        String code,
        @Positive
        BigDecimal discountAmount,
        @Positive
        BigDecimal percentage,
        @Future @NotNull
        LocalDateTime dueDate
) {
}
