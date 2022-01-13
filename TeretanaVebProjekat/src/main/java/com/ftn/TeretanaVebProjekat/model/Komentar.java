package com.ftn.TeretanaVebProjekat.model;

import java.time.LocalDate;

public class Komentar {

	private Long id;
	private String tekstKomentara;
	private int ocena;
	private LocalDate datum;
	private Korisnik korisnik;
	private Trening trening;
	private String status;
	
	public Komentar() {}

	public Komentar(Long id, String tekstKomentara, int ocena, LocalDate datum, Korisnik korisnik, Trening trening,
			String status) {
		super();
		this.id = id;
		this.tekstKomentara = tekstKomentara;
		this.ocena = ocena;
		this.datum = datum;
		this.korisnik = korisnik;
		this.trening = trening;
		this.status = status;
	}

	public Komentar(String tekstKomentara, int ocena, LocalDate datum, Korisnik korisnik, Trening trening,
			String status) {
		super();
		this.tekstKomentara = tekstKomentara;
		this.ocena = ocena;
		this.datum = datum;
		this.korisnik = korisnik;
		this.trening = trening;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTekstKomentara() {
		return tekstKomentara;
	}

	public void setTekstKomentara(String tekstKomentara) {
		this.tekstKomentara = tekstKomentara;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Trening getTrening() {
		return trening;
	}

	public void setTrening(Trening trening) {
		this.trening = trening;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return this.getId() + ";" + this.getTekstKomentara() + ";" + this.getOcena()
		 + ";" + this.getDatum() + ";" + this.getKorisnik() + ";" + this.getTrening()
		 + ";" + this.getStatus();	
		
	}
	
}
