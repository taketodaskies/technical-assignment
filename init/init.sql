CREATE TABLE IF NOT EXISTS pets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    age INTEGER,
    species VARCHAR(50) NOT NULL,
    owner_name VARCHAR(50)
);

INSERT INTO pets (name, age, species, owner_name) VALUES ('Fido', 5, 'Dog', 'Robert');
INSERT INTO pets (name, age, species, owner_name) VALUES ('Roger', 1, 'Rabbit', '');
INSERT INTO pets (name, age, species, owner_name) VALUES ('Sam', 0, 'Cat', 'Erica');
