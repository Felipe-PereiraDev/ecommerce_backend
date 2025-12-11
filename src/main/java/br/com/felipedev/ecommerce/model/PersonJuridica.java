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
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "person_juridica_fk", value = ConstraintMode.CONSTRAINT))
//@DiscriminatorValue("JURIDICA")
public class PersonJuridica extends Person{
    @Column(nullable = false, length = 14)
    private String cnpj;
    private String stateRegistration;
    private String municipalRegistration;

    @Column(nullable = false)
    private String tradeName;
    @Column(nullable = false)
    private String corporateName;
    private String category;

    public PersonJuridica(String email, String name, String phone, String cnpj, String corporateName, String tradeName) {
        super(email, name, phone);
        this.cnpj = cnpj;
        this.corporateName = corporateName;
        this.tradeName = tradeName;
    }
}
