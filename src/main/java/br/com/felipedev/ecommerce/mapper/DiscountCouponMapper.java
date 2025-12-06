package br.com.felipedev.ecommerce.mapper;


import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponResponseDTO;
import br.com.felipedev.ecommerce.model.DiscountCoupon;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiscountCouponMapper {
    public DiscountCouponResponseDTO toDiscountCouponResponseDTO(DiscountCoupon discountCoupon) {
        return new DiscountCouponResponseDTO(
                discountCoupon.getId(),
                discountCoupon.getCode(),
                discountCoupon.getDiscountAmount(),
                discountCoupon.getPercentage(),
                discountCoupon.getDueDate()
        );
    }

    public List<DiscountCouponResponseDTO> toDiscountCouponResponseDTOs(List<DiscountCoupon> productList) {
        return productList.stream()
                .map(this::toDiscountCouponResponseDTO)
                .toList();
    }

}
