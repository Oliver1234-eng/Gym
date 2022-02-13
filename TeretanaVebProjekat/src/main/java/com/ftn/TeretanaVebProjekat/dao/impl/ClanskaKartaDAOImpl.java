package com.ftn.TeretanaVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.TeretanaVebProjekat.dao.ClanskaKartaDAO;
import com.ftn.TeretanaVebProjekat.dao.KorisnikDAO;
import com.ftn.TeretanaVebProjekat.dao.TreningKorpaDAO;
import com.ftn.TeretanaVebProjekat.model.ClanskaKarta;
import com.ftn.TeretanaVebProjekat.model.Korisnik;
import com.ftn.TeretanaVebProjekat.model.TreningKorpa;

@Repository
public class ClanskaKartaDAOImpl implements ClanskaKartaDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private KorisnikDAO korisnikDAO;
	
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
			Long korisnikId = resultSet.getLong(index++);
			
			Korisnik korisnik = korisnikDAO.findOne(korisnikId);

			ClanskaKarta clanskaKarta = clanskeKarte.get(id);
			if (clanskaKarta == null) {
				clanskaKarta = new ClanskaKarta(id, popust, brojPoena, registarskiBroj, korisnik);
				clanskeKarte.put(clanskaKarta.getId(), clanskaKarta); // dodavanje u kolekciju
			}
			
			clanskaKarta.setKorisnik(korisnik);
		}

		public List<ClanskaKarta> getClanskeKarte() {
			return new ArrayList<>(clanskeKarte.values());
		}

	}
	
	@Override
	public ClanskaKarta findOne(Long id) {
		String sql = 
				"SELECT ck.id, ck.popust, ck.brojPoena, ck.registarskiBroj, ck.korisnikId FROM clanskeKarte ck " +
				"WHERE ck.id = ? " + 
				"ORDER BY ck.id";

		ClanskaKartaRowCallBackHandler rowCallbackHandler = new ClanskaKartaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getClanskeKarte().get(0);
	}
	
	@Override
	public ClanskaKarta findOneByRegistarskiBroj(String registarskiBroj) {
		String sql = 
				"SELECT ck.id, ck.popust, ck.brojPoena, ck.registarskiBroj, ck.korisnikId FROM clanskeKarte ck " +
				"WHERE ck.registarskiBroj = ? " + 
				"ORDER BY ck.id";

		ClanskaKartaRowCallBackHandler rowCallbackHandler = new ClanskaKartaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, registarskiBroj);

		return rowCallbackHandler.getClanskeKarte().get(0);
	}

	@Override
	public List<ClanskaKarta> findAll() {
		String sql = 
				"SELECT ck.id, ck.popust, ck.brojPoena, ck.registarskiBroj, ck.korisnikId FROM clanskeKarte ck " +
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
				String sql = "INSERT INTO clanskeKarte (popust, brojPoena, registarskiBroj, korisnikId) VALUES (?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setInt(index++, clanskaKarta.getPopust());
				preparedStatement.setInt(index++, clanskaKarta.getBrojPoena());
				preparedStatement.setString(index++, clanskaKarta.getRegistarskiBroj());
				preparedStatement.setLong(index++, clanskaKarta.getKorisnik().getId());

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
		
		sql = "UPDATE clanskeKarte SET popust = ?, brojPoena = ?, registarskiBroj = ?, korisnikId = ? WHERE id = ?";	
		uspeh = jdbcTemplate.update(sql, clanskaKarta.getPopust(), clanskaKarta.getBrojPoena(), clanskaKarta.getRegistarskiBroj(), clanskaKarta.getKorisnik().getId(), clanskaKarta.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM clanskeKarte WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}