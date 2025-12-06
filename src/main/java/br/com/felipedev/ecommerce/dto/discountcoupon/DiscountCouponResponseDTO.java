package br.com.felipedev.ecommerce.dto.discountcoupon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DiscountCouponResponseDTO(
        Long id,
        String code,
        BigDecimal discountAmount,
        BigDecimal percentage,
        LocalDateTime dateTime
) {
}
