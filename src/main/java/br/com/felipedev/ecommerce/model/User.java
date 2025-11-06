package br.com.felipedev.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_username", columnNames = {"username"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String password;

    @Temporal(TemporalType.DATE)
    private LocalDate passwordUpdatedAt;

//    private Person person;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            uniqueConstraints = @UniqueConstraint(columnNames =
                    {"user_id", "role_id"},
                    name = "unique_role_user"
            ),
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    table = "users",
                    foreignKey = @ForeignKey(name = "fk_user", value = ConstraintMode.CONSTRAINT)
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id",
                    table = "roles",
                    foreignKey = @ForeignKey(name = "fk_role", value = ConstraintMode.CONSTRAINT)
            )
    )
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
