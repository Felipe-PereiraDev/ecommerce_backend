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
@DiscriminatorValue("FISICA")
public class PersonFisica extends Person{
    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false)
    private LocalDateTime dateOfBirth;
}
