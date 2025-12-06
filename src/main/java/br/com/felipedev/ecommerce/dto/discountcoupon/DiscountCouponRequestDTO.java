package br.com.felipedev.ecommerce.dto.discountcoupon;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DiscountCouponRequestDTO(
        @NotBlank @Length(max = 50)
        String code,
        @Positive
        BigDecimal discountAmount,
        @Positive
        BigDecimal percentage,
        @Future
        LocalDateTime dueDate
) {
}
