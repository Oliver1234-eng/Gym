package com.ftn.TeretanaVebProjekat.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.Tabela;
import com.ftn.TeretanaVebProjekat.model.Termin;

public interface TabelaDAO {
	
	public Tabela findOne(Long id);

	public List<Tabela> findAll();
	
	public List<Tabela> find(Long treningId, String korisnik, Integer cenaOd, Integer cenaDo, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo);
	
	public List<Tabela> find(HashMap<String, Object> mapaArgumenata);
	
	public int save(Tabela tabela);
	
	public int update(Tabela tabela);

	public int delete(Long id);

}
