package com.ftn.TeretanaVebProjekat.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.KomentarDAO;
import com.ftn.TeretanaVebProjekat.model.Komentar;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.service.KomentarService;

@Service
public class DatabaseKomentarServiceImpl implements KomentarService {

	@Autowired
	private KomentarDAO komentarDAO;
	
	@Override
	public Komentar findOne(Long id) {
		return komentarDAO.findOne(id);
	}

	@Override
	public List<Komentar> findAll() {
		return komentarDAO.findAll();
	}

	@Override
	public Komentar save(Komentar komentar) {
		komentarDAO.save(komentar);
		return komentar;
	}

	@Override
	public List<Komentar> save(List<Komentar> komentari) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Komentar update(Komentar komentar) {
		komentarDAO.update(komentar);
		return komentar;
	}

	@Override
	public List<Komentar> update(List<Komentar> komentari) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Komentar delete(Long id) {
		Komentar komentar = findOne(id);
		if (komentar != null) {
			komentarDAO.delete(id);
		}
		return komentar;
	}

	@Override
	public List<Komentar> deleteAll(Trening trening) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Komentar> deleteAll(List<Trening> treninzi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Komentar> find(String tekst, Integer ocenaOd, Integer ocenaDo, 
			LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, String status, String korisnik) {
		// minimalne inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 2.
		
		return komentarDAO.find(tekst, ocenaOd, ocenaDo, datumIVremeOd, datumIVremeDo, treningId, status, korisnik);

	}
	
	@Override
	public List<Komentar> findVer2(String tekst, Integer ocenaOd, Integer ocenaDo, 
			LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, String status, String korisnik) {
		// maksimalno inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 2.
		
		HashMap<String, Object> mapaArgumenata = new HashMap<String,Object>();
		
		if(tekst!=null)
			mapaArgumenata.put("tekst", tekst);
		
		if(ocenaOd!=null)
			mapaArgumenata.put("ocenaOd", ocenaOd);
		
		if(ocenaDo!=null) 
			mapaArgumenata.put("ocenaDo", ocenaDo);
		
		if(datumIVremeOd!=null) 
			mapaArgumenata.put("datumIVremeOd", datumIVremeOd);
		
		if(datumIVremeDo!=null) 
			mapaArgumenata.put("datumIVremeDo", datumIVremeDo);
		
		if(treningId!=null) 
			mapaArgumenata.put("treningId", treningId);
		
		if(status!=null)
			mapaArgumenata.put("status", status);
		
		if(korisnik!=null)
			mapaArgumenata.put("korisnik", korisnik);
		
		
		return komentarDAO.find(mapaArgumenata);
	}

	@Override
	public List<Komentar> findByTreningId(Long treningId) {
		// TODO Auto-generated method stub
		return null;
	}

}