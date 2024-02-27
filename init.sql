DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

CREATE TABLE pae.users(
                                 id_user SERIAL PRIMARY KEY,
                                 email VARCHAR(30) NOT NULL,
                                 password VARCHAR(60) NOT NULL,
                                 last_name VARCHAR(50) NOT NULL,
                                 first_name VARCHAR(50) NOT NULL,
                                 phone_number CHAR(13),
                                 registration_date DATE NOT NULL,
                                 role VARCHAR(15) NOT NULL
);


INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role) VALUES ('chuqi.chups@student.vinci.be', 'Azertyui1_', 'chuqi', 'chups', '04 666 666 66', CURRENT_DATE, 'étudiant');