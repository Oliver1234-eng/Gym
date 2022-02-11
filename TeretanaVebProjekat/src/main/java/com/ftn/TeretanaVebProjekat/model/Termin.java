package com.ftn.TeretanaVebProjekat.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Termin {

	private Long id;
	private LocalDateTime datumIVreme;
	private Trening trening;
	private int sala;
	
	public Termin() {}

	public Termin(Long id, LocalDateTime datumIVreme, Trening trening, int sala) {
		super();
		this.id = id;
		this.datumIVreme = datumIVreme;
		this.trening = trening;
		this.sala = sala;
	}

	public Termin(LocalDateTime datumIVreme, Trening trening, int sala) {
		super();
		this.datumIVreme = datumIVreme;
		this.trening = trening;
		this.sala = sala;
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

	public int getSala() {
		return sala;
	}

	public void setSala(int sala) {
		this.sala = sala;
	}

	@Override
	public String toString() {
		return "Termin [id=" + id + ", datumIVreme=" + datumIVreme + ", trening=" + trening + ", sala=" + sala + "]";
	}

	
	
}
