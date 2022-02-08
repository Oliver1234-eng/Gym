package com.ftn.TeretanaVebProjekat.model;

public class Sala {

	private Long id;
	private String oznakaSale;
	private int kapacitet;
	
	public Sala() {}

	public Sala(Long id, String oznakaSale, int kapacitet) {
		super();
		this.id = id;
		this.oznakaSale = oznakaSale;
		this.kapacitet = kapacitet;
	}

	public Sala(String oznakaSale, int kapacitet) {
		super();
		this.oznakaSale = oznakaSale;
		this.kapacitet = kapacitet;
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
		Sala other = (Sala) obj;
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

	public String getOznakaSale() {
		return oznakaSale;
	}

	public void setOznakaSale(String oznakaSale) {
		this.oznakaSale = oznakaSale;
	}

	public int getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}

	@Override
	public String toString() {
		return "Sala [oznakaSale=" + oznakaSale + ", kapacitet=" + kapacitet + "]";
	}
}
