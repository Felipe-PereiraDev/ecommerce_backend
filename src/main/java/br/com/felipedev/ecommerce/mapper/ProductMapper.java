package br.com.felipedev.ecommerce.mapper;

import br.com.felipedev.ecommerce.dto.product.ProductRequestDTO;
import br.com.felipedev.ecommerce.dto.product.ProductResponseDTO;
import br.com.felipedev.ecommerce.model.Category;
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

    public ProductResponseDTO toProductResponseDTO(Product product) {
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
                brandMapper.toBrandResponseDTO(product.getBrand()),
                categoryMapper.toCategoryResponseDTO(product.getCategory())
        );
    }

    public List<ProductResponseDTO> toProductResponseDTOs(List<Product> productList) {
        return productList.stream()
                .map(this::toProductResponseDTO)
                .toList();
    }

}
