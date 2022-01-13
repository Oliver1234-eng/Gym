package com.ftn.TeretanaVebProjekat.model;

import java.time.LocalDate;

public class Termin {

	private Long id;
	private Sala sala;
	private Trening trening;
	private LocalDate datum;
	
	public Termin() {}

	public Termin(Long id, Sala sala, Trening trening, LocalDate datum) {
		super();
		this.id = id;
		this.sala = sala;
		this.trening = trening;
		this.datum = datum;
	}

	public Termin(Sala sala, Trening trening, LocalDate datum) {
		super();
		this.sala = sala;
		this.trening = trening;
		this.datum = datum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Trening getTrening() {
		return trening;
	}

	public void setTrening(Trening trening) {
		this.trening = trening;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	
	@Override
	public String toString() {
		return this.getId() + ";" + this.getSala() + ";" + this.getTrening()
		 + ";" + this.getDatum();	
		
	}
	
}
