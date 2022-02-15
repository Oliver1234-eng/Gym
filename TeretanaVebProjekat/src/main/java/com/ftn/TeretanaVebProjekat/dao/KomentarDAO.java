package com.ftn.TeretanaVebProjekat.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Komentar;

public interface KomentarDAO {
	
	public Komentar findOne(Long id);

	public List<Komentar> findAll();
	
	public List<Komentar> find(String tekst, Integer ocenaOd, Integer ocenaDo, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, String status, String korisnik);
	
	public List<Komentar> find(HashMap<String, Object> mapaArgumenata);
	
	public int save(Komentar komentar);
	
	public int update(Komentar komentar);

	public int delete(Long id);

}
