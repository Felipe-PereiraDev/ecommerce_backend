package br.com.felipedev.ecommerce.model;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Table(name = "person_juridica", uniqueConstraints = {
        @UniqueConstraint(name = "cnpj_uk", columnNames = {"cnpj"})
})
@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
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

    public PersonJuridica(String name, String phone, String category, String cnpj, String corporateName, String municipalRegistration, String stateRegistration, String tradeName) {
        super(name, phone);
        this.category = category;
        this.cnpj = cnpj;
        this.corporateName = corporateName;
        this.municipalRegistration = municipalRegistration;
        this.stateRegistration = stateRegistration;
        this.tradeName = tradeName;
    }


}
