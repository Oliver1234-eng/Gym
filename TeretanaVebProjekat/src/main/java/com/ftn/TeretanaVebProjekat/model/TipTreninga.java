package com.ftn.TeretanaVebProjekat.model;

public class TipTreninga {
	
	private Long id;
	private String naziv;
	
	public TipTreninga() {}

	public TipTreninga(Long id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}

	public TipTreninga(String naziv) {
		super();
		this.naziv = naziv;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result + ((id == null) ? 0 : id.hashCode());
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
		TipTreninga other = (TipTreninga) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TipTreninga [id=" + id + ", naziv=" + naziv + "]";
	}

}
