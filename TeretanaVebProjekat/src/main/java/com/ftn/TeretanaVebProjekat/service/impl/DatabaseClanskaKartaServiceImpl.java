package com.ftn.TeretanaVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.TeretanaVebProjekat.dao.ClanskaKartaDAO;
import com.ftn.TeretanaVebProjekat.model.ClanskaKarta;
import com.ftn.TeretanaVebProjekat.service.ClanskaKartaService;

@Service
public class DatabaseClanskaKartaServiceImpl implements ClanskaKartaService{

	@Autowired
	private ClanskaKartaDAO clanskaKartaDAO;
	
	@Override
	public ClanskaKarta findOne(Long id) {
		return clanskaKartaDAO.findOne(id);
	}

	@Override
	public List<ClanskaKarta> findAll() {
		return clanskaKartaDAO.findAll();
	}

	@Override
	public ClanskaKarta save(ClanskaKarta clanskaKarta) {
		clanskaKartaDAO.save(clanskaKarta);
		return clanskaKarta;
	}

	@Override
	public ClanskaKarta update(ClanskaKarta clanskaKarta) {
		clanskaKartaDAO.update(clanskaKarta);
		return clanskaKarta;
	}

	@Override
	public ClanskaKarta delete(Long id) {
		ClanskaKarta clanskaKarta = clanskaKartaDAO.findOne(id);
		clanskaKartaDAO.delete(id);
		return clanskaKarta;
	}

	@Override
	public ClanskaKarta findOneByRegistarskiBroj(String registarskiBroj) {
		return clanskaKartaDAO.findOneByRegistarskiBroj(registarskiBroj);
	}

}
