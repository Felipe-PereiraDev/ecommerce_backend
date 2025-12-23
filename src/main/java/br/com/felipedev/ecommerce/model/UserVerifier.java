package br.com.felipedev.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "users_verifier", uniqueConstraints =
    @UniqueConstraint(name = "user_verifier_token_uk", columnNames = {"token"})
)
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserVerifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "uv_user_fk", value = ConstraintMode.CONSTRAINT))
    private User user;

    @Column(nullable = false)
    private String token;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    public UserVerifier(String token, User user) {
        this.expirationDate = LocalDateTime.now().plusMinutes(15L);
        this.token = token;
        this.user = user;
    }
}
