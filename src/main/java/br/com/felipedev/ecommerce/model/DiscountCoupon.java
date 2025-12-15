package br.com.felipedev.ecommerce.model;

import br.com.felipedev.ecommerce.dto.discountcoupon.DiscountCouponUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discount_coupon", uniqueConstraints = @UniqueConstraint(name = "dc_code_uk", columnNames = {"code"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DiscountCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String code;

    @Column(name = "discountAmount")
    private BigDecimal discountAmount;

    private BigDecimal percentage;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = true, foreignKey = @ForeignKey(name = "seller_fk", value = ConstraintMode.CONSTRAINT))
    private PersonJuridica seller;


    public DiscountCoupon(BigDecimal percentage, LocalDateTime dueDate, BigDecimal discountAmount, String code) {
        this.percentage = percentage;
        this.dueDate = dueDate;
        this.discountAmount = discountAmount;
        this.code = code;
    }

    public void update(DiscountCouponUpdateDTO request) {
        if (request.code() != null && !request.code().isBlank()) {
            this.code = request.code();
        }
        if (request.discountAmount() != null) {
            this.discountAmount = request.discountAmount();
        }
        if (request.percentage() != null) {
            this.percentage = request.percentage();
        }
        if (request.dueDate() != null) {
            this.dueDate = request.dueDate();
        }
    }
}
