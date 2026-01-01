package br.com.felipedev.ecommerce.mapper;


import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponRequestDTO;
import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponResponseDTO;
import br.com.felipedev.ecommerce.model.DiscountCoupon;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscountCouponMapper {

    @Mapping(target = "id", ignore = true)
    DiscountCoupon toEntity(PersonJuridica seller, DiscountCouponRequestDTO requestDTO);

    DiscountCouponResponseDTO toResponseDTO(DiscountCoupon discountCoupon);

    List<DiscountCouponResponseDTO> toResponseDTOList(List<DiscountCoupon> productList);
}
