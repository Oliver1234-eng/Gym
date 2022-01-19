package com.ftn.TeretanaVebProjekat.dao;

import java.util.ArrayList;
import java.util.List;

import com.ftn.TeretanaVebProjekat.model.TipTreninga;

public interface TipTreningaDAO {
	
	public TipTreninga findOne(Long id);

	public List<TipTreninga> findAll();

	public List<TipTreninga> find(String naziv);
	
	public int save(TipTreninga tipTreninga);

	public int [] save(ArrayList<TipTreninga> tipoviTreninga);
	
	public int update(TipTreninga tipTreninga);

	public int delete(Long id);

}
