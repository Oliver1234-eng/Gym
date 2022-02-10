package com.ftn.TeretanaVebProjekat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.SalaDAO;
import com.ftn.TeretanaVebProjekat.model.Sala;
import com.ftn.TeretanaVebProjekat.service.SalaService;

@Service
public class DatabaseSalaServiceImpl {}//implements SalaService {
	
	/*@Autowired
//	@Qualifier("zanrDAOOldCode")
	private SalaDAO salaDAO;

	@Override
	public Sala findOne(Long id) {
		return salaDAO.findOne(id);
	}

	@Override
	public List<Sala> findAll() {
		return salaDAO.findAll();
	}

	@Override
	public List<Sala> find(Long[] ids) {
		List<Sala> rezultat = new ArrayList<>();
		for (Long id: ids) {
			Sala sala = salaDAO.findOne(id);
			rezultat.add(sala);
		}

		return rezultat;
	}

	@Override
	public Sala save(Sala sala) {
		salaDAO.save(sala);
		return sala;
	}

	@Override
	public List<Sala> save(List<Sala> sale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sala update(Sala sala) {
		salaDAO.update(sala);
		return sala;
	}

	@Override
	public List<Sala> update(List<Sala> sale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sala delete(Long id) {
		Sala sala = findOne(id);
		if (sala != null) {
			salaDAO.delete(id);
		}
		return sala;
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Sala> find(String oznakaSale, Integer kapacitetOd, Integer kapacitetDo) {
		 
		List<Sala> sale = salaDAO.findAll();

		// maksimalno inkluzivne vrednosti parametara ako su izostavljeni
		// filtiranje radi u Servisnom sloju - izbegavati
		if (oznakaSale == null) {
			oznakaSale = "";
		}
		
		if (kapacitetOd == null) {
			kapacitetOd = 0;
		}
		
		if (kapacitetDo == null) {
			kapacitetDo = Integer.MAX_VALUE;
		}
		
		List<Sala> rezultat = new ArrayList<>();
		for (Sala itSala: sale) {
			// kriterijum pretrage
			if (!itSala.getOznakaSale().toLowerCase().contains(oznakaSale.toLowerCase())) {
				continue;
			}
				
			if (!(itSala.getKapacitet() >= kapacitetOd && itSala.getKapacitet() <= kapacitetDo)) {
				continue;
			}

			rezultat.add(itSala);
		}

		return rezultat;
		
	}*/
