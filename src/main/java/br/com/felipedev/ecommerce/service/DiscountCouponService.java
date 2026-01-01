package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.config.security.UserContextService;
import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponRequestDTO;
import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponResponseDTO;
import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponUpdateDTO;
import br.com.felipedev.ecommerce.exception.DescriptionExistsException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.mapper.DiscountCouponMapper;
import br.com.felipedev.ecommerce.model.DiscountCoupon;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.model.User;
import br.com.felipedev.ecommerce.repository.DiscountCouponRepository;
import br.com.felipedev.ecommerce.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountCouponService {

    private final DiscountCouponRepository couponRepository;

    private final DiscountCouponMapper couponMapper;

    private final UserContextService userContextService;


    public DiscountCouponResponseDTO createDiscountCoupon(DiscountCouponRequestDTO request) {
        userContextService.ensureSellerOwnsResource(request.sellerId());

        request = new DiscountCouponRequestDTO(
                request.sellerId(),
                Utils.formatCode(request.code()),
                request.discountAmount(),
                request.percentage(),
                request.dueDate()
        );
        if (couponRepository.existsByCode(request.code())) {
            throw new DescriptionExistsException("The discount coupon code %s already exists".formatted(request.code()));
        }

        PersonJuridica authenticatedSeller = (PersonJuridica) userContextService.getAuthenticatedPerson();

        DiscountCoupon newDiscountCoupon = couponMapper.toEntity(authenticatedSeller, request);

        couponRepository.save(newDiscountCoupon);
        return couponMapper.toResponseDTO(newDiscountCoupon);
    }

    public List<DiscountCouponResponseDTO> findAll() {
        List<DiscountCoupon> couponList = couponRepository.findAll();
        return couponMapper.toResponseDTOList(couponList);
    }

    public DiscountCouponResponseDTO updateDiscountCoupon(Long id, DiscountCouponUpdateDTO request) {
        DiscountCoupon discountCoupon = findById(id);
        userContextService.ensureSellerOwnsResource(discountCoupon.getSeller().getId());

        if (couponRepository.existsByCode(request.code())) {
            throw new DescriptionExistsException("The discount coupon code %s already exists".formatted(request.code()));
        }

        discountCoupon.update(request);
        couponRepository.save(discountCoupon);
        return couponMapper.toResponseDTO(discountCoupon);
    }

    protected DiscountCoupon findById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The coupon discount with id %d not exists".formatted(id)));
    }

    public void deleteById(Long id) {
        DiscountCoupon discountCoupon = findById(id);
        userContextService.ensureSellerOwnsResource(discountCoupon.getSeller().getId());
        couponRepository.delete(discountCoupon);
    }

    public DiscountCouponResponseDTO findByCode(String code) {
        DiscountCoupon discountCoupon = couponRepository.findByCode(code)
                .orElseThrow(
                        () -> new EntityNotFoundException("The coupon discount with code %s not exists".formatted(code))
                );
        return couponMapper.toResponseDTO(discountCoupon);
    }
}
