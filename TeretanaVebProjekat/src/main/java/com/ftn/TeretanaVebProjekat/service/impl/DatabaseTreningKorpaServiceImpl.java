package com.ftn.TeretanaVebProjekat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.TreningKorpaDAO;
import com.ftn.TeretanaVebProjekat.model.TipTreninga;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.model.TreningKorpa;
import com.ftn.TeretanaVebProjekat.service.TreningKorpaService;

@Service
public class DatabaseTreningKorpaServiceImpl implements TreningKorpaService {
	
	@Autowired
	private TreningKorpaDAO treningKorpaDAO;

	@Override
	public TreningKorpa findOne(Long id) {
		return treningKorpaDAO.findOne(id);
	}

	@Override
	public List<TreningKorpa> findAll() {
		return treningKorpaDAO.findAll();
	}

	@Override
	public TreningKorpa save(TreningKorpa treningKorpa) {
		treningKorpaDAO.save(treningKorpa);
		return treningKorpa;
	}

	@Override
	public TreningKorpa update(TreningKorpa treningKorpa) {
		treningKorpaDAO.update(treningKorpa);
		return treningKorpa;
	}

	@Override
	public TreningKorpa delete(Long id) {
		TreningKorpa treningKorpa = treningKorpaDAO.findOne(id);
		if(treningKorpa != null) {
			treningKorpaDAO.delete(id);
		}
		return treningKorpa;
	}
	
	@Override
	public List<TreningKorpa> find(String naziv, String trener, String kratakOpis, String tipTreninga,
			Integer cenaOd, Integer cenaDo, String vrstaTreninga, 
			String nivoTreninga, Integer trajanjeUMinutimaOd, Integer trajanjeUMinutimaDo, 
			Integer prosecnaOcenaOd, Integer prosecnaOcenaDo) {
			
		List<TreningKorpa> treninziKorpa = treningKorpaDAO.findAll();

		// maksimalno inkluzivne vrednosti parametara ako su izostavljeni
		// filtiranje radi u Servisnom sloju - izbegavati
		if (naziv == null) {
			naziv = "";
		}
		
		if (trener == null) {
			trener = "";
		}
		
		if (kratakOpis == null) {
			kratakOpis = "";
		}
		
		if (tipTreninga == null) {
			tipTreninga = "";
		}
		
		if (cenaOd == null) {
			cenaOd = 0;
		}
		
		if (cenaDo == null) {
			cenaDo = Integer.MAX_VALUE;
		}
		
		if (vrstaTreninga == null) {
			vrstaTreninga = "";
		}
		
		if (nivoTreninga == null) {
			nivoTreninga = "";
		}
		
		if (trajanjeUMinutimaOd == null) {
			trajanjeUMinutimaOd = 0;
		}
		
		if (trajanjeUMinutimaDo == null) {
			trajanjeUMinutimaDo = Integer.MAX_VALUE;
		}
		
		if (prosecnaOcenaOd == null) {
			prosecnaOcenaOd = 0;
		}
		
		if (prosecnaOcenaDo == null) {
			prosecnaOcenaDo = Integer.MAX_VALUE;
		}
		
		List<TreningKorpa> rezultat = new ArrayList<>();
		for (TreningKorpa itTreningKorpa: treninziKorpa) {
			// kriterijum pretrage
			if (!itTreningKorpa.getNaziv().toLowerCase().contains(naziv.toLowerCase())) {
				continue;
			}
			
			if (!itTreningKorpa.getTrener().toLowerCase().contains(trener.toLowerCase())) {
				continue;
			}
			
			if (!itTreningKorpa.getKratakOpis().toLowerCase().contains(kratakOpis.toLowerCase())) {
				continue;
			}
			
			if (!itTreningKorpa.getTipTreninga().toLowerCase().contains(tipTreninga.toLowerCase())) {
				continue;
			}
			
			if (!(itTreningKorpa.getCena() >= cenaOd && itTreningKorpa.getCena() <= cenaDo)) {
				continue;
			}
			
			if (!itTreningKorpa.getVrstaTreninga().toLowerCase().contains(vrstaTreninga.toLowerCase())) {
				continue;
			}
			
			if (!itTreningKorpa.getNivoTreninga().toLowerCase().contains(nivoTreninga.toLowerCase())) {
				continue;
			}
			
			if (!(itTreningKorpa.getTrajanjeUMinutima() >= trajanjeUMinutimaOd && itTreningKorpa.getTrajanjeUMinutima() <= trajanjeUMinutimaDo)) {
				continue;
			}
			
			if (!(itTreningKorpa.getProsecnaOcena() >= prosecnaOcenaOd && itTreningKorpa.getProsecnaOcena() <= prosecnaOcenaDo)) {
				continue;
			}

			rezultat.add(itTreningKorpa);
		}

		return rezultat;
		
	}

}
