package com.ftn.TeretanaVebProjekat.model;

import java.util.ArrayList;
import java.util.List;

public class ClanskaKarta {

	private Long id;
	private int popust;
	private int brojPoena;
	private String registarskiBroj;
	private String korisnik = "jova";
	private String status = "prihvacen";
	private List<TreningKorpa> zakazaniTreninzi;
	
	public ClanskaKarta() {
		this.zakazaniTreninzi = new ArrayList<TreningKorpa>();
	}

	public ClanskaKarta(Long id, int popust, int brojPoena, String registarskiBroj, String korisnik, String status,
			List<TreningKorpa> zakazaniTreninzi) {
		super();
		this.id = id;
		this.popust = popust;
		this.brojPoena = brojPoena;
		this.registarskiBroj = registarskiBroj;
		this.korisnik = korisnik;
		this.status = status;
		this.zakazaniTreninzi = zakazaniTreninzi;
	}

	public ClanskaKarta(int popust, int brojPoena, String registarskiBroj, String korisnik, String status,
			List<TreningKorpa> zakazaniTreninzi) {
		super();
		this.popust = popust;
		this.brojPoena = brojPoena;
		this.registarskiBroj = registarskiBroj;
		this.korisnik = korisnik;
		this.status = status;
		this.zakazaniTreninzi = zakazaniTreninzi;
	}

	public ClanskaKarta(Long id, int popust, int brojPoena, String registarskiBroj, String status,
			List<TreningKorpa> zakazaniTreninzi) {
		super();
		this.id = id;
		this.popust = popust;
		this.brojPoena = brojPoena;
		this.registarskiBroj = registarskiBroj;
		this.status = status;
		this.zakazaniTreninzi = zakazaniTreninzi;
	}

	public ClanskaKarta(Long id, int popust, int brojPoena, String registarskiBroj, String korisnik, String status) {
		super();
		this.id = id;
		this.popust = popust;
		this.brojPoena = brojPoena;
		this.registarskiBroj = registarskiBroj;
		this.korisnik = korisnik;
		this.status = status;
		this.zakazaniTreninzi = new ArrayList<TreningKorpa>();
	}

	public ClanskaKarta(int popust, int brojPoena, String registarskiBroj, String korisnik, String status) {
		super();
		this.popust = popust;
		this.brojPoena = brojPoena;
		this.registarskiBroj = registarskiBroj;
		this.korisnik = korisnik;
		this.status = status;
		this.zakazaniTreninzi = new ArrayList<TreningKorpa>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result + ((id == null) ? 0 : id.hashCode());
		return 31 + id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClanskaKarta other = (ClanskaKarta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}

	public int getBrojPoena() {
		return brojPoena;
	}

	public void setBrojPoena(int brojPoena) {
		this.brojPoena = brojPoena;
	}

	public String getRegistarskiBroj() {
		return registarskiBroj;
	}

	public void setRegistarskiBroj(String registarskiBroj) {
		this.registarskiBroj = registarskiBroj;
	}

	public String getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(String korisnik) {
		this.korisnik = korisnik;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<TreningKorpa> getZakazaniTreninzi() {
		return zakazaniTreninzi;
	}

	public void setZakazaniTreninzi(List<TreningKorpa> zakazaniTreninzi) {
		this.zakazaniTreninzi = zakazaniTreninzi;
	}

	@Override
	public String toString() {
		return "ClanskaKarta [id=" + id + ", registarskiBroj=" + registarskiBroj + ", korisnik=" + korisnik + "]";
	}
	
}
