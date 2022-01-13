package com.ftn.TeretanaVebProjekat.model;

public class ClanskaKarta {

	private Long id;
	private int popustUProcentima;
	private int brojPoena;
	
	public ClanskaKarta() {}

	public ClanskaKarta(Long id, int popustUProcentima, int brojPoena) {
		super();
		this.id = id;
		this.popustUProcentima = popustUProcentima;
		this.brojPoena = brojPoena;
	}

	public ClanskaKarta(int popustUProcentima, int brojPoena) {
		super();
		this.popustUProcentima = popustUProcentima;
		this.brojPoena = brojPoena;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPopustUProcentima() {
		return popustUProcentima;
	}

	public void setPopustUProcentima(int popustUProcentima) {
		this.popustUProcentima = popustUProcentima;
	}

	public int getBrojPoena() {
		return brojPoena;
	}

	public void setBrojPoena(int brojPoena) {
		this.brojPoena = brojPoena;
	}
	
	@Override
	public String toString() {
		return this.getId() + ";" + this.getPopustUProcentima() + ";" + this.getBrojPoena();	
		
	}
}
