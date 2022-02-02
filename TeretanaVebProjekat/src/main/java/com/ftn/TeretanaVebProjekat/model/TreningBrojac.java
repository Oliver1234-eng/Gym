package com.ftn.TeretanaVebProjekat.model;

public class TreningBrojac {
	
	private Trening trening;
	private int brojac = 0;
	
	public TreningBrojac(Trening trening) {
		this.trening= trening;
	}

	public int incrementBrojac() {
		brojac++;
		return brojac;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result + ((trening == null) ? 0 : trening.hashCode());
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
		TreningBrojac other = (TreningBrojac) obj;
		if (trening == null) {
			if (other.trening != null)
				return false;
		} else if (!trening.equals(other.trening))
			return false;
		return true;
	}

	public Trening getTrening() {
		return trening;
	}

	public void setTrening(Trening trening) {
		this.trening = trening;
	}

	public int getBrojac() {
		return brojac;
	}

	public void setBrojac(int brojac) {
		this.brojac = brojac;
	}

}
