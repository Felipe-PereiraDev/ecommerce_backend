package br.com.felipedev.ecommerce.config;

import br.com.felipedev.ecommerce.enums.RoleType;
import br.com.felipedev.ecommerce.model.Person;
import br.com.felipedev.ecommerce.model.PersonFisica;
import br.com.felipedev.ecommerce.model.Role;
import br.com.felipedev.ecommerce.model.User;
import br.com.felipedev.ecommerce.repository.PersonRepository;
import br.com.felipedev.ecommerce.repository.RoleRepository;
import br.com.felipedev.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = roleRepository.findByRole(RoleType.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleType.ROLE_ADMIN)));

        Role roleUser = roleRepository.findByRole(RoleType.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleType.ROLE_USER)));

        User admin = new User();
        Person person = new PersonFisica();
        person.setId(2L);
        admin.setPassword("admin");
        admin.setUsername("admin");
        admin.setRoles(List.of(roleAdmin, roleUser));
        admin.setPasswordUpdatedAt(LocalDate.now());
        admin.setPerson(person);
        userRepository.findByUsername("admin")
                .orElseGet(() -> userRepository.save(admin));
    }
}
