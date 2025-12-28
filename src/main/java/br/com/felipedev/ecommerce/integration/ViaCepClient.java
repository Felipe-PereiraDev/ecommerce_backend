package br.com.felipedev.ecommerce.integration;


import br.com.felipedev.ecommerce.dto.viacep.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "via-cep", url = "https://viacep.com.br")
public interface ViaCepClient {

    @GetMapping("/ws/{cep}/json/")
    ViaCepResponse getAddressByZipCode(@PathVariable("cep") String cep);
}
