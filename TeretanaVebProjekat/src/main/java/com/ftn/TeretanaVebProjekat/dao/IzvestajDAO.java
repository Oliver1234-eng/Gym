package com.ftn.TeretanaVebProjekat.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Izvestaj;
import com.ftn.TeretanaVebProjekat.model.Termin;

public interface IzvestajDAO {
	
	public Izvestaj findOne(Long id);

	public List<Izvestaj> findAll();
	
	public List<Izvestaj> find(Long treningId, String trener, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Integer brojZakazanihTreninga, Integer cenaOd, Integer cenaDo);
	
	public List<Izvestaj> find(HashMap<String, Object> mapaArgumenata);
	
	public int save(Izvestaj izvestaj);
	
	public int update(Izvestaj izvestaj);

	public int delete(Long id);

}
