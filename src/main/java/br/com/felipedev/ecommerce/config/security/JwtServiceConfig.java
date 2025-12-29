package br.com.felipedev.ecommerce.config.security;

import br.com.felipedev.ecommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class JwtServiceConfig {
    @Autowired
    private JwtEncoder encoder;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 86400L;

        User user = (User) authentication.getPrincipal();
        Long userId = user.getPerson().getId();

        List<String> scopes = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        var claims = JwtClaimsSet.builder()
                .issuer("ecommerce-security-jwt")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("personId", userId)
                .claim("roles", scopes)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}