DROP SCHEMA IF EXISTS teretana;
CREATE SCHEMA teretana DEFAULT CHARACTER SET utf8;
USE teretana;

CREATE TABLE korisnici (
	id BIGINT AUTO_INCREMENT,
	korisnickoIme VARCHAR(50) NOT NULL,
	lozinka VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	ime VARCHAR(50) NOT NULL,
	prezime VARCHAR(50) NOT NULL,
	datumRodjenja DATETIME,
	adresa VARCHAR(50) NOT NULL,
	brojTelefona VARCHAR(50) NOT NULL,
	datumRegistracije DATETIME,
	administrator BOOL DEFAULT false,
	PRIMARY KEY(id)
);

CREATE TABLE treninzi (
	id BIGINT AUTO_INCREMENT,
	naziv VARCHAR(50) NOT NULL,
	trener VARCHAR(50) NOT NULL,
	kratakOpis VARCHAR(50) NOT NULL,
	cena INT NOT NULL,
	vrstaTreninga ENUM('pojedinacni', 'grupni') DEFAULT 'pojedinacni',
	nivoTreninga ENUM('amaterski', 'srednji', 'napredni') DEFAULT 'amaterski',
	trajanjeUMinutima INT NOT NULL,
	prosecnaOcena INT NOT NULL,
	zakazan BOOLEAN NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE treninziKorpa (
	id BIGINT AUTO_INCREMENT,
	naziv VARCHAR(50) NOT NULL,
	trener VARCHAR(50) NOT NULL,
	kratakOpis VARCHAR(50) NOT NULL,
	tipTreninga VARCHAR(50) NOT NULL,
	cena INT NOT NULL,
	vrstaTreninga VARCHAR(15) NOT NULL,
	nivoTreninga VARCHAR(15) NOT NULL,
	trajanjeUMinutima INT NOT NULL,
	prosecnaOcena INT NOT NULL,
	zakazan BOOLEAN NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE tipTreninga (
	id BIGINT AUTO_INCREMENT,
	naziv VARCHAR(50) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE treningTipTreninga (
    treningId BIGINT,
    tipTreningaId BIGINT,
    PRIMARY KEY(treningId, tipTreningaId),
    FOREIGN KEY(treningId) REFERENCES treninzi(id)
		ON DELETE CASCADE,
    FOREIGN KEY(tipTreningaId) REFERENCES tipTreninga(id)
		ON DELETE CASCADE
);

CREATE TABLE clanskeKarte (
	id BIGINT AUTO_INCREMENT,
	popust INT NOT NULL,
	brojPoena INT NOT NULL,
	registarskiBroj VARCHAR(50) NOT NULL,
	korisnik ENUM('pera', 'jova', 'mika') DEFAULT 'jova',
	status ENUM('prihvacen', 'uObradi', 'odbijen') DEFAULT 'prihvacen',
	PRIMARY KEY(id)
);

CREATE TABLE treninziClanskeKarte (
	treningId BIGINT,
	clanskaKartaId BIGINT,
	PRIMARY KEY(treningId, clanskaKartaId),
	FOREIGN KEY(treningId) REFERENCES treninziKorpa(id)
		ON DELETE CASCADE,
	FOREIGN KEY(clanskaKartaId) REFERENCES clanskeKarte(id)
		ON DELETE CASCADE
		
);

CREATE TABLE sale (
	id BIGINT AUTO_INCREMENT,
	oznaka INT NOT NULL,
	kapacitet INT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE termini (
	id BIGINT AUTO_INCREMENT,
	datumIVreme DATETIME,
	treningId BIGINT NOT NULL,
	sala INT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(treningId) REFERENCES treninzi(id)
		ON DELETE CASCADE
);

CREATE TABLE komentari (
	id BIGINT AUTO_INCREMENT,
	tekst VARCHAR(50) NOT NULL,
	ocena INT NOT NULL,
	datumIVreme DATETIME,
	treningId BIGINT NOT NULL,
	status ENUM('naCekanju', 'odobren', 'nijeOdobren') DEFAULT 'odobren',
	PRIMARY KEY(id),
	FOREIGN KEY(treningId) REFERENCES treninzi(id)
		ON DELETE CASCADE
);


INSERT INTO korisnici (korisnickoime, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, administrator) 
VALUES ('pera', 'pera123', 'pera@gmail.com', 'Pera', 'Peric', '1990-12-12 8:00', 'Ulica 1', '0638892283', '2022-12-15 11:00', true);

INSERT INTO korisnici (korisnickoime, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, administrator) 
VALUES ('mika@gmail.com', 'mika123', 'mika', 'Mika', 'Mikic', '1985-10-20 9:00', 'Ulica 2', '0638854898', '2022-12-16 12:00', false);

INSERT INTO korisnici (korisnickoime, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, administrator) 
VALUES ('jova@gmail.com', 'jova123', 'jova', 'Jova', 'Jovic', '1995-02-27 10:00', 'Ulica 3', '0637738912', '2022-12-17 13:00', false);


INSERT INTO treninzi (id, naziv, trener, kratakOpis, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan)
VALUES (1, 'trening1', 'trener1', 'Opis', 500, 'pojedinacni', 'amaterski', 60, 4, false);

INSERT INTO treninzi (id, naziv, trener, kratakOpis, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan)
VALUES (2, 'trening2', 'trener1', 'Opis', 800, 'pojedinacni', 'srednji', 60, 3, false);

INSERT INTO treninzi (id, naziv, trener, kratakOpis, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan)
VALUES (3, 'trening3', 'trener1', 'Opis', 1000, 'pojedinacni', 'napredni', 60, 5, false);

INSERT INTO tipTreninga (id, naziv) VALUES (1, 'Joga');
INSERT INTO tipTreninga (id, naziv) VALUES (2, 'Fitness');
INSERT INTO tipTreninga (id, naziv) VALUES (3, 'Cardio');

INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (1, 1);
INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (1, 2);
INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (1, 3);

INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (2, 1);
INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (2, 2);

INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (3, 1);

INSERT INTO treninziKorpa (id, naziv, trener, kratakOpis, tipTreninga, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan)
VALUES (1, 'trening1', 'trener1', 'Opis', 'Joga', 500, 'pojedinacni', 'amaterski', 60, 4, false);

INSERT INTO treninziKorpa (id, naziv, trener, kratakOpis, tipTreninga, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan)
VALUES (2, 'trening2', 'trener1', 'Opis', 'Fitness', 800, 'pojedinacni', 'srednji', 60, 3, false);

INSERT INTO treninziKorpa (id, naziv, trener, kratakOpis, tipTreninga, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan)
VALUES (3, 'trening3', 'trener1', 'Opis', 'Cardio', 1000, 'pojedinacni', 'napredni', 60, 5, false);

INSERT INTO clanskeKarte (id, popust, brojPoena, registarskiBroj, korisnik, status)
VALUES (1, 10, 5, 11, 'mika', 'prihvacen');
INSERT INTO clanskeKarte (id, popust, brojPoena, registarskiBroj, korisnik, status)
VALUES (2, 10, 5, 22, 'jova', 'prihvacen');

INSERT INTO treninziClanskeKarte (treningId, clanskaKartaId)
VALUES (1, 1);
INSERT INTO treninziClanskeKarte (treningId, clanskaKartaId)
VALUES (1, 2);

INSERT INTO sale (id, oznaka, kapacitet)
VALUES (1, 1, 30);
INSERT INTO sale (id, oznaka,kapacitet)
VALUES (2, 2, 30);
INSERT INTO sale (id, oznaka,kapacitet)
VALUES (3, 3, 30);

INSERT INTO termini (id, datumIVreme, treningId, sala)
VALUES (1, '2022-06-20 15:00', 1, 1);
INSERT INTO termini (id, datumIVreme, treningId, sala)
VALUES (2, '2022-06-21 17:00', 2, 1);
INSERT INTO termini (id, datumIVreme, treningId, sala)
VALUES (3, '2022-06-22 19:00', 3, 1);

INSERT INTO komentari (id, tekst, ocena, datumIVreme, treningId, status)
VALUES (1, 'tekst', 5, '2022-01-01 11:00', 1, 'odobren');
INSERT INTO komentari (id, tekst, ocena, datumIVreme, treningId, status)
VALUES (2, 'tekst', 4, '2022-01-02 12:00', 1, 'odobren');
INSERT INTO komentari (id, tekst, ocena, datumIVreme, treningId, status)
VALUES (3, 'tekst', 5, '2022-01-03 13:00', 1, 'odobren');



