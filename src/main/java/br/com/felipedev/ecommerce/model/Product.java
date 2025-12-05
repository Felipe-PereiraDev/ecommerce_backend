package br.com.felipedev.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "products", uniqueConstraIntegers = {@UniqueConstraInteger(columnNames = {"seller_id ", "name"})})
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "unit_type", length = 50)
    private String unitType;

    @Column(name = "description", columnDefinition = "text", length = 2000)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Double depth;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "stock_alert_quantity")
    private Integer stockAlertQuantity = 0;

    @Column(name = "stock_alert_enabled")
    private Boolean stockAlertEnabled = false;

    @Column(name = "youtube_link", length = 1000)
    private String youtubeLink;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false, foreignKey = @ForeignKey(name = "brand_fk", value = ConstraintMode.CONSTRAINT))
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "category_fk", value = ConstraintMode.CONSTRAINT))
    private Category category;

    private Integer clickCount = 0;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<ProductImage> images = new ArrayList<>();
}
