package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.config.JwtServiceConfig;
import br.com.felipedev.ecommerce.dto.jwt.TokenResponseDTO;
import br.com.felipedev.ecommerce.dto.user.UserLogin;
import br.com.felipedev.ecommerce.dto.user.UserPFRequestDTO;
import br.com.felipedev.ecommerce.dto.user.UserPJRequestDTO;
import br.com.felipedev.ecommerce.dto.user.UserResponseDTO;
import br.com.felipedev.ecommerce.enums.RoleType;
import br.com.felipedev.ecommerce.enums.UserStatusEnum;
import br.com.felipedev.ecommerce.exception.DuplicateResourceException;
import br.com.felipedev.ecommerce.exception.ForbiddenException;
import br.com.felipedev.ecommerce.exception.InvalidLoginException;
import br.com.felipedev.ecommerce.mapper.RoleMapper;
import br.com.felipedev.ecommerce.mapper.UserMapper;
import br.com.felipedev.ecommerce.model.PersonFisica;
import br.com.felipedev.ecommerce.model.PersonJuridica;
import br.com.felipedev.ecommerce.model.Role;
import br.com.felipedev.ecommerce.model.User;
import br.com.felipedev.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    private final UserMapper userMapper;

    private final PersonFisicaService personFisicaService;

    private final PersonJuridicaService personJuridicaService;

    private final PasswordEncoder passwordEncoder;

    private final JwtServiceConfig jwtServiceConfig;

    private final AuthenticationManager authenticationManager;

    private final UserVerifierService userVerifierService;

    public TokenResponseDTO authentication(UserLogin userLogin ) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password())
            );
            String token = jwtServiceConfig.generateToken(authentication);
            return new TokenResponseDTO(token);
        } catch (DisabledException ex) {
            throw new ForbiddenException("Account is not active");
        }catch (BadCredentialsException ex) {
            throw new InvalidLoginException("Email or password invalid");
        }
    }

    @Transactional
    public UserResponseDTO createUserPF(UserPFRequestDTO request) {
        Optional<User> user = userRepository.findByEmail(request.email());

        if (user.isPresent() && user.get().getStatus() != UserStatusEnum.PENDING) {
            throw new DuplicateResourceException("The email %s is unavailable".formatted(request.email()));
        }

        if (user.isPresent()) {
            userVerifierService.issueVerificationToken(user.get());
            return new UserResponseDTO(user.get().getId());
        }

        PersonFisica createdPersonFisica = personFisicaService.createPersonFisica(request);

        Role roleUser = roleService.getRole(RoleType.ROLE_USER);
        User newUser = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                createdPersonFisica,
                List.of(roleUser)
        );
        userRepository.save(newUser);
        userVerifierService.issueVerificationToken(newUser);
        return new UserResponseDTO(newUser.getId());
    }

    @Transactional
    public UserResponseDTO createUserPJ(UserPJRequestDTO request) {
        Optional<User> user = userRepository.findByEmail(request.email());

        if (user.isPresent() && user.get().getStatus() != UserStatusEnum.PENDING) {
            throw new DuplicateResourceException("The email %s is unavailable".formatted(request.email()));
        }

        if (user.isPresent()) {
            userVerifierService.issueVerificationToken(user.get());
            return new UserResponseDTO(user.get().getId());
        }

        PersonJuridica createdPersonJuridica = personJuridicaService.createPersonJuridica(request);

        Role roleUser = roleService.getRole(RoleType.ROLE_USER);
        Role roleSeller = roleService.getRole(RoleType.ROLE_SELLER);
        User newUser = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                createdPersonJuridica,
                List.of(roleUser, roleSeller)
        );
        userRepository.save(newUser);
        return new UserResponseDTO(newUser.getId());
    }


    public List<User> getUsersWithExpiredPasswords() {
        return userRepository.findUsersWithExpiredPasswords(LocalDate.now().minusDays(90L));
    }
}
