package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.dto.product.ProductRequestDTO;
import br.com.felipedev.ecommerce.dto.product.ProductResponseDTO;
import br.com.felipedev.ecommerce.dto.product.ProductUpdateDTO;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.mapper.ProductMapper;
import br.com.felipedev.ecommerce.model.Brand;
import br.com.felipedev.ecommerce.model.Category;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.model.Product;
import br.com.felipedev.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PersonJuridicaService personJuridicaService;

    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        var category = categoryService.findById(request.categoryId());
        var brand = brandService.findById(request.brandId());
        PersonJuridica seller = personJuridicaService.getPersonJuridica();
        Product newProduct = new Product(
                brand,
                category,
                request.depth(),
                request.description(),
                request.height(),
                request.name(),
                request.price(),
                request.stockAlertQuantity(),
                request.stockQuantity(),
                request.unitType(),
                request.weight(),
                request.width(),
                request.youtubeLink(),
                seller
        );
        productRepository.save(newProduct);
        return productMapper.toResponseDTO(newProduct);
    }

    public List<ProductResponseDTO> findAll() {
        List<Product> productList = productRepository.findAll();
        return productMapper.toResponseDTOList(productList);
    }

    public ProductResponseDTO updateProduct(Long id, ProductUpdateDTO request) {
        Product product = findById(id);
        Brand brand = null;
        Category category = null;
        if (request.brandId() != null && !request.brandId().equals(product.getBrand().getId())) {
             brand = brandService.findById(request.brandId());
        }
        if (request.categoryId() != null && !request.categoryId().equals(product.getCategory().getId())) {
            category = categoryService.findById(request.brandId());
        }
        product.update(request, brand, category);
        productRepository.save(product);
        return productMapper.toResponseDTO(product);
    }

    protected Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The product with id %d not exists".formatted(id)));
    }

    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("The product with id %d not exists".formatted(id));
        }
        productRepository.deleteById(id);
    }
}
