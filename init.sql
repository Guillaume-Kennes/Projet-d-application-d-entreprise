DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

CREATE TABLE pae.users(
    id_user SERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(60) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    phone_number CHAR(18),
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
    phone_number VARCHAR(18),
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
    version_contacts int
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
1   Assyst Europe		        02.609.25.00	Avenue du Japon, 1/B9	        1420 Braine-l'Alleud
2   AXIS SRL		            02 752 17 60	Avenue de l'Hélianthe, 63	    1180 Uccle
3   Infrabel		            02 525 22 11	Rue Bara, 135	                1070 Bruxelles
4   La route du papier		    02 586 16 65	Avenue des Mimosas, 83	        1150 Woluwe-Saint-Pierre
5   LetsBuild		            014 54 67 54	Chaussée de Bruxelles, 135A	    1310 La Hulpe
6   Niboo		                0487 02 79 13	Boulevard du Souverain, 24	    1170 Watermael-Boisfort
7   Sopra Steria		        02 566 66 66	Avenue Arnaud Fraiteur, 15/23	1050 Bruxelles
8   The Bayard Partnership		02 309 52 45	Grauwmeer, 1/57 bte 55	        3001 Leuven
  */
INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('Assyst Europe', null, 'Avenue du Japon, 1/B9', '1420 Braine-l Alleud', '02.609.25.00', false, null, 1);

INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('AXIS SRL', null, 'Avenue de l''Hélianthe, 63', '1180 Uccle', '02 752 17 60', false, null, 1);

INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('Infrabel', null, 'Rue Bara, 135', '1070 Bruxelles', '02 525 22 11', false, null, 1);

INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('La route du papier', null, 'Avenue des Mimosas, 83', '1150 Woluwe-Saint-Pierre', '02 586 16 65', false, null, 1);

INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('LetsBuild', null, 'Chaussée de Bruxelles, 135A', '1310 La Hulpe', '014 54 67 54', false, null, 1);

INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('Niboo', null, 'Boulevard du Souverain, 24', '1170 Watermael-Boisfort', '0487 02 79 13', false, null, 1);

INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('Sopra Steria', null, 'Avenue Arnaud Fraiteur, 15/23', '1050 Bruxelles', '02 566 66 66', false, null, 1);

INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('The Bayard Partnership', null, 'Grauwmeer, 1/57 bte 55', '3001 Leuven', '02 309 52 45', false, null, 1);

/*RESPONSABLE
    Nom	                Prénom	    Tel	            Email	                            Entreprise
1   Dossche	            Stéphanie		            stephanie.dossche@letsbuild.com	    LetsBuild                   5
2   ALVAREZ CORCHETE	Roberto	    02.566.60.14		                                Sopra Steria                7
3   Assal	            Farid	    0474 39 69 09	f.assal@assyst-europe.com	        Assyst Europe               1
4   Ile	                Emile	    0489 32 16 54		                                La route du papier          4
5   Hibo	            Owln	    0456 678 567		                                Infrabel                    3
6   Barn	            Henri	    02 752 17 60		                                AXIS SRL                    2
  */
INSERT INTO pae.internship_supervisors (supervisor_last_name, supervisor_first_name, phone_number, email, enterprise, version_internship_surpervisors)
VALUES ('Dossche', 'Stéphanie', '014.54.67.54', 'stephanie.dossche@letsbuild.com', 5, 1);

INSERT INTO pae.internship_supervisors (supervisor_last_name, supervisor_first_name, phone_number, email, enterprise, version_internship_surpervisors)
VALUES ('ALVAREZ CORCHETE', 'Roberto', '02.566.60.14', null, 7, 1);

INSERT INTO pae.internship_supervisors (supervisor_last_name, supervisor_first_name, phone_number, email, enterprise, version_internship_surpervisors)
VALUES ('Assal', 'Farid', '0474 39 69 09', 'f.assal@assyst-europe.com', 1, 1);

INSERT INTO pae.internship_supervisors (supervisor_last_name, supervisor_first_name, phone_number, email, enterprise, version_internship_surpervisors)
VALUES ('Ile', 'Emile', '0489 32 16 54', null, 4, 1);

INSERT INTO pae.internship_supervisors (supervisor_last_name, supervisor_first_name, phone_number, email, enterprise, version_internship_surpervisors)
VALUES ('Hibo', 'Owln', '0456 678 567', null, 3, 1);

INSERT INTO pae.internship_supervisors (supervisor_last_name, supervisor_first_name, phone_number, email, enterprise, version_internship_surpervisors)
VALUES ('Barn', 'Henri', '02 752 17 60', null, 2, 1);


/*UTILISATEUR
    Nom	        Prénom	    Tel	            Email	                            Role	        Date inscription	Année aca
1   Baroni	    Raphaël 	0481 01 01 01	raphael.baroni@vinci.be	            Professeur	    21-09-20	        (Si nécessaire) 2020-2021       mdp prof : $2a$12$KSQlEHl/jc46zAp9FUO7QuMbMq/uUFj5T3jEKpmXeI5FnlIVnJ/Rm
2   Lehmann	    Brigitte	0482 02 02 02	brigitte.lehmann@vinci.be	        Professeur	    21-09-20        	(Si nécessaire) 2020-2021
3   Leleux	    Laurent	    0483 03 03 03	laurent.leleux@vinci.be 	        Professeur	    21-09-20        	(Si nécessaire) 2020-2021
4   Lancaster	Annouck	    0484 04 04 04	annouck.lancaster@vinci.be         	Administratif	21-09-20        	(Si nécessaire) 2020-2021       mdp admini : $2a$12$mnXAD6Me/1WPKdlxXcvR8uk4xMYT9VmcJSc1JtzlNrYCwRiJwXOrm
5   skile	    Elle	    0491 00 00 01	elle.skile@student.vinci.be       	Etudiant    	21-09-21        	2021-2022                       mdp etudiant : $2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa
6   Ilotie	    Basile	    0491 00 00 11	Basile.Ilotie@student.vinci.be  	Etudiant    	21-09-21        	2021-2022
7   Frilot	    Basile	    0491 00 00 21	Basile.frilot@student.vinci.be  	Etudiant    	21-09-21        	2021-2022
8   Ilot	    Basile	    0492 00 00 01	Basile.Ilot@student.vinci.be    	Etudiant    	21-09-21        	2021-2022
9   dito	    Arnaud	    0493 00 00 01	Arnaud.dito@student.vinci.be    	Etudiant    	21-09-21        	2021-2022
10  dilo	    Arnaud	    0494 00 00 01	Arnaud.dilo@student.vinci.be    	Etudiant    	21-09-21    	    2021-2022
11  dilot	    Cedric	    0495 00 00 01	Cedric.dilot@student.vinci.be   	Etudiant    	21-09-21    	    2021-2022
12  linot	    Auristelle	0496 00 00 01	Auristelle.linot@student.vinci.be	Etudiant    	21-09-21        	2021-2022
13  demoulin	Basile	    0496 00 00 02	basile.demoulin@student.vinci.be	Etudiant    	23-09-22        	2022-2023
14  moulin	    Arthur	    0497 00 00 02	Arthur.moulin@student.vinci.be	    Etudiant    	23-09-22        	2022-2023
15  moulin	    Hugo	    0497 00 00 03	Hugo.moulin@student.vinci.be	    Etudiant    	23-09-22        	2022-2023
16  demoulin	Jeremy	    0497 00 00 20	Jeremy.demoulin@student.vinci.be	Etudiant    	23-09-22        	2022-2023
17  mile	    Aurèle	    0497 00 00 21	Aurèle.mile@student.vinci.be    	Etudiant    	23-09-22    	    2022-2023
18  mile	    Frank	    0497 00 00 75	Frank.mile@student.vinci.be     	Etudiant    	27-09-22	        2022-2023
19  dumoulin	Basile  	0497 00 00 58	basile.dumoulin@student.vinci.be   	Etudiant    	27-09-22	        2022-2023
20  dumoulin	Axel	    0497 00 00 97	Axel.dumoulin@student.vinci.be  	Etudiant    	27-09-22        	2022-2023
21  Line	    Caroline   	0486 00 00 01	Caroline.line@student.vinci.be  	Etudiant    	18-09-23        	2023-2024
22  Ile	        Achille 	0487 00 00 01	Ach.ile@student.vinci.be    	    Etudiant    	18-09-23        	2023-2024
23  Ile	        Basile  	0488 00 00 01	Basile.Ile@student.vinci.be	        Etudiant	    18-09-23        	2023-2024
24  skile	    Achille 	0490 00 00 01	Achille.skile@student.vinci.be  	Etudiant    	18-09-23         	2023-2024
25  skile	    Carole  	0489 00 00 01	Carole.skile@student.vinci.be   	Etudiant    	18-09-23        	2023-2024
26  Ile	        Théophile	0488 35 33 89	theophile.ile@student.vinci.be  	Etudiant    	01-03-24    	    2023-2024

  */
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('raphael.baroni@vinci.be', '$2a$12$KSQlEHl/jc46zAp9FUO7QuMbMq/uUFj5T3jEKpmXeI5FnlIVnJ/Rm', 'Baroni', 'Raphaël', '0481 01 01 01', '21-09-20', 'Professeur', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('brigitte.lehmann@vinci.be', '$2a$12$KSQlEHl/jc46zAp9FUO7QuMbMq/uUFj5T3jEKpmXeI5FnlIVnJ/Rm', 'Lehmann', 'Brigitte', '0482 02 02 02', '21-09-20', 'Professeur', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('laurent.leleux@vinci.be', '$2a$12$KSQlEHl/jc46zAp9FUO7QuMbMq/uUFj5T3jEKpmXeI5FnlIVnJ/Rm', 'Leleux', 'Laurent', '0483 03 03 03', '21-09-20', 'Professeur', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('annouck.lancaster@vinci.be', '$2a$12$mnXAD6Me/1WPKdlxXcvR8uk4xMYT9VmcJSc1JtzlNrYCwRiJwXOrm', 'Lancaster', 'Annouck', '0484 04 04 04', '21-09-20', 'Administratif', 1);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('elle.skile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Skile', 'Elle', '0491 00 00 01', '21-09-21', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('basile.ilotie@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Ilotie', 'Basile', '0491 00 00 11', '21-09-21', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('basile.frilot@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Frilot', 'Basile', '0491 00 00 21', '21-09-21', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('basile.ilot@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Ilot', 'Basile', '0492 00 00 01', '21-09-21', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('arnaud.dito@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Dito', 'Arnaud', '0493 00 00 01', '21-09-21', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('arnaud.dilo@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Dilo', 'Arnaud', '0494 00 00 01', '21-09-21', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('cedric.dilot@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Dilot', 'Cedric', '0495 00 00 01', '21-09-21', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('auristelle.linot@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Linot', 'Auristelle', '0496 00 00 01', '21-09-21', 'Etudiant', 1);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('basile.demoulin@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Demoulin', 'Basile', '0496 00 00 02', '23-09-22', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('arthur.moulin@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Moulin', 'Arthur', '0497 00 00 02', '23-09-22', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('hugo.moulin@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Moulin', 'Hugo', '0497 00 00 03', '23-09-22', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('jeremy.demoulin@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Demoulin', 'Jeremy', '0497 00 00 20', '23-09-22', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('aurele.mile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Mile', 'Aurèle', '0497 00 00 21', '23-09-22', 'Etudiant', 1);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('frank.mile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Mile', 'Frank', '0497 00 00 75', '27-09-22', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('basile.dumoulin@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Dumoulin', 'Basile', '0497 00 00 58', '27-09-22', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('axel.dumoulin@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Dumoulin', 'Axel', '0497 00 00 97', '27-09-22', 'Etudiant', 1);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('caroline.line@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Line', 'Caroline', '0486 00 00 01', '18-09-23', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('ach.ile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Ile', 'Achille', '0487 00 00 01', '18-09-23', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('basile.Ile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Ile', 'Basile', '0488 00 00 01', '18-09-23', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('achille.skile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Skile', 'Achille', '0490 00 00 01', '18-09-23', 'Etudiant', 1);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('carole.skile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Skile', 'Carole', '0489 00 00 01', '18-09-23', 'Etudiant', 1);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, role, version_users)
VALUES ('theophile.ile@student.vinci.be', '$2a$12$apxeP3MlY5R7IGkxYc/H4.WzjnPeJnPO45tWMt2dZ4mukoXt.xdwa', 'Ile', 'Théophile', '0488 35 33 89', '01-03-24', 'Etudiant', 1);




/*Contacts & stage
Année académique	Etudiant	                    Entreprise	    Etat contact / raison refus	                        Type de contact	    Sujet stage (si stage)	                    Date signature convention	Responsable stage
2023-2024	        Carole.skile@student.vinci.be	LetsBuild	    accepté	                                            A distance	        Un ERP : Odoo	                            10-10-23	                Dossche
2023-2024	        Ach.ile@student.vinci.be	    Sopra Steria	accepté	                                            Dans l'entreprise	sBMS project - a complex environment	    23-11-23	                ALVAREZ CORCHETE
2023-2024   	    Ach.ile@student.vinci.be    	Niboo	        refusé (N'ont pas accepté d'avoir un entretien)	    A distance
2023-2024   	    Basile.ile@student.vinci.be 	Assyst Europe	accepté	                                            Dans l'entreprise	CRM : Microsoft Dynamics 365 For Sales	    12-10-23	                Assal
2023-2024   	    Basile.ile@student.vinci.be 	LetsBuild	    suspendu	                                        A distance
2023-2024       	Basile.ile@student.vinci.be 	Sopra Steria	suspendu
2023-2024       	Basile.ile@student.vinci.be 	Niboo	        refusé (ne prennent qu'un seul étudiant)	        Dans l'entreprise
2023-2024       	Caroline.line@student.vinci.be	Niboo	        refusé (Pas d’affinité avec le l’ERP Odoo)	        A distance
2023-2024       	Caroline.line@student.vinci.be	Sopra Steria	non suivi
2023-2024          	Caroline.line@student.vinci.be	LetsBuild	    pris	                                            A distance
2023-2024       	theophile.ile@student.vinci.be	Sopra Steria	initié
2023-2024   	    theophile.ile@student.vinci.be	Niboo	        initié
2023-2024          	theophile.ile@student.vinci.be	LetsBuild	    initié
2023-2024	        Achille.skile@student.vinci.be	Sopra Steria	initié

Année académique	Etudiant	                        Entreprise	            Etat contact / raison refus	                        Type de contact	    Sujet stage (si stage)	                    Date signature convention	Responsable stage
2021-2022	        elle.skile@student.vinci.be	        La route du papier	    accepté	                                            A distance	        Conservation et restauration d’œuvres d’art	25-11-21	                Ile
2021-2022       	Basile.Ilot@student.vinci.be	    Sopra Steria	        non suivi
2021-2022	        Basile.frilot@student.vinci.be	    The Bayard Partnership	refusé (ne prennent pas de stage)	                A distance
2021-2022	        Arnaud.dito@student.vinci.be	    Sopra Steria	        accepté	                                            Dans l'entreprise	L'analyste au centre du développement	    17-11-21	                ALVAREZ CORCHETE
2021-2022	        Arnaud.dilo@student.vinci.be	    Sopra Steria	        accepté	                                            Dans l'entreprise	L'analyste au centre du développement	    17-11-21	                ALVAREZ CORCHETE
2021-2022	        Cedric.dilot@student.vinci.be	    Assyst Europe	        accepté	                                            Dans l'entreprise	ERP : Microsoft Dynamics 366	            23-11-21	                Assal
2021-2022	        Cedric.dilot@student.vinci.be	    Sopra Steria	        refusé (Choix autre étudiant)	                    Dans l'entreprise
2021-2022	        Auristelle.linot@student.vinci.be	Infrabel	            accepté	                                            A distance	        Entretien des rails	                        22-11-21	                Hibo
2021-2022	        Auristelle.linot@student.vinci.be	Sopra Steria	        suspendu
2021-2022	        Auristelle.linot@student.vinci.be	Niboo	                refusé (Choix autre étudiant)	                    A distance

Année académique	Etudiant	                        Entreprise	    Etat contact / raison refus	                        Type de contact	    Sujet stage (si stage)	                    Date signature convention	Responsable stage
2022-2023	        Jeremy.demoulin@student.vinci.be	Assyst Europe	accepté	                                            A distance	        CRM : Microsoft Dynamics 365 For Sales	    23-11-22	                Assal
2022-2023	        Arthur.moulin@student.vinci.be	    AXIS SRL	    accepté	                                            Dans l'entreprise	Un métier : chef de projet	                19-10-22	                Barn
2022-2023	        Hugo.moulin@student.vinci.be	    AXIS SRL	    accepté	                                            Dans l'entreprise	Un métier : chef de projet	                19-10-22	                Barn
2022-2023	        Aurèle.mile@student.vinci.be	    AXIS SRL	    accepté	                                            A distance	        Un métier : chef de projet	                19-10-22	                Barn
2022-2023	        Frank.mile@student.vinci.be     	AXIS SRL	    accepté	                                            A distance	        Un métier : chef de projet	                19-10-22	                Barn
2022-2023	        basile.dumoulin@student.vinci.be	AXIS SRL	    refusé (Entretien n'a pas eu lieu)	                Dans l'entreprise
2022-2023	        basile.dumoulin@student.vinci.be	Niboo	        refusé (Entretien n'a pas eu lieu)	                Dans l'entreprise
2022-2023	        basile.dumoulin@student.vinci.be	Sopra Steria	refusé (Entretien n'a pas eu lieu)	                A distance
2022-2023	        Axel.dumoulin@student.vinci.be	    Sopra Steria	accepté	                                            A distance	        sBMS project - Java Development	            17-10-22	                ALVAREZ CORCHETE
2022-2023	        Basile.frilot@student.vinci.be	    Sopra Steria	refusé (Choix autre étudiant)	                    A distance

*/

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (5, '2021-2022', 1);                                             /* 1 Elle skile */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (6, '2023-2024', 1);                                             /* 2 Basile Ilotie */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (7, '2023-2024', 1);                                             /* 3 Basile Frilot */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (8, '2023-2024', 1);                                             /* 4 Ilot Basile */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (9, '2023-2024', 1);                                             /* 5 dito Arnaud */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (10, '2023-2024', 1);                                             /* 6 dilo Arnaud */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (11, '2023-2024', 1);                                             /* 7 dilot Cedric */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (12, '2023-2024', 1);                                             /* 8 linot	Auristelle */

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (13, '2022-2023', 1);                                             /* 9 demoulin Basile */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (14, '2022-2023', 1);                                             /* 10 moulin Arthur */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (15, '2022-2023', 1);                                             /* 11 moulin Hugo */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (16, '2022-2023', 1);                                             /* 12 demoulin Jeremy */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (17, '2022-2023', 1);                                             /* 13 mile Aurèle */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (18, '2022-2023', 1);                                             /* 14 mile Frank */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (19, '2022-2023', 1);                                             /* 15 dumoulin Basile */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (20, '2022-2023', 1);                                             /* 16 dumoulin Axel */

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (25, '2023-2024', 1);                                            /* 17 Carole skile */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (22, '2023-2024', 1);                                            /* 18 Achille Ile */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (23, '2023-2024', 1);                                            /* 19 Basille Ile */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (21, '2023-2024', 1);                                            /* 20 Caroline Line */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (24, '2023-2024', 1);                                            /* 21 Achille skile */

INSERT INTO pae.inscriptions_ue (student, school_year, version_inscriptions_ue)
VALUES (26, '2023-2024', 1);                                            /* 22 Théophile Ile */




/* Entreprise
    1 : Assyst Europe,
    2 : AXIS SRL,
    3 : Infrabel,
    4 : La route du papier,
    5 : LetsBuild,
    6 : Niboo,
    7 : Sopra Steria,
    8 : The Bayard Partnership
*/

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (1, 4, 'accepté', null, 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (4, 7, 'non suivi', null, null, FALSE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (3, 8, 'refusé', 'ne prennent pas de stage', 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (5, 7, 'accepté', null, 'Dans l''entreprise', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (6, 7, 'accepté', null, 'Dans l''entreprise', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (7, 1, 'accepté', null, 'Dans l''entreprise', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (7, 7, 'refusé', 'Choix autre étudiant', 'Dans l''entreprise', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (8, 3, 'accepté', 'Choix autre étudiant', 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (8, 7, 'suspendu', 'Choix autre étudiant', null, TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (8, 6, 'refusé', 'Choix autre étudiant', 'A distance', TRUE, 1);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (12, 1, 'accepté', null, 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (10, 2, 'accepté', null, 'Dans l''entreprise', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (11, 2, 'accepté', null, 'Dans l''entreprise', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (13, 2, 'accepté', null, 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (14, 2, 'accepté', null, 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (15, 2, 'refusé', 'Entretien n''a pas eu lieu', 'Dans l''entreprise', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (15, 6, 'refusé', 'Entretien n''a pas eu lieu', 'Dans l''entreprise', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (15, 7, 'refusé', 'Entretien n''a pas eu lieu', 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (16, 7, 'accepté', null, 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (3, 7, 'refusé', 'Choix autre étudiant', 'A distance', TRUE, 1);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (17, 5, 'accepté', null, 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (18, 7, 'accepté', null, 'Dans l entreprise', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (18, 6, 'refusé', 'N ont pas accepté d avoir un entretien', 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (19, 1, 'accepté', null, 'Dans l entreprise', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (19, 5, 'suspendu', null, 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (19, 7, 'suspendu', null, null, TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (19, 6, 'refusé', 'ne prennent qu un seul étudiant', 'Dans l entreprise', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (20, 6, 'refusé', 'Pas d’affinité avec le l’ERP Odoo', 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (20, 7, 'non suivi', null, null, FALSE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (20, 5, 'pris', null, 'A distance', TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (22, 7, 'initié', null, null, TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (22, 6, 'initié', null, null, TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (22, 5, 'initié', null, null, TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (21, 7, 'initié', null, null, TRUE, 1);







/* Supervisor
    1 : Dossche,
    2 : ALVAREZ CORCHETE,
    3 : Assal,
    4 : Ile,
    5 : Hibo,
    6 : Barn,
*/

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (1, 4, 'Conservation et restauration d’œuvres d’art', '25-11-21', 1);

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (4, 2, 'L''analyste au centre du développement', '17-11-21', 1);

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (5, 2, 'L''analyste au centre du développement', '17-11-21', 1);

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (5, 3, 'ERP : Microsoft Dynamics 366', '23-11-21', 1);

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (8, 5, 'Entretien des rails', '22-11-21', 1);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (11, 3, 'CRM : Microsoft Dynamics 365 For Sales', '23-11-22', 1);

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (12, 6, 'Un métier : chef de projet', '19-10-22', 1);

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (13, 6, 'Un métier : chef de projet', '19-10-22', 1);

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (14, 6, 'Un métier : chef de projet', '19-10-22', 1);

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (15, 6, 'Un métier : chef de projet', '19-10-22', 1);

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (19, 2, 'sBMS project - Java Development', '17-10-22', 1);

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (21, 1, 'Un ERP : Odoo', '10-10-23', 1);

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (22, 2, 'sBMS project - a complex environment', '23-11-23', 1);

INSERT INTO pae.internships (contact, internship_supervisor, internship_project, signature_date, version_internships)
VALUES (24, 3, 'CRM : Microsoft Dynamics 365 For Sales', '12-10-23', 1);








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
        WHEN state = 'non suivi' THEN 'Non suivi'
        ELSE 'Autre'
    END AS etat_contact,
    COUNT(*) AS nombre_contacts
FROM pae.contacts
GROUP BY state;






/*NEW*/
/*
1. Comptage du nombre d’utilisateurs, par rôle et par année académique.
2. Année académique et comptage du nombre de stages par année académique.
3. Entreprise, année académique, et comptage du nombre de stages par entreprise et année
académique.
4. Année académique et comptage du nombre de contacts par année académique.
5. Etats (en format lisible par le client) et comptage du nombre de contacts dans chacun des
états.
6. Année académique, états (en format lisible par le client) et comptage du nombre de contacts
dans chacun des états par année académique.
7. Entreprise, états (en format lisible par le client) et comptage du nombre de contacts dans
chacun des états par entreprise.
*/

/*1*/
SELECT role, EXTRACT(YEAR FROM registration_date) AS année_académique, COUNT(*) AS nombre_d_utilisateurs
FROM pae.users
GROUP BY role, EXTRACT(YEAR FROM registration_date)
ORDER BY role, EXTRACT(YEAR FROM registration_date);


/*2*/
SELECT EXTRACT(YEAR FROM signature_date) AS année_académique, COUNT(*) AS nombre_de_stages
FROM pae.internships
GROUP BY EXTRACT(YEAR FROM signature_date)
ORDER BY EXTRACT(YEAR FROM signature_date);


/*3*/
SELECT e.trade_name AS enterprise, EXTRACT(YEAR FROM i.signature_date) AS année_académique, COUNT(*) AS nombre_de_stages
FROM pae.internships i
JOIN pae.internship_supervisors s ON i.internship_supervisor = s.id_supervisor
JOIN pae.enterprises e ON s.enterprise = e.id_enterprise
GROUP BY e.trade_name, EXTRACT(YEAR FROM i.signature_date)
ORDER BY e.trade_name, EXTRACT(YEAR FROM i.signature_date);


/*4*/
SELECT EXTRACT(YEAR FROM u.registration_date) AS année_académique, COUNT(*) AS nombre_de_contacts
FROM pae.contacts c
JOIN pae.inscriptions_ue iu ON c.inscription_ue = iu.id_inscription_ue
JOIN pae.users u ON iu.student = u.id_user
GROUP BY EXTRACT(YEAR FROM u.registration_date)
ORDER BY EXTRACT(YEAR FROM u.registration_date);


/*5*/
SELECT
    CASE
        WHEN state = 'initié' THEN 'Initié'
        WHEN state = 'pris' THEN 'Pris'
        WHEN state = 'accepté' THEN 'Accepté'
        WHEN state = 'refusé' THEN 'Refusé'
        WHEN state = 'suspendu' THEN 'Suspendu'
        WHEN state = 'non suivi' THEN 'Non suivi'
        ELSE state
    END AS etat_contact,
    COUNT(*) AS nombre_de_contacts
FROM pae.contacts
GROUP BY state
ORDER BY state;


/*6*/
SELECT
    EXTRACT(YEAR FROM u.registration_date) AS année_académique,
    CASE
        WHEN state = 'initié' THEN 'Initié'
        WHEN state = 'pris' THEN 'Pris'
        WHEN state = 'accepté' THEN 'Accepté'
        WHEN state = 'refusé' THEN 'Refusé'
        WHEN state = 'suspendu' THEN 'Suspendu'
        WHEN state = 'non suivi' THEN 'Non suivi'
        ELSE state
    END AS state_description,
    COUNT(*) AS nombre_de_contacts
FROM pae.contacts c
JOIN pae.inscriptions_ue iu ON c.inscription_ue = iu.id_inscription_ue
JOIN pae.users u ON iu.student = u.id_user
GROUP BY EXTRACT(YEAR FROM u.registration_date), c.state
ORDER BY EXTRACT(YEAR FROM u.registration_date), c.state;


/*7*/
SELECT
    e.trade_name AS enterprise,
    CASE
        WHEN state = 'initié' THEN 'Initié'
        WHEN state = 'pris' THEN 'Pris'
        WHEN state = 'accepté' THEN 'Accepté'
        WHEN state = 'refusé' THEN 'Refusé'
        WHEN state = 'suspendu' THEN 'Suspendu'
        WHEN state = 'non suivi' THEN 'Non suivi'
        ELSE state
    END AS state_description,
    COUNT(*) AS contact_count
FROM pae.contacts c
JOIN pae.enterprises e ON c.enterprise = e.id_enterprise
GROUP BY e.trade_name, c.state
ORDER BY e.trade_name, c.state;








/*
initié -> pris ou suspendu

pris -> refusé ou accepté ou suspendu
*/

/*
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


 */



/*
INSERT INTO pae.enterprises (trade_name, designation, address, city, means_of_communication, is_black_listed, motivation_black_list, version_enterprises)
VALUES ('Infrabel', 'I-ICT Ring station', 'Rue des deux gares 82', '1070 Bruxelles', '02.212.88.88', false, null, 1);


INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (22, 9, 'initié', null, null, TRUE, 1);


INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (2, 9, 'initié', null, null, TRUE, 1);


INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (4, 9, 'initié', null, null, TRUE, 1);


INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (3, 9, 'initié', null, null, TRUE, 1);

INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (3, 3, 'initié', null, null, TRUE, 1);



INSERT INTO pae.contacts (inscription_ue, enterprise, state, reason_for_refusal, meeting_place, is_followed, version_contacts)
VALUES (15, 3, 'initié', null, null, TRUE, 1);



 */

