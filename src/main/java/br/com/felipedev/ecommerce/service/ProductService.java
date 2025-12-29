package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.config.security.UserContextService;
import br.com.felipedev.ecommerce.dto.product.ProductRequestDTO;
import br.com.felipedev.ecommerce.dto.product.ProductResponseDTO;
import br.com.felipedev.ecommerce.dto.product.ProductUpdateDTO;
import br.com.felipedev.ecommerce.exception.DuplicateResourceException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.exception.UnprocessableEntityException;
import br.com.felipedev.ecommerce.mapper.ProductMapper;
import br.com.felipedev.ecommerce.model.Brand;
import br.com.felipedev.ecommerce.model.Category;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.model.Product;
import br.com.felipedev.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final PersonJuridicaService personJuridicaService;
    private final UserContextService userContextService;

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        Long sellerId = userContextService.getAuthenticatedSellerId();
        PersonJuridica seller = personJuridicaService.findById(sellerId);

        var category = categoryService.findById(request.categoryId());
        var brand = brandService.findById(request.brandId());

        userContextService.ensureSellerOwnsBrandAndCategory(sellerId, brand.getSeller().getId(), category.getSeller().getId());

        if (productRepository.existsByNameAndSellerId(request.name(), sellerId)){
            throw new DuplicateResourceException("The product with name %s already exists".formatted(request.name()));
        }

        Product newProduct = productMapper.toEntity(request, seller, brand, category);

        productRepository.save(newProduct);
        return productMapper.toResponseDTO(newProduct);
    }

    public List<ProductResponseDTO> findAll() {
        List<Product> productList = productRepository.findAll();
        return productMapper.toResponseDTOList(productList);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductUpdateDTO request) {
        Product product = findById(id);
        // verifica se o produto pertence ao seller
        userContextService.ensureSellerOwnsResource(product.getSeller().getId());

        Brand brand = null;
        Category category = null;

        if (request.brandId() != null && !request.brandId().equals(product.getBrand().getId())) {
            brand = brandService.findById(request.brandId());
            // verifica se a nova marca pertence ao seller autenticado
            userContextService.ensureSellerOwnsResource(brand.getSeller().getId());
        }
        if (request.categoryId() != null && !request.categoryId().equals(product.getCategory().getId())) {
            category = categoryService.findById(request.categoryId());
            // verifica se a categoria marca pertence ao seller autenticado
            userContextService.ensureSellerOwnsResource(category.getSeller().getId());
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
        Product product = findById(id);
        userContextService.ensureSellerOwnsResource(product.getSeller().getId());

        productRepository.delete(product);
        productRepository.deactivateProduct(id);
    }

    public void deactivateById(Long id) {
        Product product = findById(id);
        userContextService.ensureSellerOwnsResource(product.getSeller().getId());

        if (product.isDisabled()) {
            throw new UnprocessableEntityException("Product with id %d is already deactivated".formatted(id));
        }
        productRepository.deactivateProduct(id);
    }

}
