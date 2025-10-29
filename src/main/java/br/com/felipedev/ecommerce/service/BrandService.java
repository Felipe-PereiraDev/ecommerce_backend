package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.dto.brand.BrandRequestDTO;
import br.com.felipedev.ecommerce.dto.brand.BrandResponseDTO;
import br.com.felipedev.ecommerce.exception.DescriptionExistsException;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.mapper.BrandMapper;
import br.com.felipedev.ecommerce.model.Brand;
import br.com.felipedev.ecommerce.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandMapper brandMapper;

    public BrandResponseDTO create(BrandRequestDTO request) {
        if (brandRepository.existsByDescription(request.description())) {
            throw new DescriptionExistsException("The brand %s already exists".formatted(request.description()));
        }
        Brand brand = new Brand(null, request.description());
        brandRepository.save(brand);
        return brandMapper.toBrandResponseDTO(brand);
    }

    public List<BrandResponseDTO> findAll() {
        return brandMapper.toBrandResponseDTOs(brandRepository.findAll());
    }

    public BrandResponseDTO updateDescription(Long id, BrandRequestDTO request) {
        Optional<Brand> brand = brandRepository.findById(id);
        if (brand.isEmpty()) {
            throw new EntityNotFoundException("The brand with id %d not exists".formatted(id));
        }

        if (brandRepository.existsByDescription(request.description())) {
            throw new DescriptionExistsException("The brand %s already exists".formatted(request.description()));
        }

        brand.get().setDescription(request.description());
        return brandMapper.toBrandResponseDTO(brand.get());
    }


    public void deleteById(Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        if (brand.isEmpty()) {
            throw new EntityNotFoundException("The brand with id %d not exists".formatted(id));
        }
        brandRepository.delete(brand.get());
    }
}
