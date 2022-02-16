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

import com.ftn.TeretanaVebProjekat.dao.TabelaDAO;
import com.ftn.TeretanaVebProjekat.dao.TerminDAO;
import com.ftn.TeretanaVebProjekat.model.Tabela;
import com.ftn.TeretanaVebProjekat.model.Termin;
import com.ftn.TeretanaVebProjekat.model.Trening;

@Repository
public class TabelaDAOImpl implements TabelaDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class TabelaRowMapper implements RowMapper<Tabela> {

		@Override
		public Tabela mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long tabelaId = rs.getLong(index++);
			String tabelaKorisnik = rs.getString(index++);
			Integer tabelaCena = rs.getInt(index++);
			LocalDateTime tabelaDatumIVreme = rs.getTimestamp(index++).toLocalDateTime();

			Long treningId = rs.getLong(index++);
			String treningNaziv = rs.getString(index++);
			Integer treningTrajanjeUMinutima = rs.getInt(index++);
			Trening trening = new Trening(treningId, treningNaziv, treningTrajanjeUMinutima);

			Tabela tabela = new Tabela(tabelaId, trening, tabelaKorisnik, tabelaCena, tabelaDatumIVreme);
			return tabela;
		}

	}
	@Override
	public Tabela findOne(Long id) {
		String sql = 
				"SELECT p.id, p.korisnik, p.cena, p.datumIVreme, f.id, f.naziv, f.trajanjeUMinutima FROM tabele p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id " + 
				"WHERE p.id = ? " + 
				"ORDER BY p.id";
		return jdbcTemplate.queryForObject(sql, new TabelaRowMapper(), id);
	}
	@Override
	public List<Tabela> findAll() {
		String sql = 
				"SELECT p.id, p.korisnik, p.cena, p.datumIVreme, f.id, f.naziv, f.trajanjeUMinutima FROM tabele p " + 
				"LEFT JOIN treninzi f ON p.treningId = f.id " + 
				"ORDER BY p.id";
		return jdbcTemplate.query(sql, new TabelaRowMapper());
	}
	@Override
	public List<Tabela> find(Long treningId, String korisnik, Integer cenaOd, Integer cenaDo, LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT p.id, p.korisnik, p.cena, p.datumIVreme, f.id, f.naziv, f.trajanjeUMinutima FROM tabele p " + 
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
		
		if(korisnik!=null) {
			korisnik = "%" + korisnik + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.korisnik LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(korisnik);
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
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY p.id";
		else
			sql=sql + " ORDER BY p.id";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new TabelaRowMapper());
	}
	
	@Override
	public List<Tabela> find(HashMap<String, Object> mapaArgumenata) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT p.id, p.korisnik, p.cena, p.datumIVreme, f.id, f.naziv, f.trajanjeUMinutima FROM tabele p " + 
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
		
		if(mapaArgumenata.containsKey("korisnik")) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("p.korisnik LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(mapaArgumenata.get("korisnik"));
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
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY p.id";
		else
			sql=sql + " ORDER BY p.id";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new TabelaRowMapper());
	}
	
	@Override
	public int save(Tabela tabela) {
		String sql = "INSERT INTO tabele (treningId, korisnik, cena, datumIVreme) VALUES (?, ?, ?, ?)";
		return jdbcTemplate.update(sql, tabela.getTrening().getId(), tabela.getKorisnik(), tabela.getCena(), tabela.getDatumIVreme());
	}
	@Override
	public int update(Tabela tabela) {
		String sql = "UPDATE tabele SET treningID = ?, korisnik = ?, cena = ?, datumIVreme = ? WHERE id  = ?";
		return jdbcTemplate.update(sql, tabela.getTrening().getId(), tabela.getKorisnik(), tabela.getCena(), tabela.getDatumIVreme(), tabela.getId());
	}
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM tabele WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}
