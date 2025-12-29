package br.com.felipedev.ecommerce.config.security;

import br.com.felipedev.ecommerce.enums.RoleType;
import br.com.felipedev.ecommerce.exception.EntityNotFoundException;
import br.com.felipedev.ecommerce.model.Person;
import br.com.felipedev.ecommerce.model.User;
import br.com.felipedev.ecommerce.repository.PersonRepository;
import br.com.felipedev.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserContextService {
    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    private Jwt getTokenAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof JwtAuthenticationToken jwtAuth)) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado");
        }
        return jwtAuth.getToken();
    }

    public User getAuthenticatedUser() {
        Jwt jwt = getTokenAuthenticatedUser();
        String email = jwt.getSubject();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public Person getAuthenticatedPerson() {
        Jwt token = getTokenAuthenticatedUser();
        Long personId = token.getClaim("personId");

        if (personId == null) {
            throw new AccessDeniedException("Authentication credentials are incomplete or invalid");
        }

        return personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
    }

    public void ensureUserOwnsResource(Long userId) {
        User authenticatedUser = getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
    }

    public Long getAuthenticatedSellerId() {
        Jwt token = getTokenAuthenticatedUser();
        List<String> roles = token.getClaimAsStringList("roles");
        Long userId = token.getClaim("personId");

        if (userId == null || roles == null) {
            throw new AccessDeniedException("Authentication credentials are incomplete or invalid");
        }

        if (!roles.contains(RoleType.ROLE_SELLER.name())) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
        return userId;
    }

    public void ensureSellerOwnsResource(Long ownerSellerId) {
        Long expectedSellerId = getAuthenticatedSellerId();
        if  (!expectedSellerId.equals(ownerSellerId)) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
    }

    public void ensureSellerOwnsBrandAndCategory(Long expectedSellerId, Long brandSellerId, Long categorySellerId) {
        if (!(expectedSellerId.equals(brandSellerId) && expectedSellerId.equals(categorySellerId))){
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
    }

}
