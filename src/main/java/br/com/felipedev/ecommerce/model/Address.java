package br.com.felipedev.ecommerce.model;

import br.com.felipedev.ecommerce.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String zipCode;
    public String street;
    public String number;
    public String complement;
    public String neighborhood;
    public String state;
    public String city;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    public Person person;

    @Enumerated(EnumType.STRING)
    public AddressType type;
}
