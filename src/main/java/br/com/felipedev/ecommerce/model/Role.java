package br.com.felipedev.ecommerce.model;

import br.com.felipedev.ecommerce.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(name = "uk_role", columnNames = {"role"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleType role;

//    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
//    private List<User> users = new ArrayList<>();

    @Override
    public String getAuthority() {
        return role.name();
    }
}
