package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.enums.RoleType;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.model.Role;
import br.com.felipedev.ecommerce.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public Role getRole(RoleType roleType) {
        return roleRepository.findByRole(roleType)
                .orElseThrow(
                        () -> new EntityNotFoundException("The role %s not exists".formatted(roleType.name()))
                );
    }
}
