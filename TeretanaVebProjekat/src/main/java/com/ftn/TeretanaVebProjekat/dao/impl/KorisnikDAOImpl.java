package com.ftn.TeretanaVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
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

import com.ftn.TeretanaVebProjekat.dao.KorisnikDAO;
import com.ftn.TeretanaVebProjekat.model.Korisnik;

@Repository
public class KorisnikDAOImpl implements KorisnikDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class KorisnikRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Korisnik> korisnici = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String korisnickoIme = resultSet.getString(index++);
			String lozinka = resultSet.getString(index++);
			String email = resultSet.getString(index++);
			String ime = resultSet.getString(index++);
			String prezime = resultSet.getString(index++);
			LocalDate datumRodjenja = resultSet.getTimestamp(index++).toLocalDateTime().toLocalDate();
			String adresa = resultSet.getString(index++);
			String brojTelefona = resultSet.getString(index++);
			LocalDate datumRegistracije = resultSet.getTimestamp(index++).toLocalDateTime().toLocalDate();
			String uloga = resultSet.getString(index++);

			Korisnik korisnik = korisnici.get(id);
			if (korisnik == null) {
				korisnik = new Korisnik(id, korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, uloga);
				korisnici.put(korisnik.getId(), korisnik); // dodavanje u kolekciju
			}
		}

		public List<Korisnik> getKorisnici() {
			return new ArrayList<>(korisnici.values());
		}

	}
	
	@Override
	public Korisnik findOne(Long id) {
		String sql = 
				"SELECT kor.id, kor.korisnickoIme, kor.lozinka, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumRegistracije, kor.uloga FROM korisnici kor " + 
				"WHERE kor.id = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getKorisnici().get(0);
	}
	
	@Override
	public Korisnik findOne(String email) {
		String sql = 
				"SELECT kor.id, kor.korisnickoIme, kor.lozinka, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumRegistracije, kor.uloga FROM korisnici kor " + 
				"WHERE kor.email = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, email);

		return rowCallbackHandler.getKorisnici().get(0);
	}

	@Override
	public Korisnik findOne(String email, String sifra) {
		String sql = 
				"SELECT kor.id, kor.korisnickoIme, kor.lozinka, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumRegistracije, kor.uloga FROM korisnici kor " + 
				"WHERE kor.email = ? AND " +
				"kor.lozinka = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, email, sifra);

		if(rowCallbackHandler.getKorisnici().size() == 0) {
			return null;
		}
		
		return rowCallbackHandler.getKorisnici().get(0);
	}

	@Override
	public List<Korisnik> findAll() {
		String sql = 
				"SELECT kor.id, kor.korisnickoIme, kor.lozinka, kor.email, kor.ime, kor.prezime, kor.datumRodjenja, kor.adresa, kor.brojTelefona, kor.datumRegistracije, kor.uloga FROM korisnici kor " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getKorisnici();
	}
	
	@Transactional
	@Override
	public int save(Korisnik korisnik) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO korisnici (korisnickoIme, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, uloga) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, korisnik.getKorisnickoIme());
				preparedStatement.setString(index++, korisnik.getLozinka());
				preparedStatement.setString(index++, korisnik.getEmail());
				preparedStatement.setString(index++, korisnik.getIme());
				preparedStatement.setString(index++, korisnik.getPrezime());
				preparedStatement.setTimestamp(index++, Timestamp.valueOf(korisnik.getDatumRodjenja().atStartOfDay()));
				preparedStatement.setString(index++, korisnik.getAdresa());
				preparedStatement.setString(index++, korisnik.getBrojTelefona());
				preparedStatement.setTimestamp(index++, Timestamp.valueOf(korisnik.getDatumRegistracije().atStartOfDay()));
				preparedStatement.setString(index++, korisnik.getUloga());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	//NIJE URADJENO
	
	@Transactional
	@Override
	public int update(Korisnik korisnik) {
		String sql = "UPDATE korisnici SET ime = ?, prezime = ?, email = ?, lozinka = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, korisnik.getIme() , korisnik.getPrezime(), korisnik.getEmail(), korisnik.getLozinka(), korisnik.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM korisnici WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
