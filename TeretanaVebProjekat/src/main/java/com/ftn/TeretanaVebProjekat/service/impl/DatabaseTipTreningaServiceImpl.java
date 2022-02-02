package com.ftn.TeretanaVebProjekat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.TipTreningaDAO;
import com.ftn.TeretanaVebProjekat.model.TipTreninga;
import com.ftn.TeretanaVebProjekat.service.TipTreningaService;

@Service
public class DatabaseTipTreningaServiceImpl implements TipTreningaService {

	@Autowired
//	@Qualifier("zanrDAOOldCode")
	private TipTreningaDAO tipTreningaDAO;

	@Override
	public TipTreninga findOne(Long id) {
		return tipTreningaDAO.findOne(id);
	}

	@Override
	public List<TipTreninga> findAll() {
		return tipTreningaDAO.findAll();
	}

	@Override
	public List<TipTreninga> find(Long[] ids) {
		List<TipTreninga> rezultat = new ArrayList<>();
		for (Long id: ids) {
			TipTreninga tipTreninga = tipTreningaDAO.findOne(id);
			rezultat.add(tipTreninga);
		}

		return rezultat;
	}

	@Override
	public TipTreninga save(TipTreninga tipTreninga) {
		tipTreningaDAO.save(tipTreninga);
		return tipTreninga;
	}

	@Override
	public List<TipTreninga> save(List<TipTreninga> tipoviTreninga) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipTreninga update(TipTreninga tipTreninga) {
		tipTreningaDAO.update(tipTreninga);
		return tipTreninga;
	}

	@Override
	public List<TipTreninga> update(List<TipTreninga> tipoviTreninga) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipTreninga delete(Long id) {
		TipTreninga tipTreninga = findOne(id);
		if (tipTreninga != null) {
			tipTreningaDAO.delete(id);
		}
		return tipTreninga;
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TipTreninga> find(String naziv) {
		// minimalne inkluzivne vrednosti parametara ako su izostavljeni
		//1. način bi bilo pozivanje ogovarajuće DAO metode u odnosu na broj parametara 
		//		gde bi trebalo implementirati više dao metoda tako da pokriju različite situacije
		//2. način reši sve u DAO sloju
		
		//odabran 1.
		if (naziv == null) {
			return tipTreningaDAO.findAll();
		}
		return tipTreningaDAO.find(naziv);
	}
	
}
