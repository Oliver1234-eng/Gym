package com.ftn.TeretanaVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.TeretanaVebProjekat.dao.KorisnikDAO;
import com.ftn.TeretanaVebProjekat.model.Korisnik;

@Repository
public class KorisnikDAOImpl implements KorisnikDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class KorisnikRowMapper implements RowMapper<Korisnik> {

		@Override
		public Korisnik mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			String korisnickoIme = rs.getString(index++);
			String email = rs.getString(index++);
			String ime = rs.getString(index++);
			String prezime = rs.getString(index++);
			LocalDateTime datumRodjenja = rs.getTimestamp(index++).toLocalDateTime();
			String adresa = rs.getString(index++);
			String brojTelefona = rs.getString(index++);
			LocalDateTime datumRegistracije = rs.getTimestamp(index++).toLocalDateTime();
			Boolean administrator = rs.getBoolean(index++);

			Korisnik korisnik = new Korisnik(korisnickoIme, null, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, administrator);
			return korisnik;
		}

	}
	
	@Override
	public Korisnik findOne(Long id) {
		try {
			String sql = "SELECT id, korisnickoIme, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, administrator FROM korisnici WHERE id = ?";
			return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(), id);
		} catch (EmptyResultDataAccessException ex) {
			// ako korisnik nije pronađen
			return null;
		}
	}
	
	@Override
	public Korisnik findOne(String korisnickoIme) {
		try {
			String sql = "SELECT korisnickoIme, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, administrator FROM korisnici WHERE korisnickoIme = ?";
			return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(), korisnickoIme);
		} catch (EmptyResultDataAccessException ex) {
			// ako korisnik nije pronađen
			return null;
		}
	}
	
	@Override
	public Korisnik findOne(String korisnickoIme, String lozinka) {
		try {
			String sql = "SELECT korisnickoIme, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, administrator FROM korisnici WHERE korisnickoIme = ? AND lozinka = ?";
			return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(), korisnickoIme, lozinka);
		} catch (EmptyResultDataAccessException ex) {
			// ako korisnik nije pronađen
			return null;
		}
	}
	@Override
	public List<Korisnik> findAll() {
		String sql = "SELECT SELECT korisnickoIme, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, administrator FROM korisnici";
		return jdbcTemplate.query(sql, new KorisnikRowMapper());
	}
	@Override
	public List<Korisnik> find(String korisnickoIme, String email, String ime, String prezime, LocalDateTime datumRodjenjaOd, LocalDateTime datumRodjenjaDo, String adresa, String brojTelefona, LocalDateTime datumRegistracijeOd, LocalDateTime datumRegistracijeDo, Boolean administrator) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT korisnickoIme, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, administrator FROM korisnici ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(korisnickoIme!=null) {
			korisnickoIme = "%" + korisnickoIme + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("korisnickoIme LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(korisnickoIme);
		}
		
		if(email!=null) {
			email = "%" + email + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("email LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(email);
		}
		
		if(ime!=null) {
			ime = "%" + ime + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("ime LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(ime);
		}
		
		if(prezime!=null) {
			prezime = "%" + prezime + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("prezime LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(prezime);
		}
		
		if(datumRodjenjaOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("datumRodjenja >= ?");
			imaArgumenata = true;
			listaArgumenata.add(datumRodjenjaOd);
		}
		
		if(datumRodjenjaDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("datumRodjenja <= ?");
			imaArgumenata = true;
			listaArgumenata.add(datumRodjenjaDo);
		}
		
		if(adresa!=null) {
			adresa = "%" + adresa + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("adresa LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(adresa);
		}
		
		if(brojTelefona!=null) {
			brojTelefona = "%" + brojTelefona + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("brojTelefona LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(brojTelefona);
		}
		
		if(datumRegistracijeOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("datumRegistracije >= ?");
			imaArgumenata = true;
			listaArgumenata.add(datumRegistracijeOd);
		}
		
		if(datumRegistracijeDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("datumRegistracije <= ?");
			imaArgumenata = true;
			listaArgumenata.add(datumRegistracijeDo);
		}
		
		if(administrator!=null) {	
			//vraća samo administratore ili sve korisnike sistema
			String administratorSql = (administrator)? "administrator = 1": "administrator >= 0";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append(administratorSql);
			imaArgumenata = true;
		}
		
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY korisnickoIme";
		else
			sql=sql + " ORDER BY korisnickoIme";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new KorisnikRowMapper());
	}
	
	@Override
	public void save(Korisnik korisnik) {
		String sql = "INSERT INTO korisnici (korisnickoime, lozinka, email, ime, prezime, datumRodjenja, adresa, brojTelefona, datumRegistracije, administrator) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, korisnik.getKorisnickoIme(), korisnik.getLozinka(), korisnik.getEmail(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getAdresa(), korisnik.getBrojTelefona(), korisnik.getDatumRegistracije(), korisnik.isAdministrator());
	}
	
	@Override
	public void update(Korisnik korisnik) {
		if (korisnik.getLozinka() == null) {
			String sql = "UPDATE korisnici SET email = ?, ime = ?, prezime = ?, datumRodjenja = ?, adresa = ?, brojTelefona = ?, datumRegistracije = ?, administrator = ? WHERE korisnickoIme = ?";
			jdbcTemplate.update(sql, korisnik.getEmail(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getAdresa(), korisnik.getBrojTelefona(), korisnik.getDatumRegistracije(), korisnik.isAdministrator(), korisnik.getKorisnickoIme());
		} else {
			String sql = "UPDATE korisnici SET lozinka = ?, email = ?, ime = ?, prezime = ?, datumRodjenja = ?, adresa = ?, brojTelefona = ?, datumRegistracije = ?, administrator = ? WHERE korisnickoIme = ?";
			jdbcTemplate.update(sql, korisnik.getLozinka(), korisnik.getEmail(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getAdresa(), korisnik.getBrojTelefona(), korisnik.getDatumRegistracije(), korisnik.isAdministrator(), korisnik.getKorisnickoIme());
		}
	}
	@Override
	public void delete(String korisnickoIme) {
		String sql = "DELETE FROM korisnici WHERE korisnickoIme = ?";
		jdbcTemplate.update(sql, korisnickoIme);
	}

}
