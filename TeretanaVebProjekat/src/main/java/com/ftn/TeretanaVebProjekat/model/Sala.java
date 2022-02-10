package com.ftn.TeretanaVebProjekat.model;

public class Sala {

	private Long id;
	private int kapacitet;
	
	public Sala() {}

	public Sala(Long id, int kapacitet) {
		super();
		this.id = id;
		this.kapacitet = kapacitet;
	}

	public Sala(int kapacitet) {
		super();
		this.kapacitet = kapacitet;
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

	public int getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}

	@Override
	public String toString() {
		return "Sala [id=" + id + ", kapacitet=" + kapacitet + "]";
	}

	
}
