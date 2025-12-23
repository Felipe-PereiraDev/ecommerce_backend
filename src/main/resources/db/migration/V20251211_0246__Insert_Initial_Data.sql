-- ROLES
INSERT INTO "role" (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO "role" (id, role) VALUES (2, 'ROLE_USER');
INSERT INTO "role" (id, role) VALUES (3, 'ROLE_SELLER');

-- PAYMENT METHODS
INSERT INTO payment_method (id, payment_type) VALUES (1, 'CREDIT_CARD');
INSERT INTO payment_method (id, payment_type) VALUES (2, 'DEBIT_CARD');
INSERT INTO payment_method (id, payment_type) VALUES (3, 'BANK_SLIP');
INSERT INTO payment_method (id, payment_type) VALUES (4, 'PIX');
