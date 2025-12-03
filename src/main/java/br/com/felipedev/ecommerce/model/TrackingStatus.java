package br.com.felipedev.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tracking_status", uniqueConstraints = @UniqueConstraint(name = "ts_sale_purchase_uk", columnNames = {"sale_purchase_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String distributionCenter;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "sale_purchase_id", foreignKey = @ForeignKey(name = "sale_purchase_fk", value = ConstraintMode.CONSTRAINT))
    private SalePurchase salePurchase;

}
