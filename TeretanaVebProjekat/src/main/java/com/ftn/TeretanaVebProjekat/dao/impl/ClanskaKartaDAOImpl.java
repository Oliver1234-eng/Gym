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
	
	@Autowired
	private TreningKorpaDAO treningKorpaDAO;
	
	private class ClanskaKartaRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, ClanskaKarta> clanskeKarte = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			Integer popust = resultSet.getInt(index++);
			Integer brojPoena = resultSet.getInt(index++);
			String registarskiBroj = resultSet.getString(index++);
			String korisnik = resultSet.getString(index++);
			String status = resultSet.getString(index++);

			ClanskaKarta clanskaKarta = clanskeKarte.get(id);
			if (clanskaKarta == null) {
				clanskaKarta = new ClanskaKarta(id, popust, brojPoena, registarskiBroj, korisnik, status);
				clanskeKarte.put(clanskaKarta.getId(), clanskaKarta); // dodavanje u kolekciju
			}
			
		}

		public List<ClanskaKarta> getClanskeKarte() {
			return new ArrayList<>(clanskeKarte.values());
		}

	}
	
	@Override
	public ClanskaKarta findOne(Long id) {
		String sql = 
				"SELECT ck.id, ck.popust, ck.brojPoena, ck.registarskiBroj, ck.korisnik, ck.status FROM clanskeKarte ck " +
				"WHERE ck.id = ? " + 
				"ORDER BY ck.id";

		ClanskaKartaRowCallBackHandler rowCallbackHandler = new ClanskaKartaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getClanskeKarte().get(0);
	}
	
	@Override
	public ClanskaKarta findOneByRegistarskiBroj(String registarskiBroj) {
		String sql = 
				"SELECT ck.id, ck.popust, ck.brojPoena, ck.registarskiBroj, ck.korisnik, ck.status FROM clanskeKarte ck " +
				"WHERE ck.registarskiBroj = ? " + 
				"ORDER BY ck.id";

		ClanskaKartaRowCallBackHandler rowCallbackHandler = new ClanskaKartaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, registarskiBroj);

		return rowCallbackHandler.getClanskeKarte().get(0);
	}

	@Override
	public List<ClanskaKarta> findAll() {
		String sql = 
				"SELECT ck.id, ck.popust, ck.brojPoena, ck.registarskiBroj, ck.korisnik, ck.status FROM clanskeKarte ck " +
				"ORDER BY ck.id";

		ClanskaKartaRowCallBackHandler rowCallbackHandler = new ClanskaKartaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getClanskeKarte();
	}
	
	@Transactional
	@Override
	public int save(ClanskaKarta clanskaKarta) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO clanskeKarte (popust, brojPoena, registarskiBroj, korisnik, status) VALUES (?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setInt(index++, clanskaKarta.getPopust());
				preparedStatement.setInt(index++, clanskaKarta.getBrojPoena());
				preparedStatement.setString(index++, clanskaKarta.getRegistarskiBroj());
				preparedStatement.setString(index++, clanskaKarta.getKorisnik());
				preparedStatement.setString(index++, clanskaKarta.getStatus());
				

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(ClanskaKarta clanskaKarta) {
		
		String sql = "DELETE FROM treninziClanskeKarte WHERE treningId = ?";
		jdbcTemplate.update(sql, clanskaKarta.getId());
	
		boolean uspeh = true;
		sql = "INSERT INTO treninziClanskeKarte (treningId, clanskaKartaId) VALUES (?, ?)";
		for (TreningKorpa itTreningKorpa: clanskaKarta.getZakazaniTreninzi()) {	
			uspeh = uspeh &&  jdbcTemplate.update(sql, clanskaKarta.getId(), itTreningKorpa.getId()) == 1;
		}
		
		sql = "UPDATE clanskeKarte SET popust = ?, brojPoena = ?, registarskiBroj = ?, korisnik = ?, status = ? WHERE id = ?";	
		uspeh = jdbcTemplate.update(sql, clanskaKarta.getPopust(), clanskaKarta.getBrojPoena(), clanskaKarta.getRegistarskiBroj(), clanskaKarta.getKorisnik(), clanskaKarta.getStatus(), clanskaKarta.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM clanskeKarte WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	
	@Override
	public List<ClanskaKarta> find(Integer popust, Integer brojPoenaOd, Integer brojPoenaDo, String registarskiBroj, String korisnik, String status) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT p.id, p.popust, p.brojPoena, p.registarskiBroj, p.korisnik, p.status FROM clanskeKarte p ";
		
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
		
		if(korisnik!=null) {
			korisnik = "%" + korisnik + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.korisnik LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(korisnik);
		}
		
		if(status!=null) {
			status = "%" + status + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.status LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(status);
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY p.id";
		else
			sql=sql + " ORDER BY p.id";
		System.out.println(sql);
		
		return null;
		//return jdbcTemplate.query(sql, listaArgumenata.toArray(), new ClanskaKartaRowMapper());
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
		
		return null;
		//return jdbcTemplate.query(sql, listaArgumenata.toArray(), new ClanskaKartaRowMapper());
	}
	
	
	
}