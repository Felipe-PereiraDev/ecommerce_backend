package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.config.security.UserContextService;
import br.com.felipedev.ecommerce.dto.brand.BrandRequestDTO;
import br.com.felipedev.ecommerce.dto.brand.BrandResponseDTO;
import br.com.felipedev.ecommerce.exception.DescriptionExistsException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.exception.UnprocessableEntityException;
import br.com.felipedev.ecommerce.mapper.BrandMapper;
import br.com.felipedev.ecommerce.model.Brand;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final PersonJuridicaService personJuridicaService;
    private final UserContextService userContextService;

    public BrandResponseDTO create(BrandRequestDTO request) {
        Long sellerId = userContextService.getAuthenticatedSellerId();
        PersonJuridica seller = personJuridicaService.findById(sellerId);

        if (brandRepository.existsByDescriptionAndSellerId(request.description(), sellerId)) {
            throw new DescriptionExistsException("The brand %s already exists".formatted(request.description()));
        }

        Brand brand = new Brand(null, request.description());
        brand.setSeller(seller);
        brandRepository.save(brand);
        return brandMapper.toResponseDTO(brand);
    }

    protected Brand findById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The brand with id %d not exists".formatted(id)));
    }

    public List<BrandResponseDTO> findAll() {
        return brandMapper.toResponseDTOList(brandRepository.findAll());
    }

    public BrandResponseDTO updateDescription(Long id, BrandRequestDTO request) {

        Brand brand = findById(id);

        userContextService.ensureSellerOwnsResource(brand.getSeller().getId());

        if (brandRepository.existsByDescriptionAndSellerId(request.description(), brand.getSeller().getId())) {
            throw new DescriptionExistsException("The brand %s already exists".formatted(request.description()));
        }

        brand.setDescription(request.description());
        brandRepository.save(brand);
        return brandMapper.toResponseDTO(brand);
    }


    public void deleteById(Long id) {
        Brand brand = findById(id);

        userContextService.ensureSellerOwnsResource(brand.getSeller().getId());

        if (brandRepository.hasProductsAssociated(id)) {
            throw new UnprocessableEntityException("Cannot delete the brand with ID %d".formatted(id));
        }
        brandRepository.delete(brand);
    }
}
