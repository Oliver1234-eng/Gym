package com.ftn.TeretanaVebProjekat.service;

import java.util.List;

import com.ftn.TeretanaVebProjekat.model.TipTreninga;

public interface TipTreningaService {
	
	TipTreninga findOne(Long id);
	List<TipTreninga> findAll();
	List<TipTreninga> find(Long[] ids);
	TipTreninga save(TipTreninga tipTreninga);
	List<TipTreninga> save(List<TipTreninga> tipoviTreninga);
	TipTreninga update(TipTreninga tipTreninga);
	List<TipTreninga> update(List<TipTreninga> tipoviTreninga);
	TipTreninga delete(Long id);
	void delete(List<Long> ids);
	List<TipTreninga> find(String naziv);

}
