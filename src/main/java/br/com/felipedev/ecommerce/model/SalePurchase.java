package br.com.felipedev.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sale_purchase")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalePurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false, foreignKey = @ForeignKey(name = "person_fk", value = ConstraintMode.CONSTRAINT))
    private Person person;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id", nullable = false,foreignKey = @ForeignKey(name = "delivery_address_fk", value = ConstraintMode.CONSTRAINT))
    private Address deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "billing_address_id", foreignKey = @ForeignKey(name = "billing_address_fk", value = ConstraintMode.CONSTRAINT))
    private Address billingAddress;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false, foreignKey = @ForeignKey(name = "payment_method_fk", value = ConstraintMode.CONSTRAINT))
    private PaymentMethod paymentMethod;

    @OneToOne(mappedBy = "salePurchase")
//    @JoinColumn(name = "nota_fiscal_venda_id", nullable = false, foreignKey = @ForeignKey(name = "nota_fiscal_venda_fk", value = ConstraintMode.CONSTRAINT))
    private NotaFiscalVenda notaFiscalVenda;

    @ManyToOne
    @JoinColumn(name = "discount_coupon_id", foreignKey = @ForeignKey(name = "discount_coupon_fk", value = ConstraintMode.CONSTRAINT))
    private DiscountCoupon discountCoupon;

    @Column(name = "shipping_cost", nullable = false)
    private BigDecimal shippingCost;

    @Column(nullable = false)
    private Integer deliveryDays;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @OneToMany(mappedBy = "salePurchase", fetch = FetchType.LAZY)
    private List<TrackingStatus> trackingStatusList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false, foreignKey = @ForeignKey(name = "seller_fk", value = ConstraintMode.CONSTRAINT))
    private PersonJuridica seller;


}
