package br.com.felipedev.ecommerce.controller.docs;

import br.com.felipedev.ecommerce.dto.brand.BrandRequestDTO;
import br.com.felipedev.ecommerce.dto.brand.BrandResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Brand", description = "Endpoints para gerenciar marcas")
public interface BrandControllerDocs {

    @Operation(summary = "Criar Marca", description = "Cria uma nova marca")
    public ResponseEntity<BrandResponseDTO> createBrand(BrandRequestDTO request);

    @Operation(summary = "Listar Marcas", description = "Retorna uma lista de marcas")
    public ResponseEntity<List<BrandResponseDTO>> findAll();

    @Operation(summary = "Atualizar Descrição", description = "Atualiza a descrição de uma marca existente")
    public ResponseEntity<BrandResponseDTO> updateDescription(Long id, BrandRequestDTO request);
    
    @Operation(summary = "Deletar Marcar", description = "Deleta uma marca pelo seu id")
    public ResponseEntity<Void> deleteById(Long id);
}
