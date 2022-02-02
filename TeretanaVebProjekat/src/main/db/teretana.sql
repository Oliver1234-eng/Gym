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
	datumRodjenja DATE,
	adresa VARCHAR(50) NOT NULL,
	brojTelefona VARCHAR(50) NOT NULL,
	datumRegistracije DATE,
	uloga VARCHAR(15) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE treninzi (
	id BIGINT AUTO_INCREMENT,
	naziv VARCHAR(50) NOT NULL,
	trener VARCHAR(50) NOT NULL,
	kratakOpis VARCHAR(50) NOT NULL,
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
	opis VARCHAR(50) NOT NULL,
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


INSERT INTO korisnici (korisnickoime, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, Uloga) 
VALUES ('pera@gmail.com', 'pera123', 'pera', 'Pera', 'Peric', '1990-12-12', 'Ulica 1', '0638892283', '2022-12-15', 'administrator');

INSERT INTO korisnici (korisnickoime, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, Uloga) 
VALUES ('mika@gmail.com', 'mika123', 'mika', 'Mika', 'Mikic', '1985-10-20', 'Ulica 2', '0638854898', '2022-12-16', 'administrator');

INSERT INTO korisnici (korisnickoime, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, Uloga) 
VALUES ('jova@gmail.com', 'jova123', 'jova', 'Jova', 'Jovic', '1995-02-27', 'Ulica 3', '0637738912', '2022-12-17', 'clanTeretane');

INSERT INTO korisnici (korisnickoime, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, Uloga) 
VALUES ('zika@gmail.com', 'zika123', 'zika', 'Zika', 'Zikic', '2000-06-11', 'Ulica 4', '0637758392', '2022-12-18', 'clanTeretane');




INSERT INTO treninzi (id, naziv, trener, kratakOpis, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan)
VALUES (1, 'trening1', 'trener1', 'Opis', 500, 'pojedinacni', 'amaterski', 60, 4, false);

INSERT INTO treninzi (id, naziv, trener, kratakOpis, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan)
VALUES (2, 'trening2', 'trener1', 'Opis', 800, 'pojedinacni', 'srednji', 70, 3, false);

INSERT INTO treninzi (id, naziv, trener, kratakOpis, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan)
VALUES (3, 'trening3', 'trener1', 'Opis', 1000, 'pojedinacni', 'napredni', 90, 5, false);

INSERT INTO tipTreninga (id, naziv, opis) VALUES (1, 'Joga', 'Opis');
INSERT INTO tipTreninga (id, naziv, opis) VALUES (2, 'Fitness', 'Opis');
INSERT INTO tipTreninga (id, naziv, opis) VALUES (3, 'Cardio', 'Opis');

INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (1, 1);
INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (1, 2);
INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (1, 3);

INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (2, 1);
INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (2, 2);

INSERT INTO treningTipTreninga (treningId, tipTreningaId) VALUES (3, 1);
