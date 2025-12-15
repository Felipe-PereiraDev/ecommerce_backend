-- INSERE ROLES ESSENCIAIS
INSERT INTO "role" (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO "role" (id, role) VALUES (2, 'ROLE_USER');
INSERT INTO "role" (id, role) VALUES (3, 'ROLE_SELLER');

-- INSERE UMA PESSOA JURIDICA
INSERT INTO person (id, name, phone) VALUES (1, 'admin', '1111-1111');

INSERT INTO person_juridica(
    id, category, cnpj, corporate_name,
    municipal_registration, state_registration, trade_name
) VALUES (
    1, 'COMERCIO_ELETRONICO', '12345678912345',
    'Alfa Comércio e Distribuição Ltda.', '456789123',
    '123456789', 'Alfa Distribuidora'
);

-- INSERE UM USUARIO PARA PESSOA JURIDICA
INSERT INTO users (id, email, password, password_updated_at, person_id)
VALUES (
    1,
    'admin@email.com',
    '$2a$10$ZWf5Qg8avQBR251zWtP26.1pwf06sCeWMKGp.Eo6ShRbkxp7HEtzO',
    NOW(),
    1
);

-- INSERE ROLE AO USUARIO
INSERT INTO users_roles (user_id, role_id) VALUES (1, 3);