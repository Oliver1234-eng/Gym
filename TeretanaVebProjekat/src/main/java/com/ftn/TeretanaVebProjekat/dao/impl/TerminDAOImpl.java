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

import com.ftn.TeretanaVebProjekat.dao.TerminDAO;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;

@Repository
public class TerminDAOImpl implements TerminDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class TerminRowMapper implements RowMapper<Termin> {

		@Override
		public Termin mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long terminId = rs.getLong(index++);
			LocalDateTime terminDatumIVreme = rs.getTimestamp(index++).toLocalDateTime();
			Integer terminSala = rs.getInt(index++);

			Long treningId = rs.getLong(index++);
			String treningNaziv = rs.getString(index++);
			Integer treningTrajanjeUMinutima = rs.getInt(index++);
			Trening trening = new Trening(treningId, treningNaziv, treningTrajanjeUMinutima);

			Termin termin = new Termin(terminId, terminDatumIVreme, trening, terminSala);
			return termin;
		}

	}
	@Override
	public Termin findOne(Long id) {
		String sql = 
				"SELECT p.id, p.datumIVreme, p.sala, f.id, f.naziv, f.trajanjeUMinutima FROM termini p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id " + 
				"WHERE p.id = ? " + 
				"ORDER BY p.id";
		return jdbcTemplate.queryForObject(sql, new TerminRowMapper(), id);
	}
	@Override
	public List<Termin> findAll() {
		String sql = 
				"SELECT p.id, p.datumIVreme, p.sala, f.id, f.naziv, f.trajanjeUMinutima FROM termini p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id " + 
				"ORDER BY p.id";
		return jdbcTemplate.query(sql, new TerminRowMapper());
	}
	@Override
	public List<Termin> find(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, Integer sala) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT p.id, p.datumIVreme, p.sala, f.id, f.naziv, f.trajanjeUMinutima FROM termini p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
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
		
		if(treningId!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.treningId = ?");
			imaArgumenata = true;
			listaArgumenata.add(treningId);
		}
		
		if(sala!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.sala = ?");
			imaArgumenata = true;
			listaArgumenata.add(sala);
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY p.id";
		else
			sql=sql + " ORDER BY p.id";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new TerminRowMapper());
	}
	
	@Override
	public List<Termin> find(HashMap<String, Object> mapaArgumenata) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT p.id, p.datumIVreme, p.sala, f.id, f.naziv, f.trajanjeUMinutima FROM termini p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
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
		
		if(mapaArgumenata.containsKey("treningId")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.treningId = ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("treningId"));
		}
		
		if(mapaArgumenata.containsKey("sala")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.sala = ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("sala"));
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY p.id";
		else
			sql=sql + " ORDER BY p.id";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new TerminRowMapper());
	}
	
	@Override
	public int save(Termin termin) {
		String sql = "INSERT INTO termini (datumIVreme, treningId, sala) VALUES (?, ?, ?)";
		return jdbcTemplate.update(sql, termin.getDatumIVreme(), termin.getTrening().getId(), termin.getSala());
	}
	@Override
	public int update(Termin termin) {
		String sql = "UPDATE termini SET datumIVreme = ?, treningID = ?, sala = ? WHERE id  = ?";
		return jdbcTemplate.update(sql, termin.getDatumIVreme(), termin.getTrening().getId(), termin.getSala(), termin.getId());
	}
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM termini WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}