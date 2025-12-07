package br.com.felipedev.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    public PersonFisica(String email, String name, String phone, String cpf, LocalDate dateOfBirth) {
        super(email, name, phone);
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
    }
}
