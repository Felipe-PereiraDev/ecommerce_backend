package br.com.felipedev.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category", uniqueConstraints = @UniqueConstraint(name = "category_description_uk", columnNames = {"description"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "category_seq", sequenceName = "category_seq", allocationSize = 1, initialValue = 1)
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID  = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    private Long id;

    @Column(name = "description", length = 50, nullable = false)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false, foreignKey = @ForeignKey(name = "seller_fk", value = ConstraintMode.CONSTRAINT))
    private PersonJuridica seller;



    public Category(Long id, String description) {
        this.id = id;
        this.description = description;
    }


}
