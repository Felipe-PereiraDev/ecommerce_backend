package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.product.ProductResponseDTO;
import br.com.felipedev.ecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getUnitType(),
                product.getDescription(),
                product.getWeight(),
                product.getWidth(),
                product.getHeight(),
                product.getDepth(),
                product.getStockQuantity(),
                product.getStockAlertQuantity(),
                product.getYoutubeLink(),
                brandMapper.toResponseDTO(product.getBrand()),
                categoryMapper.toResponseDTO(product.getCategory())
        );
    }

    public List<ProductResponseDTO> toResponseDTOList(List<Product> productList) {
        return productList.stream()
                .map(this::toResponseDTO)
                .toList();
    }

}
