package com.ftn.TeretanaVebProjekat.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Komentar;
import com.ftn.TeretanaVebProjekat.model.Trening;

public interface KomentarService {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	Komentar findOne(Long id);
	List<Komentar> findAll();
	Komentar save(Komentar komentar);
	List<Komentar> save(List<Komentar> komentari);
	Komentar update(Komentar komentar);
	List<Komentar> update(List<Komentar> komentari);
	Komentar delete(Long id);
	List<Komentar> deleteAll(Trening trening);
	List<Komentar> deleteAll(List<Trening> treninzi);
	void delete(List<Long> ids);
	List<Komentar> find(String tekst, Integer ocenaOd, Integer ocenaDo, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, String status, String korisnik);
	List<Komentar> findVer2(String tekst, Integer ocenaOd, Integer ocenaDo, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, String status, String korisnik);
	List<Komentar> findByTreningId(Long treningId);

}
