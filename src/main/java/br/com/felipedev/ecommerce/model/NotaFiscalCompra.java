package br.com.felipedev.ecommerce.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "nota_fiscal_compra", uniqueConstraints = {
        @UniqueConstraint(name = "number_uk", columnNames = {"number"}),
        @UniqueConstraint(name = "accounts_payable_uk", columnNames = {"accounts_payable_id"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotaFiscalCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    private String description;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discountAmount;

    @Column(name = "amount_icms", nullable = false)
    private BigDecimal amountIcms;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "person_fk", value = ConstraintMode.CONSTRAINT))
    private Person person;

    @OneToOne
    @JoinColumn(name = "accounts_payable_id", foreignKey = @ForeignKey(name = "accounts_payable_fk", value = ConstraintMode.CONSTRAINT))
    private AccountsPayable accountsPayable;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NotaFiscalCompra that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
