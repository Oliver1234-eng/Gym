package com.ftn.TeretanaVebProjekat.dao;

import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Trening;

public interface TreningDAO {

		public Trening findOne(Long id);

		public List<Trening> findAll();

		public int save(Trening trening);

		public int update(Trening trening);

		public int delete(Long id);
		
		List<Trening> find(String naziv, Long tipTreningaId, String trener, String kratakOpis, 
				Integer cenaOd, Integer cenaDo, String vrstaTreninga, 
				String nivoTreninga, String trajanjeUMinutima, 
				Integer prosecnaOcenaOd, Integer prosecnaOcenaDo);
}
