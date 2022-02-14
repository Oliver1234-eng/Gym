package com.ftn.TeretanaVebProjekat.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.KorisnikDAO;
import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.service.KorisnikService;

@Service
public class DatabaseKorisnikServiceImpl implements KorisnikService{

	@Autowired
	private KorisnikDAO korisnikDAO;
	
	@Override
	public Korisnik findOne(Long id) {
		return korisnikDAO.findOne(id);
	}
	
	@Override
	public Korisnik findOne(String korisnickoIme) {
		return korisnikDAO.findOne(korisnickoIme);
	}

	@Override
	public Korisnik findOne(String korisnickoIme, String lozinka) {
		return korisnikDAO.findOne(korisnickoIme, lozinka);
	}

	@Override
	public List<Korisnik> findAll() {
		return korisnikDAO.findAll();
	}

	@Override
	public Korisnik save(Korisnik korisnik) {
		korisnikDAO.save(korisnik);
		return korisnik;
	}

	@Override
	public List<Korisnik> save(List<Korisnik> korisnici) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Korisnik update(Korisnik korisnik) {
		korisnikDAO.update(korisnik);
		return korisnik;
	}

	@Override
	public List<Korisnik> update(List<Korisnik> korisnici) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Korisnik delete(String korisnickoIme) {
		Korisnik korisnik = findOne(korisnickoIme);
		if (korisnik != null) {
			korisnikDAO.delete(korisnickoIme);
		}
		return korisnik;
	}

	@Override
	public void delete(List<String> korisnickaImena) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Korisnik> find(String korisnickoIme, String email, String ime, String prezime, LocalDateTime datumRodjenjaOd, LocalDateTime datumRodjenjaDo, String adresa, String brojTelefona, LocalDateTime datumRegistracijeOd, LocalDateTime datumRegistracijeDo, Boolean administrator) {
		// minimalne inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 2.
		return korisnikDAO.find(korisnickoIme, email, ime, prezime, datumRodjenjaOd, datumRodjenjaDo, adresa, brojTelefona, datumRegistracijeOd, datumRegistracijeDo, administrator);
	}

	@Override
	public List<Korisnik> findByKorisnickoIme(String korisnickoIme) {
		// TODO Auto-generated method stub
		return null;
	}
}
