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
                          role VARCHAR(15) NOT NULL
);

CREATE TABLE pae.entreprises(
                                id_enterprise SERIAL PRIMARY KEY,
                                trade_name VARCHAR(30) NOT NULL,
                                designation VARCHAR(60),
                                adress VARCHAR(50) NOT NULL,
                                means_of_communication VARCHAR(50) NOT NULL,
                                is_black_listed boolean NOT NULL,
                                motivation_black_list VARCHAR(200)
);

CREATE TABLE pae.internship_supervisors(
                                           id_supervisor SERIAL PRIMARY KEY,
                                           entreprise INTEGER REFERENCES pae.entreprises (id_enterprise),
                                           last_name VARCHAR(20) NOT NULL,
                                           first_name VARCHAR(20) NOT NULL,
                                           phone_number VARCHAR(13),
                                           email CHAR(50)
);

CREATE TABLE pae.inscriptions_UE(
                                    id_inscription_UE SERIAL PRIMARY KEY,
                                    student INTEGER REFERENCES pae.users (id_user),
                                    school_year VARCHAR(9) NOT NULL
);

CREATE TABLE pae.contacts(
                             id_contact SERIAL PRIMARY KEY,
                             state VARCHAR(15) NOT NULL,
                             enterprise INTEGER REFERENCES pae.entreprises (id_enterprise),
                             inscription_UE INTEGER REFERENCES pae.inscriptions_UE (id_inscription_UE),
                             reason_for_refusal  VARCHAR(200),
                             is_followed BOOLEAN NOT NULL,
                             meeting_place varchar(20)
);

CREATE TABLE pae.internships(
                                id_internship SERIAL PRIMARY KEY,
                                contact INTEGER REFERENCES pae.contacts (id_contact),
                                internship_supervisor INTEGER REFERENCES pae.internship_supervisors (id_supervisor),
                                internship_project VARCHAR(50),
                                signature_date DATE NOT NULL
);



/*ENTREPRISE
    Nom	            Appelation	Tel	            Adresse	                        Ville
    Assyst Europe		        02.609.25.00	Avenue du Japon, 1/B9	        1420 Braine-l'Alleud
    LetsBuild		            014 54 67 54	Chaussée de Bruxelles, 135A	    1310 La Hulpe
    Niboo		                0487 02 79 13	Boulevard du Souverain, 24	    1170 Watermael-Boisfort
    Sopra Steria		        02 566 66 66	Avenue Arnaud Fraiteur, 15/23	1050 Bruxelles
  */
INSERT INTO pae.entreprises (trade_name, designation, adress, means_of_communication, is_black_listed, motivation_black_list)
VALUES ('Assyst Europe', null, 'Avenue du Japon, 1/B9', '02.609.25.00', false, null);
INSERT INTO pae.entreprises (trade_name, designation, adress, means_of_communication, is_black_listed, motivation_black_list)
VALUES ('LetsBuild', null, 'Chaussée de Bruxelles, 135A', '014 54 67 54', false, null);
INSERT INTO pae.entreprises (trade_name, designation, adress, means_of_communication, is_black_listed, motivation_black_list)
VALUES ('Niboo', null, 'Chaussée de Bruxelles, 135A', '014 54 67 54', false, null);
INSERT INTO pae.entreprises (trade_name, designation, adress, means_of_communication, is_black_listed, motivation_black_list)
VALUES ('Sopra Steria', null, 'Avenue Arnaud Fraiteur, 15/23', '02 566 66 66', false, null);


/*RESPONSABLE
    Nom	                Prénom	    Tel	            Email	                            Entreprise
    Dossche	            Stéphanie		            stephanie.dossche@letsbuild.com	    LetsBuild
    ALVAREZ CORCHETE	Roberto	    02.566.60.14		                                Sopra Steria
    Assal	            Farid	    0474 39 69 09	f.assal@assyst-europe.com	        Assyst Europe
  */
INSERT INTO pae.internship_supervisors (last_name, first_name, phone_number, email, entreprise)
VALUES ('Dossche', 'Stéphanie', '014.54.67.54', 'stephanie.dossche@letsbuild.com', 2);
INSERT INTO pae.internship_supervisors (last_name, first_name, phone_number, email, entreprise)
VALUES ('ALVAREZ CORCHETE', 'Roberto', '02.566.60.14', null, 4);
INSERT INTO pae.internship_supervisors (last_name, first_name, phone_number, email, entreprise)
VALUES ('Assal', 'Farid', '0474 39 69 09', 'f.assal@assyst-europe.com', 1);




