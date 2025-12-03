package br.com.felipedev.ecommerce.controller;

import br.com.felipedev.ecommerce.controller.docs.BrandControllerDocs;
import br.com.felipedev.ecommerce.dto.brand.BrandRequestDTO;
import br.com.felipedev.ecommerce.dto.brand.BrandResponseDTO;
import br.com.felipedev.ecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brands")
public class BrandController implements BrandControllerDocs {

    @Autowired
    private BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponseDTO> createBrand(@RequestBody @Validated BrandRequestDTO request) {
        return ResponseEntity.ok(brandService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> findAll() {
        return ResponseEntity.ok(brandService.findAll());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BrandResponseDTO> updateDescription(@PathVariable("id") Long id, @RequestBody @Validated BrandRequestDTO request) {
        return ResponseEntity.ok(brandService.updateDescription(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BrandResponseDTO> deleteById(@PathVariable("id") Long id) {
        brandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
