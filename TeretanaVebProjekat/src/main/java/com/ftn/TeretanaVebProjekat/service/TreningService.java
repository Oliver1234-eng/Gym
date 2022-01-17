package com.ftn.TeretanaVebProjekat.service;

import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Trening;

public interface TreningService {

	Trening findOne(Long id); 
	List<Trening> findAll(); 
	Trening save(Trening trening); 
	Trening update(Trening trening); 
	Trening delete(Long id);
	List<Trening> find(String naziv, String trener, String kratakOpis, 
			String tipTreninga, Integer cenaOd, Integer cenaDo, String vrstaTreninga, 
			String nivoTreninga, Integer trajanjeUMinutimaOd, Integer trajanjeUMinutimaDo, 
			Integer prosecnaOcenaOd, Integer prosecnaOcenaDo);
}
