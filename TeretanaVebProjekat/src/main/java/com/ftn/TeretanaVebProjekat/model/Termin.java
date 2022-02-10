package com.ftn.TeretanaVebProjekat.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Termin {

	private Long id;
	private Sala sala;
	private Trening trening;
	private LocalDateTime datum;
	
	public Termin() {}

	public Termin(Long id, Sala sala, Trening trening, LocalDateTime datum) {
		super();
		this.id = id;
		this.sala = sala;
		this.trening = trening;
		this.datum = datum;
	}

	public Termin(Sala sala, Trening trening, LocalDateTime datum) {
		super();
		this.sala = sala;
		this.trening = trening;
		this.datum = datum;
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
		Termin other = (Termin) obj;
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

	public LocalDateTime getDatum() {
		return datum;
	}

	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "Termin [id=" + id + ", sala=" + sala + ", trening=" + trening + ", datum=" + datum + "]";
	}
	
	
	
}
