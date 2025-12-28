package br.com.felipedev.ecommerce.controller.docs;

import br.com.felipedev.ecommerce.dto.address.AddressRequestDTO;
import br.com.felipedev.ecommerce.dto.address.AddressResponseDTO;
import br.com.felipedev.ecommerce.dto.address.AddressUpdateDTO;
import br.com.felipedev.ecommerce.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestBody @Validated AddressRequestDTO request) {
        AddressResponseDTO response = addressService.create(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable("id") Long id,
                                                            @RequestBody @Validated AddressUpdateDTO request) {
        return ResponseEntity.ok(addressService.updateAddress(id, request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<AddressResponseDTO>> findAllAddressByPerson() {
        return ResponseEntity.ok(addressService.findAllAddressByPerson());
    }

}
