DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

CREATE TABLE pae.utilisateurs(
                                 id_utilisateur SERIAL PRIMARY KEY,
                                 email VARCHAR(30) NOT NULL,
                                 mot_de_passe VARCHAR(60) NOT NULL,
                                 nom VARCHAR(50) NOT NULL,
                                 prenom VARCHAR(50) NOT NULL,
                                 telephone CHAR(13),
                                 date_inscription DATE NOT NULL,
                                 role NOT NULL IN ('étudiant', 'professeur', 'administratif');
);


INSERT INTO pae.utilisateurs (email, mot_de_passe, nom, prenom, telephone, date_inscription, role) VALUES ('chuqi.chups@student.vinci.be', 'Azertyui1_', 'chuqi', 'chups', '04 666 666 66', CURRENT_DATE, 'étudiant');