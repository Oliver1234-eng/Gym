package com.ftn.TeretanaVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.TreningKorpaDAO;
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

}
