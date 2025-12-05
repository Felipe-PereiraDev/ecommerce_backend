package br.com.felipedev.ecommerce.controller.docs;

import br.com.felipedev.ecommerce.dto.category.CategoryRequestDTO;
import br.com.felipedev.ecommerce.dto.category.CategoryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Category", description = "Endpoints para gerenciar categorias")
public interface CategoryControllerDocs {

    @Operation(summary = "Criar Categoria", description = "Cria uma nova categoria")
    public ResponseEntity<CategoryResponseDTO> createCategory(CategoryRequestDTO request);

    @Operation(summary = "Listar Categorias", description = "Retorna uma lista de categorias")
    public ResponseEntity<List<CategoryResponseDTO>> findAll();

    @Operation(summary = "Atualizar Descrição", description = "Atualiza a descrição de uma categoria existente")
    public ResponseEntity<CategoryResponseDTO> updateDescription(Long id, CategoryRequestDTO request);
    
    @Operation(summary = "Deletar Categoria", description = "Deleta uma merca pelo seu id")
    public ResponseEntity<Void> deleteById(Long id);
}
