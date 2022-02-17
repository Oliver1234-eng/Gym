package com.ftn.TeretanaVebProjekat.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Termin;

public interface TerminDAO {
	
	public Termin findOne(Long id);

	public List<Termin> findAll();
	
	public List<Termin> find(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, String sala);
	
	public List<Termin> find(HashMap<String, Object> mapaArgumenata);
	
	public int save(Termin termin);
	
	public int update(Termin termin);

	public int delete(Long id);

}
