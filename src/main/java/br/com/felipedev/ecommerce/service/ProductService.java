package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.dto.product.ProductRequestDTO;
import br.com.felipedev.ecommerce.dto.product.ProductResponseDTO;
import br.com.felipedev.ecommerce.dto.product.ProductUpdateDTO;
import br.com.felipedev.ecommerce.exception.DescriptionExistsException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
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

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        PersonJuridica seller = personJuridicaService.getAuthenticatedPersonJuridica();

        var category = categoryService.findById(request.categoryId());
        var brand = brandService.findById(request.brandId());

        if (!personJuridicaService.hasSellerOwnership(seller, brand, category)) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }

        if (productRepository.existsByNameAndSellerId(request.name(), seller.getId())){
            throw new DuplicateResourceException("The product with name %s already exists".formatted(request.description()));
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
        PersonJuridica seller = personJuridicaService.getAuthenticatedPersonJuridica();
        Product product = findById(id);
        verifySellerOwnership(seller.getId(), product.getSeller().getId());

        Brand brand = null;
        Category category = null;

        if (request.brandId() != null && !request.brandId().equals(product.getBrand().getId())) {
            brand = brandService.findById(request.brandId());
            verifySellerOwnership(seller.getId(), brand.getSeller().getId());
        }
        if (request.categoryId() != null && !request.categoryId().equals(product.getCategory().getId())) {
            category = categoryService.findById(request.brandId());
            verifySellerOwnership(seller.getId(), category.getSeller().getId());
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
        PersonJuridica seller = personJuridicaService.getAuthenticatedPersonJuridica();
        Product product = findById(id);

        verifySellerOwnership(seller.getId(), product.getSeller().getId());
        productRepository.delete(product);
        productRepository.deactivateProduct(id);
    }

    public void deactivateById(Long id) {
        PersonJuridica seller = personJuridicaService.getAuthenticatedPersonJuridica();
        Product product = findById(id);

        verifySellerOwnership(seller.getId(), product.getSeller().getId());
        if (product.getActive().equals(false)) {
            throw new UnprocessableEntityException("Product with id %d is already deactivated".formatted(id));
        }
        productRepository.deactivateProduct(id);
    }

    private void verifySellerOwnership(Long expectedSellerId, Long actualSellerId) {
        if (!personJuridicaService.hasSellerOwnership(expectedSellerId, actualSellerId)) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
    }
}
