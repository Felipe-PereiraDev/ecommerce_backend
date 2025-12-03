package br.com.felipedev.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discount_coupon", uniqueConstraints = @UniqueConstraint(name = "code_uk", columnNames = {"code"}))
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

}
