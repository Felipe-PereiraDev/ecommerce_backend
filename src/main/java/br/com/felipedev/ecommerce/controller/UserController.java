package br.com.felipedev.ecommerce.controller;


import br.com.felipedev.ecommerce.dto.jwt.TokenResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserLogin;
import br.com.felipedev.ecommerce.dto.user.UserPFRequestDTO;
import br.com.felipedev.ecommerce.dto.user.UserPFResponseDTO;
import br.com.felipedev.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/pessoa-fisica")
    public ResponseEntity<UserPFResponseDTO> createPersonFisica(@RequestBody @Validated UserPFRequestDTO request) {
        return ResponseEntity.ok(userService.createUserPF(request));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody UserLogin userLogin) {
        return ResponseEntity.ok(userService.authentication(userLogin));
    }


}
