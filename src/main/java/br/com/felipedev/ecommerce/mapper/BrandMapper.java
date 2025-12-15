package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.brand.BrandResponseDTO;
import br.com.felipedev.ecommerce.model.Brand;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrandMapper {

    public BrandResponseDTO toResponseDTO(Brand brand) {
        return new BrandResponseDTO(brand.getId(), brand.getDescription(), brand.getSeller().getId());
    }

    public List<BrandResponseDTO> toResponseDTOList(List<Brand> brandList) {
        return brandList.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
