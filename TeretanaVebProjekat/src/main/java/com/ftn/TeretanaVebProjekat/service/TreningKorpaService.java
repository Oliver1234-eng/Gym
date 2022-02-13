package com.ftn.TeretanaVebProjekat.service;

import java.util.List;

import com.ftn.TeretanaVebProjekat.model.TreningKorpa;

public interface TreningKorpaService {
	
	TreningKorpa findOne(Long id); 
	
	List<TreningKorpa> findAll(); 
	
	TreningKorpa save(TreningKorpa treningKorpa); 
	
	TreningKorpa update(TreningKorpa treningKorpa); 
	
	TreningKorpa delete(Long id); 

}
