package br.com.felipedev.ecommerce.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "person_fisica", uniqueConstraints = {
        @UniqueConstraint(name = "cpf_uk", columnNames = {"cpf"})
})
@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "person_fisica_fk", value = ConstraintMode.CONSTRAINT))
//@DiscriminatorValue("FISICA")
public class PersonFisica extends Person{
    @Column(nullable = false, length = 11)
    private String cpf;
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    public PersonFisica(String name, String phone, String cpf, LocalDate dateOfBirth) {
        super(name, phone);
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
    }
}
