package br.com.felipedev.ecommerce.mocks;

import br.com.felipedev.ecommerce.model.PersonJuridica;

public abstract class MockPerson {

    public static PersonJuridica mockPersonJuridica() {
        return new PersonJuridica(
                "admin@email.com",
                "admin",
                "1111-1111",
                "12345678912345",
                "Alfa Comércio e Distribuição Ltda.",
                "Alfa Distribuidora"
        );
    }

}
