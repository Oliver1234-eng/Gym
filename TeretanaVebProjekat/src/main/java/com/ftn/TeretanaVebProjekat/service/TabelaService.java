package com.ftn.TeretanaVebProjekat.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Tabela;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;

public interface TabelaService {
	
public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	Tabela findOne(Long id);
	List<Tabela> findAll();
	Tabela save(Tabela tabela);
	List<Tabela> save(List<Tabela> tabele);
	Tabela update(Tabela tabela);
	List<Tabela> update(List<Tabela> tabele);
	Tabela delete(Long id);
	List<Tabela> deleteAll(Trening trening);
	List<Tabela> deleteAll(List<Trening> treninzi);
	void delete(List<Long> ids);
	List<Tabela> find(Long treningId, String korisnik, Integer cenaOd, Integer cenaDo, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo);
	List<Tabela> findVer2(Long treningId, String korisnik, Integer cenaOd, Integer cenaDo, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo);
	List<Tabela> findByTreningId(Long treningId);

}
