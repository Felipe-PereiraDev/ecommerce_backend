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
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        roleRepository.findByRole(RoleType.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleType.ROLE_ADMIN)));
        roleRepository.findByRole(RoleType.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleType.ROLE_USER)));
        roleRepository.findByRole(RoleType.ROLE_SELLER)
                .orElseGet(() -> roleRepository.save(new Role(null, RoleType.ROLE_SELLER)));
        System.out.println(passwordEncoder.encode("admin123"));
//        if (!userRepository.existsByUsername("admin")) {
//            Role roleAdmin = roleRepository.findByRole(RoleType.ROLE_ADMIN).orElseThrow();
//
//            Role roleUser = roleRepository.findByRole(RoleType.ROLE_USER).orElseThrow();
//            User admin = new User();
//            PersonFisica person = new PersonFisica();
//            person.setName("admin");
//            person.setEmail("admin@email.com");
//            person.setPhone("1234-5678");
//            person.setCpf("12345678910");
//            person.setDateOfBirth(LocalDate.now().minusYears(22));
//            personRepository.save(person);
//            admin.setPassword(passwordEncoder.encode("admin"));
//            admin.setUsername("admin");
//            admin.setRoles(List.of(roleAdmin, roleUser));
//            admin.setPasswordUpdatedAt(LocalDate.now());
//            admin.setPerson(person);
//        }
    }
}
