package com.ftn.TeretanaVebProjekat.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Komentar {

	private Long id;
	private String tekst;
	private int ocena;
	private LocalDateTime datumIVreme;
	private Trening trening;
	private String status = "odobren";
	private String korisnik = "mika";
	
	public Komentar() {}

	public Komentar(Long id, String tekst, int ocena, LocalDateTime datumIVreme, Trening trening,
			String status, String korisnik) {
		super();
		this.id = id;
		this.tekst = tekst;
		this.ocena = ocena;
		this.datumIVreme = datumIVreme;
		this.trening = trening;
		this.status = status;
		this.korisnik = korisnik;
	}

	public Komentar(String tekst, int ocena, LocalDateTime datumIVreme, Trening trening, String status,
			String korisnik) {
		super();
		this.tekst = tekst;
		this.ocena = ocena;
		this.datumIVreme = datumIVreme;
		this.trening = trening;
		this.status = status;
		this.korisnik = korisnik;
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
		Komentar other = (Komentar) obj;
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

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public LocalDateTime getDatumIVreme() {
		return datumIVreme;
	}

	public void setDatumIVreme(LocalDateTime datumIVreme) {
		this.datumIVreme = datumIVreme;
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

	public String getKorisnik() {
		return korisnik;
	}
	
	public void setKorisnik(String korisnik) {
		this.korisnik = korisnik;
	}
	
	@Override
	public String toString() {
		return "Komentar [id=" + id + ", trening=" + trening + "]";
	}

	
	
	
	
}
