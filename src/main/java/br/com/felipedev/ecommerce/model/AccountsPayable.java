package br.com.felipedev.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "accounts_payable")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AccountsPayable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "discount_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal discountAmount;

    @Column(name = "due_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dueDate;

    @Column(name = "payment_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "fk_person", value = ConstraintMode.CONSTRAINT), nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "fk_supplier", value = ConstraintMode.CONSTRAINT), nullable = false)
    private Person supplier;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "fk_address", value = ConstraintMode.CONSTRAINT))
    private Address address;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", foreignKey = @ForeignKey(name = "fk_payment_method", value = ConstraintMode.CONSTRAINT), nullable = false)
    private PaymentMethod paymentMethod;

}
