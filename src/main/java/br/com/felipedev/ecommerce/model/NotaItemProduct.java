package br.com.felipedev.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "nota_item_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotaItemProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "product_fk", value = ConstraintMode.CONSTRAINT))
    private Product product;

    @ManyToOne
    @JoinColumn(name = "nota_fiscal_compra_id", nullable = false, foreignKey = @ForeignKey(name = "nota_fiscal_compra_fk", value = ConstraintMode.CONSTRAINT))
    private NotaFiscalCompra notaFiscalCompra;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false, foreignKey = @ForeignKey(name = "seller_fk", value = ConstraintMode.CONSTRAINT))
    private PersonJuridica seller;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NotaItemProduct that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
