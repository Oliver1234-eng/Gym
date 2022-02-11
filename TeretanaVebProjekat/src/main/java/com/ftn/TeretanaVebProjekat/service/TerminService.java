package com.ftn.TeretanaVebProjekat.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;

public interface TerminService {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	Termin findOne(Long id);
	List<Termin> findAll();
	Termin save(Termin termin);
	List<Termin> save(List<Termin> termini);
	Termin update(Termin termin);
	List<Termin> update(List<Termin> termini);
	Termin delete(Long id);
	List<Termin> deleteAll(Trening trening);
	List<Termin> deleteAll(List<Trening> treninzi);
	void delete(List<Long> ids);
	List<Termin> find(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, Integer sala);
	List<Termin> findVer2(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, Integer sala);
	List<Termin> findByTreningId(Long treningId);


}
