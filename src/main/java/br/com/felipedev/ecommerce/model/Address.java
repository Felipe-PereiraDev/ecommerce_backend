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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    private String complement;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String city;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false, foreignKey = @ForeignKey(name = "person_fk"))
    private Person person;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType type;

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
