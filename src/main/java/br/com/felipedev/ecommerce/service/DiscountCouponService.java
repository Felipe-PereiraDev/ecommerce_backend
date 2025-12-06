package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponRequestDTO;
import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponResponseDTO;
import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponUpdateDTO;
import br.com.felipedev.ecommerce.exception.DescriptionExistsException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.mapper.DiscountCouponMapper;
import br.com.felipedev.ecommerce.model.DiscountCoupon;
import br.com.felipedev.ecommerce.repository.DiscountCouponRepository;
import br.com.felipedev.ecommerce.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountCouponService {

    @Autowired
    private DiscountCouponRepository couponRepository;

    @Autowired
    private DiscountCouponMapper couponMapper;

    public DiscountCouponResponseDTO createDiscountCoupon(DiscountCouponRequestDTO request) {
        request = new DiscountCouponRequestDTO(
                Utils.formatCode(request.code()),
                request.discountAmount(),
                request.percentage(),
                request.dueDate()
        );
        if (couponRepository.existsByCode(request.code())) {
            throw new DescriptionExistsException("The discount coupon code %s already exists".formatted(request.code()));
        }
        DiscountCoupon newDiscountCoupon = new DiscountCoupon(
                request.percentage(),
                request.dueDate(),
                request.discountAmount(),
                request.code()
        );
        couponRepository.save(newDiscountCoupon);
        return couponMapper.toDiscountCouponResponseDTO(newDiscountCoupon);
    }

    public List<DiscountCouponResponseDTO> findAll() {
        List<DiscountCoupon> couponList = couponRepository.findAll();
        return couponMapper.toDiscountCouponResponseDTOs(couponList);
    }

    public DiscountCouponResponseDTO updateDiscountCoupon(Long id, DiscountCouponUpdateDTO request) {
        DiscountCoupon discountCoupon = findById(id);
        if (couponRepository.existsByCode(request.code())) {
            throw new DescriptionExistsException("The discount coupon code %s already exists".formatted(request.code()));
        }

        discountCoupon.update(request);
        couponRepository.save(discountCoupon);
        return couponMapper.toDiscountCouponResponseDTO(discountCoupon);
    }

    protected DiscountCoupon findById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The coupon discount with id %d not exists".formatted(id)));
    }

    public void deleteById(Long id) {
        if (!couponRepository.existsById(id)) {
            throw new EntityNotFoundException("The coupon discount with id %d not exists".formatted(id));
        }
        couponRepository.deleteById(id);
    }

    public DiscountCouponResponseDTO findByCode(String code) {
        DiscountCoupon discountCoupon = couponRepository.findByCode(code)
                .orElseThrow(
                        () -> new EntityNotFoundException("The coupon discount with code %s not exists".formatted(code))
                );
        return couponMapper.toDiscountCouponResponseDTO(discountCoupon);
    }
}
