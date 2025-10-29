package br.com.felipedev.ecommerce.controller;

import br.com.felipedev.ecommerce.dto.category.CategoryRequestDTO;
import br.com.felipedev.ecommerce.dto.category.CategoryResponseDTO;
import br.com.felipedev.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createBrand(@RequestBody @Validated CategoryRequestDTO request) {
        return ResponseEntity.ok(categoryService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> updateDescription(@PathVariable("id") Long id, @RequestBody @Validated CategoryRequestDTO request) {
        return ResponseEntity.ok(categoryService.updateDescription(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDTO> updateDescription(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
