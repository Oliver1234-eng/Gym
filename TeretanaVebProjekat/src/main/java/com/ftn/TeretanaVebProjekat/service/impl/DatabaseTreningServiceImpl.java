package com.ftn.TeretanaVebProjekat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.TreningDAO;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.service.TreningService;

@Service
public class DatabaseTreningServiceImpl implements TreningService {
	
	@Autowired
	private TreningDAO treningDAO;

	@Override
	public Trening findOne(Long id) {
		return treningDAO.findOne(id);
	}

	@Override
	public List<Trening> findAll() {
		return treningDAO.findAll();
	}

	@Override
	public Trening save(Trening trening) {
		treningDAO.save(trening);
		return trening;
	}

	@Override
	public Trening update(Trening trening) {
		treningDAO.update(trening);
		return trening;
	}

	@Override
	public Trening delete(Long id) {
		Trening trening = treningDAO.findOne(id);
		if(trening != null) {
			treningDAO.delete(id);
		}
		return trening;
	}
	
	@Override
	public List<Trening> find(String naziv, String trener, String kratakOpis, 
			String tipTreninga, Integer cenaOd, Integer cenaDo, String vrstaTreninga, 
			String nivoTreninga, Integer trajanjeUMinutimaOd, Integer trajanjeUMinutimaDo, 
			Integer prosecnaOcenaOd, Integer prosecnaOcenaDo) {
			
		List<Trening> treninzi = treningDAO.findAll();

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
		
		List<Trening> rezultat = new ArrayList<>();
		for (Trening itTrening: treninzi) {
			// kriterijum pretrage
			if (!itTrening.getNaziv().toLowerCase().contains(naziv.toLowerCase())) {
				continue;
			}
			
			if (!itTrening.getTrener().toLowerCase().contains(trener.toLowerCase())) {
				continue;
			}
			
			if (!itTrening.getKratakOpis().toLowerCase().contains(kratakOpis.toLowerCase())) {
				continue;
			}
			
			if (!itTrening.getTipTreninga().toLowerCase().contains(tipTreninga.toLowerCase())) {
				continue;
			}
			
			if (!(itTrening.getCena() >= cenaOd && itTrening.getCena() <= cenaDo)) {
				continue;
			}
			
			if (!itTrening.getVrstaTreninga().toLowerCase().contains(vrstaTreninga.toLowerCase())) {
				continue;
			}
			
			if (!itTrening.getNivoTreninga().toLowerCase().contains(nivoTreninga.toLowerCase())) {
				continue;
			}
			
			if (!(itTrening.getTrajanjeUMinutima() >= trajanjeUMinutimaOd && itTrening.getTrajanjeUMinutima() <= trajanjeUMinutimaDo)) {
				continue;
			}
			
			if (!(itTrening.getProsecnaOcena() >= prosecnaOcenaOd && itTrening.getProsecnaOcena() <= prosecnaOcenaDo)) {
				continue;
			}

			rezultat.add(itTrening);
		}

		return rezultat;
		
	}

}
