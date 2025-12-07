package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.dto.user.UserPFRequestDTO;
import br.com.felipedev.ecommerce.dto.user.UserPFResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserResponseDTO;
import br.com.felipedev.ecommerce.enums.RoleType;
import br.com.felipedev.ecommerce.exception.DuplicateResourceException;
import br.com.felipedev.ecommerce.mapper.RoleMapper;
import br.com.felipedev.ecommerce.mapper.UserMapper;
import br.com.felipedev.ecommerce.model.PersonFisica;
import br.com.felipedev.ecommerce.model.Role;
import br.com.felipedev.ecommerce.model.User;
import br.com.felipedev.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PersonFisicaService personFisicaService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> findAll() {
        return userMapper.toResponseDTOList(userRepository.findAll());
    }

    @Transactional
    public UserPFResponseDTO createUserPF(UserPFRequestDTO request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("The username %s is unavailable".formatted(request.username()));
        }
        PersonFisica createdPessoaFisica = personFisicaService.createPersonFisica(request);

        User newUser = new User();
        Role roleUser = roleService.getRole(RoleType.ROLE_USER);
        newUser.setUsername(request.username());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setPasswordUpdatedAt(LocalDate.now());
        newUser.setPerson(createdPessoaFisica);
        newUser.setRoles(List.of(roleUser));
        userRepository.save(newUser);
        return new UserPFResponseDTO(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getPassword(),
                createdPessoaFisica.getName(),
                createdPessoaFisica.getEmail(),
                createdPessoaFisica.getPhone(),
                createdPessoaFisica.getCpf(),
                createdPessoaFisica.getDateOfBirth(),
                roleMapper.toResponseDTOList(newUser.getRoles())
            );
    }

}
