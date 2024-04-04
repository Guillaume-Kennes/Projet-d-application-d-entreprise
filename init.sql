DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

CREATE TABLE pae.users(
                          id_user SERIAL PRIMARY KEY,
                          email VARCHAR(50) NOT NULL,
                          password VARCHAR(60) NOT NULL,
                          last_name VARCHAR(20) NOT NULL,
                          first_name VARCHAR(20) NOT NULL,
                          phone_number CHAR(13),
                          registration_date DATE NOT NULL,
                          role VARCHAR(15) NOT NULL,
                          version_users int NOT NULL
);

CREATE TABLE pae.enterprises(
                                id_enterprise SERIAL PRIMARY KEY,
                                trade_name VARCHAR(30) NOT NULL,
                                designation VARCHAR(60),
                                address VARCHAR(50) NOT NULL,
                                city VARCHAR(60) NOT NULL,
                                means_of_communication VARCHAR(50) NOT NULL,
                                is_black_listed boolean NOT NULL,
                                motivation_black_list VARCHAR(200),
                                version_enterprises int NOT NULL
);

CREATE TABLE pae.internship_supervisors(
                                           id_supervisor SERIAL PRIMARY KEY,
                                           enterprise INTEGER REFERENCES pae.enterprises (id_enterprise),
                                           supervisor_last_name VARCHAR(20) NOT NULL,
                                           supervisor_first_name VARCHAR(20) NOT NULL,
                                           phone_number VARCHAR(13),
                                           email CHAR(50),
                                           version_internship_surpervisors int NOT NULL
);

CREATE TABLE pae.inscriptions_ue(
                                    id_inscription_ue SERIAL PRIMARY KEY,
                                    student INTEGER REFERENCES pae.users (id_user),
                                    school_year VARCHAR(9) NOT NULL,
                                    version_inscriptions_ue int NOT NULL
);

CREATE TABLE pae.contacts(
                             id_contact SERIAL PRIMARY KEY,
                             state VARCHAR(15) NOT NULL,
                             enterprise INTEGER REFERENCES pae.enterprises (id_enterprise),
                             inscription_ue INTEGER REFERENCES pae.inscriptions_ue (id_inscription_ue),
                             reason_for_refusal  VARCHAR(200),
                             is_followed BOOLEAN NOT NULL,
                             meeting_place varchar(20),
                             version_contacts int NOT NULL
);

CREATE TABLE pae.internships(
                                id_internship SERIAL PRIMARY KEY,
                                contact INTEGER REFERENCES pae.contacts (id_contact),
                                internship_supervisor INTEGER REFERENCES pae.internship_supervisors (id_supervisor),
                                internship_project VARCHAR(50),
                                signature_date DATE NOT NULL,
                                version_internships int NOT NULL
);



/*ENTREPRISE
    Nom	            Appelation	Tel	            Adresse	                        Ville
    Assyst Europe		        02.609.25.00	Avenue du Japon, 1/B9	        1420 Braine-l'Alleud
    LetsBuild		            014 54 67 54	Chaussée de Bruxelles, 135A	    1310 La Hulpe
    Niboo		                0487 02 79 13	Boulevard du Souverain, 24	    1170 Watermael-Boisfort
    Sopra Steria		        02 566 66 66	Avenue Arnaud Fraiteur, 15/23	1050 Bruxelles
  */
INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('Assyst Europe', null, 'Avenue du Japon, 1/B9', '1420 Braine-l Alleud', '02.609.25.00', false, null, 1);
INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('LetsBuild', null, 'Chaussée de Bruxelles, 135A', '1310 La Hulpe', '014 54 67 54', false, null, 1);
INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('Niboo', null, 'Boulevard du Souverain, 24', '1170 Watermael-Boisfort', '0487 02 79 13', false, null, 1);
INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('Sopra Steria', null, 'Avenue Arnaud Fraiteur, 15/23', '1050 Bruxelles', '02 566 66 66', false, null, 1);


/*RESPONSABLE
    Nom	                Prénom	    Tel	            Email	                            Entreprise
    Dossche	            Stéphanie		            stephanie.dossche@letsbuild.com	    LetsBuild
    ALVAREZ CORCHETE	Roberto	    02.566.60.14		                                Sopra Steria
    Assal	            Farid	    0474 39 69 09	f.assal@assyst-europe.com	        Assyst Europe
  */
INSERT INTO pae.internship_supervisors (supervisor_last_name, supervisor_first_name, phone_number, email, enterprise, version_internship_surpervisors)
VALUES ('Dossche', 'Stéphanie', '014.54.67.54', 'stephanie.dossche@letsbuild.com', 2, 1);
INSERT INTO pae.internship_supervisors (supervisor_last_name, supervisor_first_name, phone_number, email, enterprise, version_internship_surpervisors)
VALUES ('ALVAREZ CORCHETE', 'Roberto', '02.566.60.14', null, 4, 1);
INSERT INTO pae.internship_supervisors (supervisor_last_name, supervisor_first_name, phone_number, email, enterprise, version_internship_surpervisors)
VALUES ('Assal', 'Farid', '0474 39 69 09', 'f.assal@assyst-europe.com', 1, 1);




