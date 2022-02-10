package com.ftn.TeretanaVebProjekat.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Korisnik {

	private Long id;
	private String korisnickoIme;
	private String lozinka;
	private String email;
	private String ime;
	private String prezime;
	private LocalDateTime datumRodjenja;
	private String adresa;
	private String brojTelefona;
	private LocalDateTime datumRegistracije;
	private boolean administrator = false;
	
	public Korisnik() {}

	public Korisnik(Long id, String korisnickoIme, String lozinka, String email, String ime, String prezime,
			LocalDateTime datumRodjenja, String adresa, String brojTelefona, LocalDateTime datumRegistracije,
			boolean administrator) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumRegistracije = datumRegistracije;
		this.administrator = administrator;
	}

	public Korisnik(Long id, String korisnickoIme, String lozinka, String email, String ime, String prezime,
			LocalDateTime datumRodjenja, String adresa, String brojTelefona, LocalDateTime datumRegistracije) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumRegistracije = datumRegistracije;
	}

	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime,
			LocalDateTime datumRodjenja, String adresa, String brojTelefona, LocalDateTime datumRegistracije,
			boolean administrator) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumRegistracije = datumRegistracije;
		this.administrator = administrator;
	}

	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime,
			LocalDateTime datumRodjenja, String adresa, String brojTelefona, LocalDateTime datumRegistracije) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumRegistracije = datumRegistracije;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result + ((korisnickoIme == null) ? 0 : korisnickoIme.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Korisnik other = (Korisnik) obj;
		if (korisnickoIme == null) {
			if (other.korisnickoIme != null)
				return false;
		} else if (!korisnickoIme.equals(other.korisnickoIme))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public LocalDateTime getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(LocalDateTime datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public LocalDateTime getDatumRegistracije() {
		return datumRegistracije;
	}

	public void setDatumRegistracije(LocalDateTime datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	@Override
	public String toString() {
		return "Korisnik [id=" + id + ", korisnickoIme=" + korisnickoIme + ", administrator=" + administrator + "]";
	}

	
}
