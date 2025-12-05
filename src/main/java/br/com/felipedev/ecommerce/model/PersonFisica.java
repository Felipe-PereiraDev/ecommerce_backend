package br.com.felipedev.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "person_fisica", uniqueConstraints = {
        @UniqueConstraint(name = "cpf_uk", columnNames = {"cpf"})
})
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "person_fisica_fk", value = ConstraintMode.CONSTRAINT))
//@DiscriminatorValue("FISICA")
public class PersonFisica extends Person{
    @Column(nullable = false, length = 11)
    private String cpf;
    @Column(nullable = false)
    private LocalDateTime dateOfBirth;
}
