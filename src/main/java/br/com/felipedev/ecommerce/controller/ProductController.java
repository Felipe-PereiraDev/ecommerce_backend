package br.com.felipedev.ecommerce.controller;

import br.com.felipedev.ecommerce.controller.docs.ProductControllerDocs;
import br.com.felipedev.ecommerce.dto.product.ProductRequestDTO;
import br.com.felipedev.ecommerce.dto.product.ProductResponseDTO;
import br.com.felipedev.ecommerce.dto.product.ProductUpdateDTO;
import br.com.felipedev.ecommerce.service.ProductService;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController implements ProductControllerDocs {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Override
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Validated  ProductRequestDTO request) {
        System.out.println(request);
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping
    @Override
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PutMapping(value = "/{id}")
    @Override
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id,
                                                            @RequestBody @Validated ProductUpdateDTO request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
