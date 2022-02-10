package com.ftn.TeretanaVebProjekat.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Korisnik;

public interface KorisnikDAO {
	
	public Korisnik findOne(String korisnickoIme);

	public Korisnik findOne(String korisnickoIme, String lozinka);

	public List<Korisnik> findAll();

	public List<Korisnik> find(String korisnickoIme, String email, String ime, String prezime, LocalDateTime datumRodjenjaOd, LocalDateTime datumRodjenjaDo, String adresa, String brojTelefona, LocalDateTime datumRegistracijeOd, LocalDateTime datumRegistracijeDo, Boolean administrator);
	
	public void save(Korisnik korisnik);

	public void update(Korisnik korisnik);

	public void delete(String korisnickoIme);
}
