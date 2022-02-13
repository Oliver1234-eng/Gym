package com.ftn.TeretanaVebProjekat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.SalaDAO;
import com.ftn.TeretanaVebProjekat.model.Sala;
import com.ftn.TeretanaVebProjekat.service.SalaService;

@Service
public class DatabaseSalaServiceImpl implements SalaService {

	@Autowired
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
	public List<Sala> find(Integer oznaka, Integer kapacitetOd, Integer kapacitetDo) {
		// minimalne inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 1.
		return salaDAO.find(oznaka, kapacitetOd, kapacitetDo);
	}

}
