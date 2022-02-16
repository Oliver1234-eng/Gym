package com.ftn.TeretanaVebProjekat.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.TeretanaVebProjekat.dao.IzvestajDAO;
import com.ftn.TeretanaVebProjekat.dao.TerminDAO;
import com.ftn.TeretanaVebProjekat.model.Izvestaj;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;

@Repository
public class IzvestajDAOImpl implements IzvestajDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class IzvestajRowMapper implements RowMapper<Izvestaj> {

		@Override
		public Izvestaj mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long izvestajId = rs.getLong(index++);
			String izvestajTrener = rs.getString(index++);
			LocalDateTime izvestajDatumIVreme = rs.getTimestamp(index++).toLocalDateTime();
			Integer izvestajBrojZakazanihTreninga = rs.getInt(index++);
			Integer izvestajCena = rs.getInt(index++);

			Long treningId = rs.getLong(index++);
			String treningNaziv = rs.getString(index++);
			Integer treningTrajanjeUMinutima = rs.getInt(index++);
			Trening trening = new Trening(treningId, treningNaziv, treningTrajanjeUMinutima);

			Izvestaj izvestaj = new Izvestaj(izvestajId, trening, izvestajTrener, izvestajDatumIVreme, izvestajBrojZakazanihTreninga, izvestajCena);
			return izvestaj;
		}

	}
	@Override
	public Izvestaj findOne(Long id) {
		String sql = 
				"SELECT p.id, p.trener, p.datumIVreme, p.brojZakazanihTreninga, p.cena, f.id, f.naziv, f.trajanjeUMinutima FROM izvestaji p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id " + 
				"WHERE p.id = ? " + 
				"ORDER BY p.id";
		return jdbcTemplate.queryForObject(sql, new IzvestajRowMapper(), id);
	}
	@Override
	public List<Izvestaj> findAll() {
		String sql = 
				"SELECT p.id, p.trener, p.datumIVreme, p.brojZakazanihTreninga, p.cena, f.id, f.naziv, f.trajanjeUMinutima FROM izvestaji p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id " + 
				"ORDER BY p.id";
		return jdbcTemplate.query(sql, new IzvestajRowMapper());
	}
	@Override
	public List<Izvestaj> find(Long treningId, String trener, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Integer brojZakazanihTreninga, Integer cenaOd, Integer cenaDo) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT p.id, p.trener, p.datumIVreme, p.brojZakazanihTreninga, p.cena, f.id, f.naziv, f.trajanjeUMinutima FROM izvestaji p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(treningId!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.treningId = ?");
			imaArgumenata = true;
			listaArgumenata.add(treningId);
		}
		
		if(trener!=null) {
			trener = "%" + trener + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.trener LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(trener);
		}
		
		if(datumIVremeOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.datumIVreme >= ?");
			imaArgumenata = true;
			listaArgumenata.add(datumIVremeOd);
		}
		
		if(datumIVremeDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.datumIVreme <= ?");
			imaArgumenata = true;
			listaArgumenata.add(datumIVremeDo);
		}
		
		if(brojZakazanihTreninga!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.brojZakazanihTreninga = ?");
			imaArgumenata = true;
			listaArgumenata.add(brojZakazanihTreninga);
		}
		
		if(cenaOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.cena >= ?");
			imaArgumenata = true;
			listaArgumenata.add(cenaOd);
		}
		
		if(cenaDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.cena <= ?");
			imaArgumenata = true;
			listaArgumenata.add(cenaDo);
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY p.id";
		else
			sql=sql + " ORDER BY p.id";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new IzvestajRowMapper());
	}
	
	@Override
	public List<Izvestaj> find(HashMap<String, Object> mapaArgumenata) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT p.id, p.trener, p.datumIVreme, p.brojZakazanihTreninga, p.cena, f.id, f.naziv, f.trajanjeUMinutima FROM izvestaji p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(mapaArgumenata.containsKey("treningId")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.treningId = ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("treningId"));
		}
		
		if(mapaArgumenata.containsKey("trener")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.trener LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("treningId"));
		}
		
		if(mapaArgumenata.containsKey("datumIVremeOd")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.datumIVreme >= ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("datumIVremeOd"));
		}
		
		if(mapaArgumenata.containsKey("datumIVremeDo")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.datumIVreme <= ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("datumIVremeDo"));
		}
		
		if(mapaArgumenata.containsKey("brojZakazanihTreninga")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.brojZakazanihTreninga = ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("brojZakazanihTreninga"));
		}
		
		if(mapaArgumenata.containsKey("cenaOd")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.cena >= ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("cenaOd"));
		}
		
		if(mapaArgumenata.containsKey("cenaDo")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.cena <= ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("cenaDo"));
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY p.id";
		else
			sql=sql + " ORDER BY p.id";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new IzvestajRowMapper());
	}
	
	@Override
	public int save(Izvestaj izvestaj) {
		String sql = "INSERT INTO izvestaji (treningId, trener, datumIVreme, brojZakazanihTreninga, cena) VALUES (?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, izvestaj.getTrening().getId(), izvestaj.getTrener(), izvestaj.getDatumIVreme(), izvestaj.getBrojZakazanihTreninga(), izvestaj.getCena());
	}
	@Override
	public int update(Izvestaj izvestaj) {
		String sql = "UPDATE izvestaji SET treningID = ?, trener = ?, datumIVreme = ?, brojZakazanihTreninga = ?, cena = ? WHERE id  = ?";
		return jdbcTemplate.update(sql, izvestaj.getTrening().getId(), izvestaj.getTrener(), izvestaj.getDatumIVreme(), izvestaj.getBrojZakazanihTreninga(), izvestaj.getCena(), izvestaj.getId());
	}
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM izvestaji WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}
