package com.ftn.TeretanaVebProjekat.service;

import java.util.List;

import com.ftn.TeretanaVebProjekat.model.ClanskaKarta;

public interface ClanskaKartaService {
	
	ClanskaKarta findOne(Long id); 
	
	List<ClanskaKarta> findAll(); 
	
	ClanskaKarta save(ClanskaKarta clanskaKarta); 
	
	ClanskaKarta update(ClanskaKarta clanskaKarta); 
	
	ClanskaKarta delete(Long id); 
	
	ClanskaKarta findOneByRegistarskiBroj(String registarskiBroj);

}
