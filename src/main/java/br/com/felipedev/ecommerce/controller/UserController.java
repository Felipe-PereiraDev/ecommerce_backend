package br.com.felipedev.ecommerce.controller;


import br.com.felipedev.ecommerce.dto.jwt.TokenResponseDTO;
import br.com.felipedev.ecommerce.dto.user.*;
import br.com.felipedev.ecommerce.service.UserService;
import br.com.felipedev.ecommerce.service.UserVerifierService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @Autowired
    private final UserVerifierService userVerifierService;

    @PostMapping(value = "/pessoa-fisica")
    public ResponseEntity<UserPFResponseDTO> createPersonFisica(@RequestBody @Validated UserPFRequestDTO request) {
        UserResponseDTO userResponse = userService.createUserPF(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(userResponse.id())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(value = "/pessoa-juridica")
    public ResponseEntity<Void> createPersonJuridica(@RequestBody @Validated UserPJRequestDTO request) {
        UserResponseDTO userResponse = userService.createUserPJ(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(userResponse.id())
                .toUri();
        return ResponseEntity.created(uri).build();
    }


    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Validated UserLogin userLogin) {
        return ResponseEntity.ok(userService.authentication(userLogin));
    }

    @GetMapping(value = "/verify-token")
    public ResponseEntity<?> verifyToken(@RequestParam(name = "token") String token) {
        userVerifierService.validateVerificationToken(token);
        return ResponseEntity.ok().body("EMAIL VALIDADO COM SUCESSO");
    }


}
