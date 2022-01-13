package com.ftn.TeretanaVebProjekat.service;

import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Trening;

public interface TreningService {

	Trening findOne(Long id); 
	List<Trening> findAll(); 
	Trening save(Trening trening); 
	Trening update(Trening trening); 
	Trening delete(Long id); 
}
