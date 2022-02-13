package com.ftn.TeretanaVebProjekat.dao;

import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.model.TreningKorpa;

public interface TreningKorpaDAO {
	
	public TreningKorpa findOne(Long id);

	public List<TreningKorpa> findAll();

	public int save(TreningKorpa treningKorpa);

	public int update(TreningKorpa treningKorpa);

	public int delete(Long id);
	
	List<TreningKorpa> find(String naziv, String trener, String kratakOpis, String tipTreninga,
			Integer cenaOd, Integer cenaDo, String vrstaTreninga, 
			String nivoTreninga, Integer trajanjeUMinutimaOd, Integer trajanjeUMinutimaDo, 
			Integer prosecnaOcenaOd, Integer prosecnaOcenaDo);

}
