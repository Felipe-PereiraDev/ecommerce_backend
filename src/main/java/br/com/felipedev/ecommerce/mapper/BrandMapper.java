package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.brand.BrandResponseDTO;
import br.com.felipedev.ecommerce.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mapping(source = "seller.id", target = "sellerId")
    BrandResponseDTO toResponseDTO(Brand brand);

    @Mapping(source = "seller", target = "sellerId")
    @Mapping(target = "products", ignore = true)
    List<BrandResponseDTO> toResponseDTOList(List<Brand> brandList);
}
