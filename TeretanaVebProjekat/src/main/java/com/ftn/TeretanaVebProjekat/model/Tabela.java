package com.ftn.TeretanaVebProjekat.model;

import java.time.LocalDateTime;

public class Tabela {
	
	private Long id;
	private Trening trening;
	private String korisnik = "mika";
	private int cena;
	private LocalDateTime datumIVreme;
	
	public Tabela() {}

	public Tabela(Long id, Trening trening, String korisnik, int cena, LocalDateTime datumIVreme) {
		super();
		this.id = id;
		this.trening = trening;
		this.korisnik = korisnik;
		this.cena = cena;
		this.datumIVreme = datumIVreme;
	}

	public Tabela(Trening trening, String korisnik, int cena, LocalDateTime datumIVreme) {
		super();
		this.trening = trening;
		this.korisnik = korisnik;
		this.cena = cena;
		this.datumIVreme = datumIVreme;
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
		Tabela other = (Tabela) obj;
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

	public Trening getTrening() {
		return trening;
	}

	public void setTrening(Trening trening) {
		this.trening = trening;
	}

	public String getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(String korisnik) {
		this.korisnik = korisnik;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public LocalDateTime getDatumIVreme() {
		return datumIVreme;
	}

	public void setDatumIVreme(LocalDateTime datumIVreme) {
		this.datumIVreme = datumIVreme;
	}

	@Override
	public String toString() {
		return "Tabela [id=" + id + ", trening=" + trening + ", korisnik=" + korisnik + ", cena=" + cena
				+ ", datumIVreme=" + datumIVreme + "]";
	}
	
}
