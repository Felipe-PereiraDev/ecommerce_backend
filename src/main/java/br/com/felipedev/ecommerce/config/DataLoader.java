package br.com.felipedev.ecommerce.config;

import br.com.felipedev.ecommerce.enums.RoleType;
import br.com.felipedev.ecommerce.model.Role;
import br.com.felipedev.ecommerce.model.User;
import br.com.felipedev.ecommerce.repository.RoleRepository;
import br.com.felipedev.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        Role roleAdmin = roleRepository.findById(1L)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleType.ROLE_ADMIN)));

        Role roleUser = roleRepository.findById(2L)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleType.ROLE_USER)));

        List<Role> roles = List.of(roleAdmin, roleUser);

        if (!userRepository.existsByUsername("admin")) {
            User user = new User(null, "admin", "admin", LocalDate.now(), List.of());
            userRepository.save(user);
            System.out.println("✅ Usuário admin criado com sucesso!");
        }
    }

}