/*UTILISATEUR
    Nom	        Prénom	    Tel	            Email	                        Role	        Date inscription	Année aca
    Baroni	    Raphaël	    0481 01 01 01	raphael.baroni@vinci.be	        Professeur	    21-09-20	        (Si nécessaire) 2020-2021           mdp prof : $2a$12$KSQlEHl/jc46zAp9FUO7QuMbMq/uUFj5T3jEKpmXeI5FnlIVnJ/Rm
    Lehmann	    Brigitte	0482 02 02 02	brigitte.lehmann@vinci.be	    Professeur	    21-09-20	        (Si nécessaire) 2020-2021
    Leleux	    Laurent	    0483 03 03 03	laurent.leleux@vinci.be	        Professeur	    21-09-20	        (Si nécessaire) 2020-2021
    Lancaster	Annouck	    0484 04 04 04	annouck.lancaster@vinci.be	    Administratif	21-09-20	        (Si nécessaire) 2020-2021           mdp admini : $2a$12$mnXAD6Me/1WPKdlxXcvR8uk4xMYT9VmcJSc1JtzlNrYCwRiJwXOrm
    Line	    Caroline	0486 00 00 01	Caroline.line@student.vinci.be	Etudiant	    18-09-23	        2023-2024                           mdp etudiant : $2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa
    Ile	        Achille	    0487 00 00 01	Ach.ile@student.vinci.be	    Etudiant	    18-09-23	        2023-2024
    Ile	        Basile	    0488 00 00 01	Basile.Ile@student.vinci.be	    Etudiant	    18-09-23	        2023-2024
    skile	    Achille	    0490 00 00 01	Achille.skile@student.vinci.be	Etudiant	    18-09-23	        2023-2024
    skile	    Carole	    0489 00 00 01	Carole.skile@student.vinci.be	Etudiant	    18-09-23	        2023-2024
  */
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('raphael.baroni@vinci.be', '$2a$12$KSQlEHl/jc46zAp9FUO7QuMbMq/uUFj5T3jEKpmXeI5FnlIVnJ/Rm', 'Baroni', 'Raphaël', '0481 01 01 01', '21-09-20', 'Professeur', 1);
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('brigitte.lehmann@vinci.be', '$2a$12$KSQlEHl/jc46zAp9FUO7QuMbMq/uUFj5T3jEKpmXeI5FnlIVnJ/Rm', 'Lehmann', 'Brigitte', '0482 02 02 02', '21-09-20', 'Professeur', 1);
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('laurent.leleux@vinci.be', '$2a$12$KSQlEHl/jc46zAp9FUO7QuMbMq/uUFj5T3jEKpmXeI5FnlIVnJ/Rm', 'Leleux', 'Laurent', '0483 03 03 03', '21-09-20', 'Professeur', 1);
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('annouck.lancaster@vinci.be', '$2a$12$mnXAD6Me/1WPKdlxXcvR8uk4xMYT9VmcJSc1JtzlNrYCwRiJwXOrm', 'Lancaster', 'Annouck', '0484 04 04 04', '21-09-20', 'Administratif', 1);
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('Caroline.line@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Line', 'Caroline', '0486 00 00 01', '18-09-23', 'Etudiant', 1);
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('Ach.ile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Ile', 'Achille', '0487 00 00 01', '18-09-23', 'Etudiant', 1);
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('Basile.Ile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Ile', 'Basile', '0488 00 00 01', '18-09-23', 'Etudiant', 1);
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('Achille.skile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'skile', 'Achille', '0490 00 00 01', '18-09-23', 'Etudiant', 1);
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('Carole.skile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'skile', 'Carole', '0489 00 00 01', '18-09-23', 'Etudiant', 1);


/*Contacts & stage
Année académique	Etudiant	                    Entreprise	    Etat contact / raison refus	                        Type de contact	    Sujet stage (si stage)	                    Date signature convention	Responsable stage
2023-2024	        Carole.skile@student.vinci.be	LetsBuild	    accepté	                                            A distance	        Un ERP : Odoo	                            10-10-23	                Dossche
2023-2024	        Ach.ile@student.vinci.be	    Sopra Steria	accepté	                                            Dans l'entreprise	sBMS project - a complex environment	    23-11-23	                ALVAREZ CORCHETE
2023-2024	        Ach.ile@student.vinci.be	    Sopra Steria	refusé (N'ont pas accepté d'avoir un entretien)	    A distance
2023-2024	        Basile.ile@student.vinci.be	    Assyst Europe	accepté	                                            Dans l'entreprise	CRM : Microsoft Dynamics 365 For Sales	    12-10-23	                Assal
2023-2024	        Basile.ile@student.vinci.be	    LetsBuild	    suspendu	                                        A distance
2023-2024	        Basile.ile@student.vinci.be	    Sopra Steria	suspendu
2023-2024	        Basile.ile@student.vinci.be	    Assyst Europe	refusé (ne prennent qu'un seul étudiant)	        Dans l'entreprise
2023-2024	        Caroline.line@student.vinci.be	Niboo	        pris	                                            A distance
2023-2024	        Caroline.line@student.vinci.be	Sopra Steria	initié
2023-2024	        Caroline.line@student.vinci.be	LetsBuild	    initié
2023-2024	        Achille.skile@student.vinci.be	Sopra Steria	initié
*/
INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (9, '2023-2024', 1);                                            /* 1 Carole skile */
INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (6, '2023-2024', 1);                                            /* 2 Achille Ile */
INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (7, '2023-2024', 1);                                            /* 3 Basille Ile */
INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (5, '2023-2024', 1);                                            /* 4 Caroline Line */
INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (8, '2023-2024', 1);                                            /* 5 Achille skile */

/* Entreprise
   1 : Assyst Europe,
   2 : LetsBuild,
   3 : Niboo,
   4 : Sopra Steria
*/

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (1, 2, 'accepté', null, 'A distance', TRUE, 1);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (2, 4, 'accepté', null, 'Dans l entreprise', TRUE, 1);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (2, 3, 'refusé', 'N ont pas accepté d avoir un entretien', 'A distance', TRUE, 1);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (3, 1, 'accepté', null, 'Dans l entreprise', TRUE, 1);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (3, 2, 'suspendu', null, 'A distance', TRUE, 1);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (3, 4, 'suspendu', null, null, TRUE, 1);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (3, 3, 'refusé', 'ne prennent qu un seul étudiant', 'Dans l entreprise', TRUE, 1);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (4, 3, 'pris', null, 'A distance', TRUE, 1);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (4, 4, 'initié', null, null, TRUE, 1);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (4, 2, 'initié', null, null, TRUE, 1);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (5, 4, 'initié', null, null, TRUE, 1);


INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (1, 1, 'Un ERP : Odoo', '10-10-23', 1);
INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (2, 2, 'sBMS project - a complex environment', '23-11-23', 1);
INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (4, 3, 'CRM : Microsoft Dynamics 365 For Sales', '12-10-23', 1);


/*
- Comptage du nombre d’utilisateurs.
- Comptage du nombre d’entreprises.
- Comptage du nombre de stages par année académique.
- Comptage du nombre de contacts par année académique.
- Etats (en format lisible par le client) et comptage du nombre de contacts dans chacun des états
*/

SELECT COUNT(*) AS nombre_utilisateurs
FROM pae.users;


SELECT COUNT(*) AS nombre_entreprises
FROM pae.enterprises;


SELECT school_year, COUNT(*) AS nombre_stages
FROM pae.inscriptions_ue i
         JOIN pae.contacts c ON i.id_inscription_ue = c.inscription_ue
         JOIN pae.internships s ON c.id_contact = s.contact
GROUP BY school_year;


SELECT school_year, COUNT(*) AS nombre_contacts
FROM pae.inscriptions_ue i
         JOIN pae.contacts c ON i.id_inscription_ue = c.inscription_ue
GROUP BY school_year;


SELECT
    CASE
        WHEN state = 'accepté' THEN 'Accepté'
        WHEN state = 'pris' THEN 'Pris'
        WHEN state = 'initié' THEN 'Initié'
        WHEN state = 'refusé' THEN 'Refusé'
        WHEN state = 'suspendu' THEN 'Suspendu'
        ELSE 'Autre'
        END AS etat_contact,
    COUNT(*) AS nombre_contacts
FROM pae.contacts
GROUP BY state;


















/*
initié -> pris ou suspendu

pris -> refusé ou accepté ou suspendu
*/


INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role)
VALUES ('chuqi.chups@student.vinci.be', '$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa', 'chuqi', 'chups', '04 666 666 66', CURRENT_DATE, 'Etudiant');

INSERT INTO pae.inscriptions_ue (student, school_year)
VALUES (10, '2023-2024');

INSERT INTO pae.contacts (state, enterprise, inscription_ue, reason_for_refusal, is_followed, meeting_place)
VALUES ('initié', 3, 6, null, true, null);



SELECT DISTINCT c.inscription_ue
FROM pae.users u, pae.contacts c, pae.inscriptions_ue i
WHERE u.id_user = i.student
  AND i.id_inscription_ue = c.inscription_ue
  AND u.id_user = 10;

SELECT e.id_enterprise
FROM pae.enterprises e
WHERE e.trade_name LIKE '%N%';

SELECT i.id_inscription_ue
FROM pae.users u, pae.inscriptions_ue i
WHERE u.id_user = i.student
  AND u.id_user = 10;
