package br.com.felipedev.ecommerce.model;

import br.com.felipedev.ecommerce.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "account_payable")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AccountPayable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "person_fk", value = ConstraintMode.CONSTRAINT), nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "supplier_fk", value = ConstraintMode.CONSTRAINT), nullable = false)
    private Person supplier;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "address_fk", value = ConstraintMode.CONSTRAINT))
    private Address address;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", foreignKey = @ForeignKey(name = "payment_method_fk", value = ConstraintMode.CONSTRAINT), nullable = false)
    private PaymentMethod paymentMethod;

}
