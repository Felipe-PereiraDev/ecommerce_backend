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
//@Table(name = "products", uniqueConstraints = {@UniqueConstraint(columnNames = {"seller_id ", "name"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", length = 100)
    public String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    public BigDecimal price;

    @Column(name = "unit_type", length = 50)
    public String unitType;

    @Column(name = "description", length = 2000)
    public String description;

    public double weight;

    public double width;

    public double height;

    public double depth;

    @Column(name = "stock_quantity", nullable = false)
    public int stockQuantity;

    @Column(name = "stock_alert_quantity")
    public int stockAlertQuantity;

    @Column(name = "stock_alert_enabled")
    public boolean stockAlertEnabled;

    @Column(name = "youtube_link", length = 1000)
    public String youtubeLink;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    public Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    public Category category;

    public int clickCount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductImage> images = new ArrayList<>();
}
