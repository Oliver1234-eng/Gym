package com.ftn.TeretanaVebProjekat.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.ClanskaKartaDAO;
import com.ftn.TeretanaVebProjekat.dao.TerminDAO;
import com.ftn.TeretanaVebProjekat.model.ClanskaKarta;
import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.service.ClanskaKartaService;
import com.ftn.TeretanaVebProjekat.service.TerminService;

@Service
public class DatabaseClanskaKartaServiceImpl implements ClanskaKartaService {

	@Autowired
	private ClanskaKartaDAO clanskaKartaDAO;
	
	@Override
	public ClanskaKarta findOne(Long id) {
		return clanskaKartaDAO.findOne(id);
	}

	@Override
	public List<ClanskaKarta> findAll() {
		return clanskaKartaDAO.findAll();
	}

	@Override
	public ClanskaKarta save(ClanskaKarta clanskaKarta) {
		clanskaKartaDAO.save(clanskaKarta);
		return clanskaKarta;
	}

	@Override
	public ClanskaKarta update(ClanskaKarta clanskaKarta) {
		clanskaKartaDAO.update(clanskaKarta);
		return clanskaKarta;
	}

	@Override
	public ClanskaKarta delete(Long id) {
		ClanskaKarta clanskaKarta = clanskaKartaDAO.findOne(id);
		clanskaKartaDAO.delete(id);
		return clanskaKarta;
	}

	@Override
	public ClanskaKarta findOneByRegistarskiBroj(String registarskiBroj) {
		return clanskaKartaDAO.findOneByRegistarskiBroj(registarskiBroj);
	}

	@Override
	public List<ClanskaKarta> find(Integer popust, Integer brojPoenaOd, Integer brojPoenaDo, String registarskiBroj, String korisnik, String status) {
		// minimalne inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 2.
		
		return clanskaKartaDAO.find(popust, brojPoenaOd, brojPoenaDo, registarskiBroj, korisnik, status);

	}


}
