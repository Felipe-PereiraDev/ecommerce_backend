package br.com.felipedev.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nota_fiscal_venda", uniqueConstraints = {
        @UniqueConstraint(name = "nfv_number_uk", columnNames = {"number"}),
        @UniqueConstraint(name = "nfv_sale_purchase_uk", columnNames = {"sale_purchase_id"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotaFiscalVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String series;

    @Column(nullable = false)
    private String type;

    @Column(name = "xml", columnDefinition = "TEXT", nullable = false)
    private String XML;

    @Column(name = "pdf", columnDefinition = "TEXT", nullable = false)
    private String PDF;

    @OneToOne
    @JoinColumn(name = "sale_purchase_id", nullable = false, foreignKey = @ForeignKey(name = "sale_purchase_fk", value = ConstraintMode.CONSTRAINT))
    private SalePurchase salePurchase;


}
