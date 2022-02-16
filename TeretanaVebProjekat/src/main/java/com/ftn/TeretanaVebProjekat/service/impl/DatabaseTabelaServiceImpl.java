package com.ftn.TeretanaVebProjekat.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.TabelaDAO;
import com.ftn.TeretanaVebProjekat.dao.TerminDAO;
import com.ftn.TeretanaVebProjekat.model.Tabela;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.service.TabelaService;
import com.ftn.TeretanaVebProjekat.service.TerminService;

@Service
public class DatabaseTabelaServiceImpl implements TabelaService {

	@Autowired
	private TabelaDAO tabelaDAO;
	
	@Override
	public Tabela findOne(Long id) {
		return tabelaDAO.findOne(id);
	}

	@Override
	public List<Tabela> findAll() {
		return tabelaDAO.findAll();
	}

	@Override
	public Tabela save(Tabela tabela) {
		tabelaDAO.save(tabela);
		return tabela;
	}

	@Override
	public List<Tabela> save(List<Tabela>  tabele) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tabela update(Tabela tabela) {
		tabelaDAO.update(tabela);
		return tabela;
	}

	@Override
	public List<Tabela> update(List<Tabela> tabele) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tabela delete(Long id) {
		Tabela tabela = findOne(id);
		if (tabela != null) {
			tabelaDAO.delete(id);
		}
		return tabela;
	}

	@Override
	public List<Tabela> deleteAll(Trening trening) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tabela> deleteAll(List<Trening> treninzi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Tabela> find(Long treningId, String korisnik, Integer cenaOd, Integer cenaDo, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo) {
		// minimalne inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 2.
		
		return tabelaDAO.find(treningId, korisnik, cenaOd, cenaDo, datumIVremeOd, datumIVremeDo);

	}
	
	@Override
	public List<Tabela> findVer2(Long treningId, String korisnik, Integer cenaOd, Integer cenaDo, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo) {
		// maksimalno inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 2.
		
		HashMap<String, Object> mapaArgumenata = new HashMap<String,Object>();
		
		if(treningId!=null) 
			mapaArgumenata.put("treningId", treningId);
		
		if(korisnik!=null) 
			mapaArgumenata.put("korisnik", korisnik);
		
		if(cenaOd!=null) 
			mapaArgumenata.put("cenaOd", cenaOd);
		
		if(cenaDo!=null) 
			mapaArgumenata.put("cenaDo", cenaDo);
		
		if(datumIVremeOd!=null) 
			mapaArgumenata.put("datumIVremeOd", datumIVremeOd);
		
		if(datumIVremeDo!=null) 
			mapaArgumenata.put("datumIVremeDo", datumIVremeDo);
		
		return tabelaDAO.find(mapaArgumenata);
	}

	@Override
	public List<Tabela> findByTreningId(Long treningId) {
		// TODO Auto-generated method stub
		return null;
	}

}
