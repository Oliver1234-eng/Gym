package com.ftn.TeretanaVebProjekat.model;

public class Trening {
	
	private Long id;
	private String naziv;
	private String trener;
	private String kratakOpis;
	private String tipTreninga;
	private int cena;
	private String vrstaTreninga;
	private String nivoTreninga;
	private int trajanjeUMinutima;
	private int prosecnaOcena;
	private boolean zakazan;
	
	public Trening() {}

	public Trening(Long id, String naziv, String trener, String kratakOpis, String tipTreninga, int cena,
			String vrstaTreninga, String nivoTreninga, int trajanjeUMinutima, int prosecnaOcena, boolean zakazan) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.trener = trener;
		this.kratakOpis = kratakOpis;
		this.tipTreninga = tipTreninga;
		this.cena = cena;
		this.vrstaTreninga = vrstaTreninga;
		this.nivoTreninga = nivoTreninga;
		this.trajanjeUMinutima = trajanjeUMinutima;
		this.prosecnaOcena = prosecnaOcena;
		this.zakazan = zakazan;
	}

	public Trening(String naziv, String trener, String kratakOpis, String tipTreninga, int cena, String vrstaTreninga,
			String nivoTreninga, int trajanjeUMinutima, int prosecnaOcena) {
		super();
		this.naziv = naziv;
		this.trener = trener;
		this.kratakOpis = kratakOpis;
		this.tipTreninga = tipTreninga;
		this.cena = cena;
		this.vrstaTreninga = vrstaTreninga;
		this.nivoTreninga = nivoTreninga;
		this.trajanjeUMinutima = trajanjeUMinutima;
		this.prosecnaOcena = prosecnaOcena;
		this.zakazan = false;
	}

	public Trening(Long id, String naziv, String trener, String kratakOpis, String tipTreninga, int cena,
			String vrstaTreninga, String nivoTreninga, int trajanjeUMinutima, int prosecnaOcena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.trener = trener;
		this.kratakOpis = kratakOpis;
		this.tipTreninga = tipTreninga;
		this.cena = cena;
		this.vrstaTreninga = vrstaTreninga;
		this.nivoTreninga = nivoTreninga;
		this.trajanjeUMinutima = trajanjeUMinutima;
		this.prosecnaOcena = prosecnaOcena;
		this.zakazan = false;
	}

	/*
	 * public Trening(String naziv, String trener, String kratakOpis, String
	 * tipTreninga, int cena, String vrstaTreninga, String nivoTreninga, int
	 * trajanjeUMinutima, int prosecnaOcena) { super(); this.naziv = naziv;
	 * this.trener = trener; this.kratakOpis = kratakOpis; this.tipTreninga =
	 * tipTreninga; this.cena = cena; this.vrstaTreninga = vrstaTreninga;
	 * this.nivoTreninga = nivoTreninga; this.trajanjeUMinutima = trajanjeUMinutima;
	 * this.prosecnaOcena = prosecnaOcena; }
	 */
	
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
		Trening other = (Trening) obj;
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

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getTrener() {
		return trener;
	}

	public void setTrener(String trener) {
		this.trener = trener;
	}

	public String getKratakOpis() {
		return kratakOpis;
	}

	public void setKratakOpis(String kratakOpis) {
		this.kratakOpis = kratakOpis;
	}

	public String getTipTreninga() {
		return tipTreninga;
	}

	public void setTipTreninga(String tipTreninga) {
		this.tipTreninga = tipTreninga;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public String getVrstaTreninga() {
		return vrstaTreninga;
	}

	public void setVrstaTreninga(String vrstaTreninga) {
		this.vrstaTreninga = vrstaTreninga;
	}

	public String getNivoTreninga() {
		return nivoTreninga;
	}

	public void setNivoTreninga(String nivoTreninga) {
		this.nivoTreninga = nivoTreninga;
	}

	public int getTrajanjeUMinutima() {
		return trajanjeUMinutima;
	}

	public void setTrajanjeUMinutima(int trajanjeUMinutima) {
		this.trajanjeUMinutima = trajanjeUMinutima;
	}

	public int getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(int prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	public boolean isZakazan() {
		return zakazan;
	}

	public void setZakazan(boolean zakazan) {
		this.zakazan = zakazan;
	}
	
	@Override
	public String toString() {
		return this.getId() + ";" + this.getNaziv() + ";" + this.getTrener()
		 + ";" + this.getKratakOpis() + ";" + this.getTipTreninga() + ";" + this.getCena()
		 + ";" + this.getVrstaTreninga() + ";" + this.getNivoTreninga() + ";" + this.getTrajanjeUMinutima()
		 + ";" + this.getProsecnaOcena() + ";" + this.isZakazan();	
		
	}
	
	
	
}
