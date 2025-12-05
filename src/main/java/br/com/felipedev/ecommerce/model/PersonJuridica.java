package br.com.felipedev.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "person_juridica", uniqueConstraints = {
        @UniqueConstraint(name = "cnpj_uk", columnNames = {"cnpj"})
})
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "person_juridica_fk", value = ConstraintMode.CONSTRAINT))
//@DiscriminatorValue("JURIDICA")
public class PersonJuridica extends Person{
    @Column(nullable = false, length = 14)
    private String cnpj;
    public String stateRegistration;
    public String municipalRegistration;

    @Column(nullable = false)
    public String tradeName;
    @Column(nullable = false)
    public String corporateName;
    public String category;
}
