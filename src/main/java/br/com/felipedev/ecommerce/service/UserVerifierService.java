package br.com.felipedev.ecommerce.service;

import br.com.felipedev.ecommerce.enums.UserStatusEnum;
import br.com.felipedev.ecommerce.exception.BadRequestException;
import br.com.felipedev.ecommerce.exception.ConflictException;
import br.com.felipedev.ecommerce.exception.TokenExpiredException;
import br.com.felipedev.ecommerce.model.User;
import br.com.felipedev.ecommerce.model.UserVerifier;
import br.com.felipedev.ecommerce.repository.UserRepository;
import br.com.felipedev.ecommerce.repository.UserVerifierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserVerifierService {

    private final EmailService emailService;

    private final UserRepository userRepository;

    private final UserVerifierRepository userVerifierRepository;

    public void issueVerificationToken(User user) {
        userVerifierRepository.deleteByUser(user);
        String baseUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toString();

        String token = UUID.randomUUID().toString();
        String fullUrl = String.format("%s/users/verify-token?token=%s", baseUrl, token);

        UserVerifier userVerifier = new UserVerifier(token, user);
        userVerifierRepository.save(userVerifier);
        emailService.sendToken(user, fullUrl);
    }

    public void validateVerificationToken(String token) {
        UserVerifier userVerifier = userVerifierRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid verification token"));

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(userVerifier.getExpirationDate())) {
             throw new TokenExpiredException("Verification token has expired");
        }

        User user = userVerifier.getUser();
        if (user.getStatus() == UserStatusEnum.ACTIVE) {
            throw new ConflictException("Account has already been verified");
        }
        user.setStatus(UserStatusEnum.ACTIVE);
        userRepository.save(user);
        userVerifierRepository.delete(userVerifier);
    }
}
