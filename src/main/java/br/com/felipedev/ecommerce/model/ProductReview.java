package br.com.felipedev.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_review")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false, foreignKey = @ForeignKey(name = "person_fk", value = ConstraintMode.CONSTRAINT))
    private Person person;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "product_fk", value = ConstraintMode.CONSTRAINT))
    private Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false, foreignKey = @ForeignKey(name = "seller_fk", value = ConstraintMode.CONSTRAINT))
    private PersonJuridica seller;



}
