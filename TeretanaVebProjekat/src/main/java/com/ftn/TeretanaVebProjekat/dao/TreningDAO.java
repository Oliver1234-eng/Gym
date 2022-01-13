package com.ftn.TeretanaVebProjekat.dao;

import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Trening;

public interface TreningDAO {

	public Trening findOne(Long id);

	public List<Trening> findAll();
	
	//public List<Trening> findAll(LocalDate datumOd, LocalDate datumDo);
	
	//public int findCount(LocalDate datumOd, LocalDate datumDo);
	
	//public int findCount(LocalDate datumOd, LocalDate datumDo, Autor autor);

	public int save(Trening trening);

	public int update(Trening trening);

	public int delete(Long id);
}
