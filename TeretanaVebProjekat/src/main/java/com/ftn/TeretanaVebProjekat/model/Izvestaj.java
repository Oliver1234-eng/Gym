package com.ftn.TeretanaVebProjekat.model;

import java.time.LocalDateTime;

public class Izvestaj {
	
	private Long id;
	private Trening trening;
	private String trener = "trener2";
	private LocalDateTime datumIVreme;
	private int brojZakazanihTreninga;
	private int cena;
	
	public Izvestaj() {}

	public Izvestaj(Long id, Trening trening, String trener, LocalDateTime datumIVreme, int brojZakazanihTreninga,
			int cena) {
		super();
		this.id = id;
		this.trening = trening;
		this.trener = trener;
		this.datumIVreme = datumIVreme;
		this.brojZakazanihTreninga = brojZakazanihTreninga;
		this.cena = cena;
	}

	public Izvestaj(Trening trening, String trener, LocalDateTime datumIVreme, int brojZakazanihTreninga, int cena) {
		super();
		this.trening = trening;
		this.trener = trener;
		this.datumIVreme = datumIVreme;
		this.brojZakazanihTreninga = brojZakazanihTreninga;
		this.cena = cena;
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
		Izvestaj other = (Izvestaj) obj;
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

	public String getTrener() {
		return trener;
	}

	public void setTrener(String trener) {
		this.trener = trener;
	}

	public LocalDateTime getDatumIVreme() {
		return datumIVreme;
	}

	public void setDatumIVreme(LocalDateTime datumIVreme) {
		this.datumIVreme = datumIVreme;
	}

	public int getBrojZakazanihTreninga() {
		return brojZakazanihTreninga;
	}

	public void setBrojZakazanihTreninga(int brojZakazanihTreninga) {
		this.brojZakazanihTreninga = brojZakazanihTreninga;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	@Override
	public String toString() {
		return "Izvestaj [id=" + id + ", trening=" + trening + ", trener=" + trener + ", datumIVreme=" + datumIVreme
				+ ", brojZakazanihTreninga=" + brojZakazanihTreninga + ", cena=" + cena + "]";
	}
	

}
