package br.com.felipedev.ecommerce.controller;

import br.com.felipedev.ecommerce.controller.docs.DiscountCouponControllerDocs;
import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponRequestDTO;
import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponResponseDTO;
import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponUpdateDTO;
import br.com.felipedev.ecommerce.service.DiscountCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/discount-coupon")
public class DiscountCouponController implements DiscountCouponControllerDocs {

    @Autowired
    private DiscountCouponService couponService;


    @PostMapping
    @Override
    public ResponseEntity<DiscountCouponResponseDTO> createDiscountCoupon(@RequestBody @Validated DiscountCouponRequestDTO request) {
        return ResponseEntity.ok(couponService.createDiscountCoupon(request));
    }

    @GetMapping
    @Override
    public ResponseEntity<List<DiscountCouponResponseDTO>> findAll() {
        return ResponseEntity.ok(couponService.findAll());
    }

    @PutMapping(value = "/{id}")
    @Override
    public ResponseEntity<DiscountCouponResponseDTO> updateDiscountCoupon(@PathVariable("id") Long id,
                                                                          @RequestBody @Validated DiscountCouponUpdateDTO request) {
        return ResponseEntity.ok(couponService.updateDiscountCoupon(id, request));
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        couponService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{code}")
    @Override
    public ResponseEntity<DiscountCouponResponseDTO> findByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(couponService.findByCode(code));
    }
}
