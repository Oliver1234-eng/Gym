package com.ftn.TeretanaVebProjekat.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.ClanskaKarta;
import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;

public interface ClanskaKartaService {
	
	ClanskaKarta findOne(Long id); 
	
	List<ClanskaKarta> findAll(); 
	
	ClanskaKarta save(ClanskaKarta clanskaKarta); 
	
	ClanskaKarta update(ClanskaKarta clanskaKarta); 
	
	ClanskaKarta delete(Long id); 
	
	ClanskaKarta findOneByRegistarskiBroj(String registarskiBroj);
	
	List<ClanskaKarta> find(Integer popust, Integer brojPoenaOd, Integer brojPoenaDo, String registarskiBroj, String korisnik, String status);

}
