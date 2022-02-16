package com.ftn.TeretanaVebProjekat.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Izvestaj;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;

public interface IzvestajiService {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	Izvestaj findOne(Long id);
	List<Izvestaj> findAll();
	Izvestaj save(Izvestaj izvestaj);
	List<Izvestaj> save(List<Izvestaj> izvestaji);
	Izvestaj update(Izvestaj izvestaj);
	List<Izvestaj> update(List<Izvestaj> izvestaji);
	Izvestaj delete(Long id);
	List<Izvestaj> deleteAll(Trening trening);
	List<Izvestaj> deleteAll(List<Trening> treninzi);
	void delete(List<Long> ids);
	List<Izvestaj> find(Long treningId, String trener, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Integer brojZakazanihTreninga, Integer cenaOd, Integer cenaDo);
	List<Izvestaj> findVer2(Long treningId, String trener, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Integer brojZakazanihTreninga, Integer cenaOd, Integer cenaDo);
	List<Izvestaj> findByTreningId(Long treningId);

}
