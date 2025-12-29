package br.com.felipedev.ecommerce.config.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jwt.private-key-path}")
    private RSAPrivateKey privateKey;
    @Value("${jwt.public-key-path}")
    private RSAPublicKey publicKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/users/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/brands/**", "/categories/**", "/products/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/products/**").hasAnyRole("ADMIN", "SELLER")
                    .requestMatchers(HttpMethod.PUT,"/products/**").hasAnyRole("ADMIN", "SELLER")
                    .requestMatchers(HttpMethod.DELETE,"/products/**").hasAnyRole("ADMIN", "SELLER")

                    .requestMatchers(HttpMethod.POST,"/brands/**").hasAnyRole("ADMIN", "SELLER")
                    .requestMatchers(HttpMethod.PUT,"/brands/**").hasAnyRole("ADMIN", "SELLER")
                    .requestMatchers(HttpMethod.DELETE,"/brands/**").hasAnyRole("ADMIN", "SELLER")

                    .requestMatchers(HttpMethod.POST,"/categories/**").hasAnyRole("ADMIN", "SELLER")
                    .requestMatchers(HttpMethod.PUT,"/categories/**").hasAnyRole("ADMIN", "SELLER")
                    .requestMatchers(HttpMethod.DELETE,"/categories/**").hasAnyRole("ADMIN", "SELLER")

                    .requestMatchers(HttpMethod.GET,"/person/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.POST,"/person/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.PUT,"/person/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.DELETE,"/person/**").hasAnyRole("ADMIN", "USER")

                    .requestMatchers("/discount-coupon").hasAnyRole("ADMIN", "SELLER")

                    .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer(oauth2 ->
                    oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
            .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        RSAKey jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        ImmutableJWKSet<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> roles = jwt.getClaimAsStringList("roles");
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });
        return converter;
    }

}