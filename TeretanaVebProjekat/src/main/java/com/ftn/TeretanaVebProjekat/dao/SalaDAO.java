package com.ftn.TeretanaVebProjekat.dao;

import java.util.ArrayList;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Sala;

public interface SalaDAO {
	
	public Sala findOne(Long id);

	public List<Sala> findAll();

	public List<Sala> find(Integer oznaka, Integer kapacitetOd, Integer kapacitetDo);
	
	public int save(Sala sala);

	public int [] save(ArrayList<Sala> sale);
	
	public int update(Sala sala);

	public int delete(Long id);

}
