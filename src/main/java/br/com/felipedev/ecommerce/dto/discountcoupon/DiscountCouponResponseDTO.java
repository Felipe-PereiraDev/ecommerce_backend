package br.com.felipedev.ecommerce.dto.discountcoupon;

import com.fasterxml.jackson.annotation.JsonFormat;
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
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
        LocalDateTime dueDate
) {
}
