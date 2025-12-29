ALTER TABLE users
ADD CONSTRAINT users_person_id_uk UNIQUE (person_id);
