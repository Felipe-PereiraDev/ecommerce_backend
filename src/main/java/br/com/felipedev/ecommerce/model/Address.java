package br.com.felipedev.ecommerce.model;

import br.com.felipedev.ecommerce.dto.address.AddressUpdateDTO;
import br.com.felipedev.ecommerce.dto.viacep.ViaCepResponse;
import br.com.felipedev.ecommerce.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    public void update(AddressUpdateDTO addressDTO, ViaCepResponse viaCepResponse) {
        if (addressDTO.number() != null && !addressDTO.number().isBlank()) {
            this.number = addressDTO.number();
        }
        if (addressDTO.complement() != null && !addressDTO.complement().isBlank()) {
            this.complement = addressDTO.complement();
        }
        if (viaCepResponse.cep() != null && !viaCepResponse.cep().isBlank()) {
            this.zipCode = viaCepResponse.cep();
        }
        if (viaCepResponse.logradouro() != null && !viaCepResponse.logradouro().isBlank()) {
            this.street = viaCepResponse.logradouro();
        }
        if (viaCepResponse.bairro() != null && !viaCepResponse.bairro().isBlank()) {
            this.neighborhood = viaCepResponse.bairro();
        }
        if (viaCepResponse.localidade() != null && !viaCepResponse.localidade().isBlank()) {
            this.city = viaCepResponse.localidade();
        }
        if (viaCepResponse.estado() != null && !viaCepResponse.estado().isBlank()) {
            this.state = viaCepResponse.estado();
        }
    }
}
