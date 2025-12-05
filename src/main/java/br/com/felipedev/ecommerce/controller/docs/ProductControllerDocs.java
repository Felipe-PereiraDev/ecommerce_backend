package br.com.felipedev.ecommerce.controller.docs;


import br.com.felipedev.ecommerce.dto.product.ProductRequestDTO;
import br.com.felipedev.ecommerce.dto.product.ProductResponseDTO;
import br.com.felipedev.ecommerce.dto.product.ProductUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Product", description = "Endpoints para gerenciar products")
public interface ProductControllerDocs {

    @Operation(summary = "Criar Product", description = "Cria uma nova product")
    public ResponseEntity<ProductResponseDTO> createProduct(ProductRequestDTO request);

    @Operation(summary = "Listar Products", description = "Retorna uma lista de products")
    public ResponseEntity<List<ProductResponseDTO>> findAll();

    @Operation(summary = "Atualizar Descrição", description = "Atualiza a descrição de uma product existente")
    public ResponseEntity<ProductResponseDTO> updateProduct(Long id, ProductUpdateDTO request);
    
    @Operation(summary = "Deletar Product", description = "Deleta uma merca pelo seu id")
    public ResponseEntity<Void> deleteById(Long id);
}
