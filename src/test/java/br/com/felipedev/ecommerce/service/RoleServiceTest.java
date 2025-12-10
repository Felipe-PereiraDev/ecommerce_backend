package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.enums.RoleType;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.model.Category;
import br.com.felipedev.ecommerce.model.Role;
import br.com.felipedev.ecommerce.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleService roleService;

    @Test
    void test_getRole_WhenRoleNameIdExists_ShouldReturnRoleEntity() {
        RoleType admin = RoleType.ROLE_ADMIN;
        Role role = new Role(1L, admin);
        Long expectedId = 1L;

        when(roleRepository.findByRole(admin)).thenReturn(Optional.of(role));

        Role result = roleService.getRole(admin);

        assertEquals(admin, result.getRole());
        assertNotNull(role);
    }

    @Test
    void test_getRole_WhenRoleNameDoesNotExist_ShouldThrowEntityNotFoundException() {
        RoleType admin = RoleType.ROLE_ADMIN;
        String expectedMessage = String.format("The role %s not exists", admin);

        when(roleRepository.findByRole(any(RoleType.class))).thenReturn(Optional.empty());

        Exception result = assertThrowsExactly(EntityNotFoundException.class, () -> roleService.getRole(admin));

        assertEquals(expectedMessage, result.getMessage());
    }
}