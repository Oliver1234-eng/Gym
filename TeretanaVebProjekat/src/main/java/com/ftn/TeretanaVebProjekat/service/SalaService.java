package com.ftn.TeretanaVebProjekat.service;

import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Sala;

public interface SalaService {
	
	Sala findOne(Long id);
	List<Sala> findAll();
	List<Sala> find(Long[] ids);
	Sala save(Sala sala);
	List<Sala> save(List<Sala> sale);
	Sala update(Sala sala);
	List<Sala> update(List<Sala> sale);
	Sala delete(Long id);
	void delete(List<Long> ids);
	List<Sala> find(Integer kapacitetOd, Integer kapacitetDo);

}
