package br.com.felipedev.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands", uniqueConstraints = @UniqueConstraint(name = "uk_brands_description", columnNames = {"description"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "brand_seq", sequenceName = "brand_seq", allocationSize = 1, initialValue = 1)
public class Brand implements Serializable {
    @Serial
    private static final long serialVersionUID  = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq")
    private Long id;

    @Column(name = "description", length = 50, nullable = false)
    private String description;

    @OneToMany(mappedBy = "brand")
    private List<Product> products = new ArrayList<>();

    public Brand(Long id, String description) {
        this.id = id;
        this.description = description;
    }
}
