package br.com.felipedev.ecommerce.controller;


import br.com.felipedev.ecommerce.dto.user.UserPFRequestDTO;
import br.com.felipedev.ecommerce.dto.user.UserPFResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserResponseDTO;
import br.com.felipedev.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return userService.findAll();
    }

    @PostMapping(value = "/pessoa-fisica")
    public ResponseEntity<UserPFResponseDTO> createPersonFisica(@RequestBody @Validated UserPFRequestDTO request) {
        return ResponseEntity.ok(userService.createUserPF(request));
    }
}
