package br.com.felipedev.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "product_fk", value = ConstraintMode.CONSTRAINT))
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sale_purchase_id", nullable = false, foreignKey = @ForeignKey(name = "sale_purchase_fk", value = ConstraintMode.CONSTRAINT))
    private SalePurchase salePurchase;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private BigDecimal amount;

}
