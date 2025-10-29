package br.com.felipedev.ecommerce.model;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("JURIDICA")
public class PersonJuridica extends Person{
    @Column(nullable = false, unique = true)
    private String cnpj;
    public String stateRegistration;       // inscricaoEstadual
    public String municipalRegistration;   // incricaMunicipal
    public String tradeName;               // nomeFantasia
    public String corporateName;           // razaoSocial
    public String category;                // categoria
}
