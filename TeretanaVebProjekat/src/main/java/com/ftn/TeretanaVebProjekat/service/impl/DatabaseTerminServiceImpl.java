package com.ftn.TeretanaVebProjekat.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.TerminDAO;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.service.TerminService;

@Service
public class DatabaseTerminServiceImpl implements TerminService {

	@Autowired
	private TerminDAO terminDAO;
	
	@Override
	public Termin findOne(Long id) {
		return terminDAO.findOne(id);
	}

	@Override
	public List<Termin> findAll() {
		return terminDAO.findAll();
	}

	@Override
	public Termin save(Termin termin) {
		terminDAO.save(termin);
		return termin;
	}

	@Override
	public List<Termin> save(List<Termin> termini) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Termin update(Termin termin) {
		terminDAO.update(termin);
		return termin;
	}

	@Override
	public List<Termin> update(List<Termin> termini) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Termin delete(Long id) {
		Termin termin = findOne(id);
		if (termin != null) {
			terminDAO.delete(id);
		}
		return termin;
	}

	@Override
	public List<Termin> deleteAll(Trening trening) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Termin> deleteAll(List<Trening> treninzi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Termin> find(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, String sala) {
		// minimalne inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 2.
		
		return terminDAO.find(datumIVremeOd, datumIVremeDo, treningId, sala);

	}
	
	@Override
	public List<Termin> findVer2(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, String sala) {
		// maksimalno inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 2.
		
		HashMap<String, Object> mapaArgumenata = new HashMap<String,Object>();
		
		if(datumIVremeOd!=null) 
			mapaArgumenata.put("datumIVremeOd", datumIVremeOd);
		
		if(datumIVremeDo!=null) 
			mapaArgumenata.put("datumIVremeDo", datumIVremeDo);
		
		if(treningId!=null) 
			mapaArgumenata.put("treningId", treningId);
		
		if(sala!=null) 
			mapaArgumenata.put("sala", sala);
		
		return terminDAO.find(mapaArgumenata);
	}

	@Override
	public List<Termin> findByTreningId(Long treningId) {
		// TODO Auto-generated method stub
		return null;
	}

}
