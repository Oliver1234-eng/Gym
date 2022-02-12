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

import com.ftn.TeretanaVebProjekat.dao.KomentarDAO;
import com.ftn.TeretanaVebProjekat.model.Komentar;
import com.ftn.TeretanaVebProjekat.model.Trening;

@Repository
public class KomentarDAOImpl implements KomentarDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class KomentarRowMapper implements RowMapper<Komentar> {

		@Override
		public Komentar mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long komentarId = rs.getLong(index++);
			String komentarTekst = rs.getString(index++);
			Integer komentarOcena = rs.getInt(index++);
			LocalDateTime komentarDatumIVreme = rs.getTimestamp(index++).toLocalDateTime();
			String komentarStatus = rs.getString(index++);

			Long treningId = rs.getLong(index++);
			String treningNaziv = rs.getString(index++);
			Integer treningTrajanjeUMinutima = rs.getInt(index++);
			Trening trening = new Trening(treningId, treningNaziv, treningTrajanjeUMinutima);

			Komentar komentar = new Komentar(komentarId, komentarTekst, komentarOcena, komentarDatumIVreme, trening, komentarStatus);
			return komentar;
		}

	}
	@Override
	public Komentar findOne(Long id) {
		String sql = 
				"SELECT p.id, p.tekst, p.ocena, p.datumIVreme, f.id, f.naziv, f.trajanjeUMinutima, p.status FROM komentari p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id " + 
				"WHERE p.id = ? " + 
				"ORDER BY p.id";
		return jdbcTemplate.queryForObject(sql, new KomentarRowMapper(), id);
	}
	@Override
	public List<Komentar> findAll() {
		String sql = 
				"SELECT p.id, p.tekst, p.ocena, p.datumIVreme, f.id, f.naziv, f.trajanjeUMinutima, p.status FROM komentari p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id " + 
				"ORDER BY p.id";
		return jdbcTemplate.query(sql, new KomentarRowMapper());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public List<Komentar> find(String tekst, Integer ocenaOd, Integer ocenaDo, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, Long treningId, String status) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT p.id, p.tekst, p.ocena, p.datumIVreme, f.id, f.naziv, f.trajanjeUMinutima, p.status FROM komentari p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(tekst!=null) {
			tekst = "%" + tekst + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.tekst LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(tekst);
		}
		
		if(ocenaOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.ocena >= ?");
			imaArgumenata = true;
			listaArgumenata.add(ocenaOd);
		}
		
		if(ocenaDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.ocena <= ?");
			imaArgumenata = true;
			listaArgumenata.add(ocenaDo);
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
		
		if(treningId!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.treningId = ?");
			imaArgumenata = true;
			listaArgumenata.add(treningId);
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
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new KomentarRowMapper());
	}
	
	@Override
	public List<Komentar> find(HashMap<String, Object> mapaArgumenata) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT p.id, p.tekst, p.ocena, p.datumIVreme, f.id, f.naziv, f.trajanjeUMinutima, p.status FROM komentari p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(mapaArgumenata.containsKey("tekst")) {
			String tekst = "%" + mapaArgumenata.get("tekst") + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.tekst LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(tekst);
		}
		
		if(mapaArgumenata.containsKey("ocenaOd")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.ocena >= ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("ocenaOd"));
		}
		
		if(mapaArgumenata.containsKey("ocenaDo")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.ocena <= ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("ocenaDo"));
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
		
		if(mapaArgumenata.containsKey("treningId")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.treningId = ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("treningId"));
		}
		
		if(mapaArgumenata.containsKey("status")) {
			String status = "%" + mapaArgumenata.get("status") + "%";
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
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new KomentarRowMapper());
	}
	
	@Override
	public int save(Komentar komentar) {
		String sql = "INSERT INTO komentari (tekst, ocena, datumIVreme, treningId, status) VALUES (?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, komentar.getTekst(), komentar.getOcena(), komentar.getDatumIVreme(), komentar.getTrening().getId(), komentar.getStatus());
	}
	@Override
	public int update(Komentar komentar) {
		String sql = "UPDATE komentari SET tekst = ?, ocena = ?, datumIVreme = ?, treningID = ?, status = ? WHERE id  = ?";
		return jdbcTemplate.update(sql, komentar.getTekst(), komentar.getOcena(), komentar.getDatumIVreme(), komentar.getTrening().getId(), komentar.getStatus(), komentar.getId());
	}
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM komentari WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}
