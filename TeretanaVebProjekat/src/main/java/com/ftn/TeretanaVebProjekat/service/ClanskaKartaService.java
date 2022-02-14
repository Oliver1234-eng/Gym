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
	List<ClanskaKarta> save(List<ClanskaKarta> clanskaKarta);
	ClanskaKarta update(ClanskaKarta clanskaKarta);
	List<ClanskaKarta> update(List<ClanskaKarta> clanskeKarte);
	ClanskaKarta delete(Long id);
	List<ClanskaKarta> deleteAll(Korisnik korisnik);
	List<ClanskaKarta> deleteAll(List<Korisnik> korisnici);
	void delete(List<Long> ids);
	List<ClanskaKarta> find(Integer popust, Integer brojPoenaOd, Integer brojPoenaDo, String registarskiBroj, Long korisnikId);
	List<ClanskaKarta> findVer2(Integer popust, Integer brojPoenaOd, Integer brojPoenaDo, String registarskiBroj, Long korisnikId);
	List<ClanskaKarta> findByKorisnikId(Long korisnikId);

}