/*UTILISATEUR
    Nom	        Prénom	    Tel	            Email	                        Role	        Date inscription	Année aca
    Baroni	    Raphaël	    0481 01 01 01	raphael.baroni@vinci.be	        Professeur	    21-09-20	        (Si nécessaire) 2020-2021
    Lehmann	    Brigitte	0482 02 02 02	brigitte.lehmann@vinci.be	    Professeur	    21-09-20	        (Si nécessaire) 2020-2021
    Leleux	    Laurent	    0483 03 03 03	laurent.leleux@vinci.be	        Professeur	    21-09-20	        (Si nécessaire) 2020-2021
    Lancaster	Annouck	    0484 04 04 04	annouck.lancaster@vinci.be	    Administratif	21-09-20	        (Si nécessaire) 2020-2021
    Line	    Caroline	0486 00 00 01	Caroline.line@student.vinci.be	Etudiant	    18-09-23	        2023-2024
    Ile	        Achille	    0487 00 00 01	Ach.ile@student.vinci.be	    Etudiant	    18-09-23	        2023-2024
    Ile	        Basile	    0488 00 00 01	Basile.Ile@student.vinci.be	    Etudiant	    18-09-23	        2023-2024
    skile	    Achille	    0490 00 00 01	Achille.skile@student.vinci.be	Etudiant	    18-09-23	        2023-2024
    skile	    Carole	    0489 00 00 01	Carole.skile@student.vinci.be	Etudiant	    18-09-23	        2023-2024
  */
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role)
VALUES ('raphael.baroni@vinci.be', '$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa', 'Baroni', 'Raphaël', '0481 01 01 01', '21-09-20', 'Professeur');
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role)
VALUES ('brigitte.lehmann@vinci.be', '$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa', 'Lehmann', 'Brigitte', '0482 02 02 02', '21-09-20', 'Professeur');
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role)
VALUES ('laurent.leleux@vinci.be', '$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa', 'Leleux', 'Laurent', '0483 03 03 03', '21-09-20', 'Professeur');
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role)
VALUES ('annouck.lancaster@vinci.be', '$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa', 'Lancaster', 'Annouck', '0484 04 04 04', '21-09-20', 'Administratif');
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role)
VALUES ('Caroline.line@student.vinci.be', '$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa', 'Line', 'Caroline', '0486 00 00 01', '18-09-23', 'Etudiant');
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role)
VALUES ('Ach.ile@student.vinci.be', '$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa', 'Ile', 'Achille', '0487 00 00 01', '18-09-23', 'Etudiant');
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role)
VALUES ('Basile.Ile@student.vinci.be', '$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa', 'Ile', 'Basile', '0488 00 00 01', '18-09-23', 'Etudiant');
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role)
VALUES ('Achille.skile@student.vinci.be', '$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa', 'skile', 'Achille', '0490 00 00 01', '18-09-23', 'Etudiant');
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role)
VALUES ('Carole.skile@student.vinci.be', '$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa', 'skile', 'Carole', '0489 00 00 01', '18-09-23', 'Etudiant');


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
INSERT INTO pae.inscriptions_UE (student, school_year)
VALUES (9, '2023-2024');
INSERT INTO pae.inscriptions_UE (student, school_year)
VALUES (6, '2023-2024');
INSERT INTO pae.inscriptions_UE (student, school_year)
VALUES (7, '2023-2024');
INSERT INTO pae.inscriptions_UE (student, school_year)
VALUES (5, '2023-2024');
INSERT INTO pae.inscriptions_UE (student, school_year)
VALUES (8, '2023-2024');

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed)
VALUES (1, 2, 3, null, 'A distance', TRUE);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed)
VALUES (2, 4, 3, null, 'Dans l entreprise', TRUE);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed)
VALUES (2, 3, 4, 'N ont pas accepté d avoir un entretien', 'A distance', TRUE);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed)
VALUES (3, 1, 3, null, 'Dans l entreprise', TRUE);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed)
VALUES (3, 2, 5, null, 'A distance', TRUE);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed)
VALUES (3, 4, 5, null, null, TRUE);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed)
VALUES (3, 3, 4, 'ne prennent qu un seul étudiant', 'Dans l entreprise', TRUE);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed)
VALUES (4, 3, 2, null, 'A distance', TRUE);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed)
VALUES (4, 4, 1, null, null, TRUE);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed)
VALUES (4, 2, 1, null, null, TRUE);
INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed)
VALUES (5, 4, 1, null, null, TRUE);


INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date)
VALUES (1, 1, 'Un ERP : Odoo', '10-10-23');
INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date)
VALUES (2, 2, 'sBMS project - a complex environment', '23-11-23');
INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date)
VALUES (4, 3, 'CRM : Microsoft Dynamics 365 For Sales', '12-10-23');





INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role)
VALUES ('chuqi.chups@student.vinci.be', '$2a$10$EjatwHeWXjlLk/TfJEE.ieP6v54EMqeQyVeox4Xvax6nV9WJShcRa', 'chuqi', 'chups', '04 666 666 66', CURRENT_DATE, 'étudiant');