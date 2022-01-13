package com.ftn.TeretanaVebProjekat.service.impl;

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

}
