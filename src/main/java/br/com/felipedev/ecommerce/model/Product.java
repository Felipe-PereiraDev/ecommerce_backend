package br.com.felipedev.ecommerce.model;

import br.com.felipedev.ecommerce.dto.product.ProductUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(name = "product_name_uk", columnNames = {"seller_id ", "name"}))
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

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false, foreignKey = @ForeignKey(name = "seller_fk", value = ConstraintMode.CONSTRAINT))
    private PersonJuridica seller;


    public Product(Brand brand, Category category, Double depth, String description, Double height, String name, BigDecimal price, Integer stockAlertQuantity, Integer stockQuantity, String unitType, Double weight, Double width, String youtubeLink, PersonJuridica seller) {
        this.brand = brand;
        this.category = category;
        this.depth = depth;
        this.description = description;
        this.height = height;
        this.name = name;
        this.price = price;
        this.stockAlertQuantity = stockAlertQuantity;
        this.stockQuantity = stockQuantity;
        this.unitType = unitType;
        this.weight = weight;
        this.width = width;
        this.youtubeLink = youtubeLink;
        this.seller = seller;
    }

    public void update(ProductUpdateDTO request, Brand brand, Category category) {
        if (request.name() != null && !request.name().isBlank()) {
            this.name = request.name();
        }
        if (request.price() != null) {
            this.price = request.price();
        }
        if (request.unitType() != null && !request.unitType().isBlank()) {
            this.unitType = request.unitType();
        }
        if (request.description() != null && !request.description().isBlank()) {
            this.description = request.description();
        }
        if (request.weight() != null) {
            this.weight = request.weight();
        }
        if (request.width() != null) {
            this.width = request.width();
        }
        if (request.height() != null) {
            this.height = request.height();
        }
        if (request.depth() != null) {
            this.depth = request.depth();
        }
        if (request.stockQuantity() != null) {
            this.stockQuantity = request.stockQuantity();
        }
        if (request.stockAlertQuantity() != null) {
            this.stockAlertQuantity = request.stockAlertQuantity();
        }
        if (request.youtubeLink() != null && request.youtubeLink().isBlank()) {
            this.youtubeLink = request.youtubeLink();
        }
        if (brand != null && brand.getId() != null) {
            this.brand = brand;
        }
        if (category != null && category.getId() != null) {
            this.category = category;
        }

    }

    //    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<ProductImage> images = new ArrayList<>();
}
