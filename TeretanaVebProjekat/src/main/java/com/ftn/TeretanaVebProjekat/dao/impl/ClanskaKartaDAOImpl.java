package com.ftn.TeretanaVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.TeretanaVebProjekat.dao.ClanskaKartaDAO;
import com.ftn.TeretanaVebProjekat.dao.KorisnikDAO;
import com.ftn.TeretanaVebProjekat.dao.TerminDAO;
import com.ftn.TeretanaVebProjekat.dao.TreningKorpaDAO;
import com.ftn.TeretanaVebProjekat.model.ClanskaKarta;
import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.model.TreningKorpa;

@Repository
public class ClanskaKartaDAOImpl implements ClanskaKartaDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class ClanskaKartaRowMapper implements RowMapper<ClanskaKarta> {

		@Override
		public ClanskaKarta mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long clanskaKartaId = rs.getLong(index++);
			Integer clanskaKartaPopust = rs.getInt(index++);
			Integer clanskaKartaBrojPoena = rs.getInt(index++);
			String clanskaKartaRegistarskiBroj = rs.getString(index++);

			Long korisnikId = rs.getLong(index++);
			String korisnikKorisnickoIme = rs.getString(index++);
			String korisnikIme = rs.getString(index++);
			Korisnik korisnik = new Korisnik(korisnikId, korisnikKorisnickoIme, korisnikIme);

			ClanskaKarta clanskaKarta = new ClanskaKarta(clanskaKartaId, clanskaKartaPopust, clanskaKartaBrojPoena, clanskaKartaRegistarskiBroj, korisnik);
			return clanskaKarta;
		}

	}
	@Override
	public ClanskaKarta findOne(Long id) {
		String sql = 
				"SELECT p.id, p.popust, p.brojPoena, p.registarskiBroj, f.id, f.korisnickoIme, f.ime FROM clanskeKarte p " + 
				"LEFT JOIN korisnici f ON p.korisnikId = f.id " + 
				"WHERE p.id = ? " + 
				"ORDER BY p.id";
		return jdbcTemplate.queryForObject(sql, new ClanskaKartaRowMapper(), id);
	}
	@Override
	public List<ClanskaKarta> findAll() {
		String sql = 
				"SELECT p.id, p.popust, p.brojPoena, p.registarskiBroj, f.id, f.korisnickoIme, f.ime FROM clanskeKarte p " + 
				"LEFT JOIN korisnici f ON p.korisnikId = f.id " + 
				"ORDER BY p.id";
		return jdbcTemplate.query(sql, new ClanskaKartaRowMapper());
	}
	@Override
	public List<ClanskaKarta> find(Integer popust, Integer brojPoenaOd, Integer brojPoenaDo, String registarskiBroj, Long korisnikId) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT p.id, p.popust, p.brojPoena, p.registarskiBroj, f.id, f.korisnickoIme, f.ime FROM clanskeKarte p " + 
				"LEFT JOIN korisnici f ON p.korisnikId = f.id ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(popust!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.popust = ?");
			imaArgumenata = true;
			listaArgumenata.add(popust);
		}
		
		if(brojPoenaOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.brojPoena >= ?");
			imaArgumenata = true;
			listaArgumenata.add(brojPoenaOd);
		}
		
		if(brojPoenaDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.brojPoena <= ?");
			imaArgumenata = true;
			listaArgumenata.add(brojPoenaDo);
		}
		
		if(registarskiBroj!=null) {
			registarskiBroj = "%" + registarskiBroj + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.registarskiBroj LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(registarskiBroj);
		}
		
		if(korisnikId!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.korisnikId = ?");
			imaArgumenata = true;
			listaArgumenata.add(korisnikId);
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY p.id";
		else
			sql=sql + " ORDER BY p.id";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new ClanskaKartaRowMapper());
	}
	
	@Override
	public List<ClanskaKarta> find(HashMap<String, Object> mapaArgumenata) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT p.id, p.popust, p.brojPoena, p.registarskiBroj, f.id, f.korisnickoIme, f.ime FROM clanskeKarte p " + 
				"LEFT JOIN korisnici f ON p.korisnikId = f.id ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(mapaArgumenata.containsKey("popust")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.popust = ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("popust"));
		}
		
		if(mapaArgumenata.containsKey("brojPoenaOd")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.brojPoena >= ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("brojPoenaOd"));
		}
		
		if(mapaArgumenata.containsKey("brojPoenaDo")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.brojPoenaDo <= ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("brojPoenaDo"));
		}
		
		if(mapaArgumenata.containsKey("registarskiBroj")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.registarskiBroj LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("popust"));
		}
		
		if(mapaArgumenata.containsKey("korisnikId")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.korisnikId = ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("korisnikId"));
		}
		
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY p.id";
		else
			sql=sql + " ORDER BY p.id";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new ClanskaKartaRowMapper());
	}
	
	
	@Override
	public int save(ClanskaKarta clanskaKarta) {
		String sql = "INSERT INTO clanskeKarte (popust, brojPoena, registarskiBroj, korisnikId) VALUES (?, ?, ?, ?)";
		return jdbcTemplate.update(sql, clanskaKarta.getPopust(), clanskaKarta.getBrojPoena(), clanskaKarta.getRegistarskiBroj(), clanskaKarta.getKorisnik().getId());
	}
	@Override
	public int update(ClanskaKarta clanskaKarta) {
		String sql = "UPDATE clanskeKarte SET popust = ?, brojPoena = ?, registarskiBroj = ?, korisnikID = ? WHERE id  = ?";
		return jdbcTemplate.update(sql, clanskaKarta.getPopust(), clanskaKarta.getBrojPoena(), clanskaKarta.getRegistarskiBroj(), clanskaKarta.getKorisnik().getId(), clanskaKarta.getId());
	}
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM clanskeKarte WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}