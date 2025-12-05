package br.com.felipedev.ecommerce.model;


import br.com.felipedev.ecommerce.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "payment_method",
        uniqueConstraints = @UniqueConstraint(
                name = "payment_type_uk",
                columnNames = {"payment_type"}
        )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
}
