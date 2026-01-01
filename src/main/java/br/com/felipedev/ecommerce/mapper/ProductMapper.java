package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.product.ProductRequestDTO;
import br.com.felipedev.ecommerce.dto.product.ProductResponseDTO;
import br.com.felipedev.ecommerce.model.Brand;
import br.com.felipedev.ecommerce.model.Category;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                BrandMapper.class,
                CategoryMapper.class
        }
)
public interface ProductMapper {

    ProductResponseDTO toResponseDTO(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stockAlertEnabled", ignore = true)
    @Mapping(target = "clickCount", ignore = true)
    @Mapping(target = "active", ignore = true)

    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "request.description", target = "description")

    @Mapping(source = "seller", target = "seller")
    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "category", target = "category")

    Product toEntity(ProductRequestDTO request, PersonJuridica seller, Brand brand, Category category);

    List<ProductResponseDTO> toResponseDTOList(List<Product> productList);

}
