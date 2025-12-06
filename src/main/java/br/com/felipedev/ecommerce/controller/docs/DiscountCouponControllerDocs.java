package br.com.felipedev.ecommerce.controller.docs;


import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponRequestDTO;
import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponResponseDTO;
import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "DiscountCoupon", description = "Endpoints para gerenciar Cupons de Descontos")
public interface DiscountCouponControllerDocs {

    @Operation(summary = "Criar Cupom de Desconto", description = "Cria um novo cupom de desconto")
    public ResponseEntity<DiscountCouponResponseDTO> createDiscountCoupon(DiscountCouponRequestDTO request);

    @Operation(summary = "Listar Cupons de Descontos", description = "Retorna uma lista de cupons de descontos")
    public ResponseEntity<List<DiscountCouponResponseDTO>> findAll();

    @Operation(summary = "Atualizar Cupom de Desconto", description = "Atualiza os dados de um cupom de desconto")
    public ResponseEntity<DiscountCouponResponseDTO> updateDiscountCoupon(Long id, DiscountCouponUpdateDTO request);
    
    @Operation(summary = "Deletar Cupom de Desconto", description = "Deleta um cupom de desconto pelo seu id")
    public ResponseEntity<Void> deleteById(Long id);

    @Operation(summary = "Buscar Cupom de Desconto", description = "Busca um cupom de desconto pelo seu código")
    public ResponseEntity<DiscountCouponResponseDTO> findByCode(String code);
}
