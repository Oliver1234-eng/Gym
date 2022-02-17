package com.ftn.TeretanaVebProjekat.service;

import java.util.List;

import com.ftn.TeretanaVebProjekat.model.TipTreninga;
import com.ftn.TeretanaVebProjekat.model.Trening;

public interface TreningService {

	Trening findOne(Long id); 
	List<Trening> findAll(); 
	Trening save(Trening trening);
	List<Trening> save(List<Trening> treninzi);
	Trening update(Trening trening);
	List<Trening> update(List<Trening> treninzi);
	Trening delete(Long id);
	List<Trening> deleteAll(TipTreninga tipTreninga);
	void delete(List<Long> ids);
	List<Trening> find(String naziv, Long tipTreningaId, String trener, String kratakOpis, 
			Integer cenaOd, Integer cenaDo, String vrstaTreninga, 
			String nivoTreninga, String trajanjeUMinutima, 
			Integer prosecnaOcenaOd, Integer prosecnaOcenaDo);
	List<Trening> findById(Long tipTreningaId);
}
