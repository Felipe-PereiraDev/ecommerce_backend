package br.com.felipedev.ecommerce.controller.docs;


import br.com.felipedev.ecommerce.dto.product.ProductRequestDTO;
import br.com.felipedev.ecommerce.dto.product.ProductResponseDTO;
import br.com.felipedev.ecommerce.dto.product.ProductUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Product", description = "Endpoints para gerenciar produtos")
public interface ProductControllerDocs {

    @Operation(summary = "Criar Produto", description = "Cria um novo produto")
    public ResponseEntity<ProductResponseDTO> createProduct(ProductRequestDTO request);

    @Operation(summary = "Listar Produtos", description = "Retorna uma lista de produto")
    public ResponseEntity<List<ProductResponseDTO>> findAll();

    @Operation(summary = "Atualizar Produto", description = "Atualiza a descrição de um produto existente")
    public ResponseEntity<ProductResponseDTO> updateProduct(Long id, ProductUpdateDTO request);
    
    @Operation(summary = "Deletar Produto", description = "Deleta um produto pelo seu id")
    public ResponseEntity<Void> deleteById(Long id);
}
