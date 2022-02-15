package com.ftn.TeretanaVebProjekat.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.ClanskaKarta;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;

public interface ClanskaKartaDAO {
	
	public ClanskaKarta findOne(Long id);
	
	public ClanskaKarta findOneByRegistarskiBroj(String registarskiBroj);

	public List<ClanskaKarta> findAll();

	public int save(ClanskaKarta clanskaKarta);

	public int update(ClanskaKarta clanskaKarta);

	public int delete(Long id);
	
	public List<ClanskaKarta> find(Integer popust, Integer brojPoenaOd, Integer brojPoenaDo, String registarskiBroj, String korisnik, String status);
	
	public List<ClanskaKarta> find(HashMap<String, Object> mapaArgumenata);
	

}
