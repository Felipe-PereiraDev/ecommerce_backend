package br.com.felipedev.ecommerce.model;

import br.com.felipedev.ecommerce.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @Column(nullable = false)
    public String zipCode;

    @Column(nullable = false)
    public String street;

    @Column(nullable = false)
    public String number;

    public String complement;
    @Column(nullable = false)
    public String neighborhood;

    @Column(nullable = false)
    public String state;

    @Column(nullable = false)
    public String city;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false, foreignKey = @ForeignKey(name = "person_fk"))
    public Person person;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public AddressType type;
}
