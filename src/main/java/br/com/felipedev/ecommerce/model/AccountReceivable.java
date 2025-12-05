package br.com.felipedev.ecommerce.model;

import br.com.felipedev.ecommerce.enums.ReceivableStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "account_receivable", uniqueConstraints = @UniqueConstraint(name = "ar_sale_purchase_uk", columnNames = {"sale_purchase_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AccountReceivable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReceivableStatus status;

    @Column(name = "due_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dueDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @ManyToOne
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "person_fk", value = ConstraintMode.CONSTRAINT))
    private Person person;

    @OneToOne
    @JoinColumn(name = "sale_purchase_id", foreignKey = @ForeignKey(name = "sale_purchase_fk", value = ConstraintMode.CONSTRAINT))
    private SalePurchase salePurchase;


}
