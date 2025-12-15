package br.com.felipedev.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String originalImage;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String thumbnailImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "product_fk", value = ConstraintMode.CONSTRAINT))
    public Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false, foreignKey = @ForeignKey(name = "seller_fk", value = ConstraintMode.CONSTRAINT))
    private PersonJuridica seller;



}
