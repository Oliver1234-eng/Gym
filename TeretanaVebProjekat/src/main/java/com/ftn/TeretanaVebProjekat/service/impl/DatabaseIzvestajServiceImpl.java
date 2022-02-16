package com.ftn.TeretanaVebProjekat.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.IzvestajDAO;
import com.ftn.TeretanaVebProjekat.dao.TerminDAO;
import com.ftn.TeretanaVebProjekat.model.Izvestaj;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.service.IzvestajiService;
import com.ftn.TeretanaVebProjekat.service.TerminService;

@Service
public class DatabaseIzvestajServiceImpl implements IzvestajiService {

	@Autowired
	private IzvestajDAO izvestajDAO;
	
	@Override
	public Izvestaj findOne(Long id) {
		return izvestajDAO.findOne(id);
	}

	@Override
	public List<Izvestaj> findAll() {
		return izvestajDAO.findAll();
	}

	@Override
	public Izvestaj save(Izvestaj izvestaj) {
		izvestajDAO.save(izvestaj);
		return izvestaj;
	}

	@Override
	public List<Izvestaj> save(List<Izvestaj> izvestaji) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Izvestaj update(Izvestaj izvestaj) {
		izvestajDAO.update(izvestaj);
		return izvestaj;
	}

	@Override
	public List<Izvestaj> update(List<Izvestaj> izvestaji) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Izvestaj delete(Long id) {
		Izvestaj izvestaj = findOne(id);
		if (izvestaj != null) {
			izvestajDAO.delete(id);
		}
		return izvestaj;
	}

	@Override
	public List<Izvestaj> deleteAll(Trening trening) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Izvestaj> deleteAll(List<Trening> treninzi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Izvestaj> find(Long treningId, String trener, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Integer brojZakazanihTreninga, Integer cenaOd, Integer cenaDo) {
		// minimalne inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 2.
		
		return izvestajDAO.find(treningId, trener, datumIVremeOd, datumIVremeDo, brojZakazanihTreninga, cenaOd, cenaDo);

	}
	
	@Override
	public List<Izvestaj> findVer2(Long treningId, String trener, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Integer brojZakazanihTreninga, Integer cenaOd, Integer cenaDo) {
		// maksimalno inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 2.
		
		HashMap<String, Object> mapaArgumenata = new HashMap<String,Object>();
		
		if(treningId!=null) 
			mapaArgumenata.put("treningId", treningId);
		
		if(trener!=null) 
			mapaArgumenata.put("trener", trener);
		
		if(datumIVremeOd!=null) 
			mapaArgumenata.put("datumIVremeOd", datumIVremeOd);
		
		if(datumIVremeDo!=null) 
			mapaArgumenata.put("datumIVremeDo", datumIVremeDo);
		
		if(brojZakazanihTreninga!=null) 
			mapaArgumenata.put("brojZakazanihTreninga", brojZakazanihTreninga);
		
		if(cenaOd!=null) 
			mapaArgumenata.put("cenaOd", cenaOd);
		
		if(cenaDo!=null) 
			mapaArgumenata.put("cenaDo", cenaDo);
		
		return izvestajDAO.find(mapaArgumenata);
	}

	@Override
	public List<Izvestaj> findByTreningId(Long treningId) {
		// TODO Auto-generated method stub
		return null;
	}

}
