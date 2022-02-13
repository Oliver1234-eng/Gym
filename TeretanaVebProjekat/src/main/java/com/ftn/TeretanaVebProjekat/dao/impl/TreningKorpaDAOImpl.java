package com.ftn.TeretanaVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.ftn.TeretanaVebProjekat.dao.TreningKorpaDAO;
import com.ftn.TeretanaVebProjekat.model.TipTreninga;
import com.ftn.TeretanaVebProjekat.model.Trening;
import com.ftn.TeretanaVebProjekat.model.TreningKorpa;

@Repository
public class TreningKorpaDAOImpl implements TreningKorpaDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class TreningKorpaRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, TreningKorpa> treninziKorpa = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String naziv = resultSet.getString(index++);
			String trener = resultSet.getString(index++);
			String kratakOpis = resultSet.getString(index++);
			String tipTreninga = resultSet.getString(index++);
			Integer cena = resultSet.getInt(index++);
			String vrstaTreninga = resultSet.getString(index++);
			String nivoTreninga = resultSet.getString(index++);
			Integer trajanjeUMinutima = resultSet.getInt(index++);
			Integer prosecnaOcena = resultSet.getInt(index++);
			boolean zakazan = resultSet.getBoolean(index++);

			TreningKorpa treningKorpa = treninziKorpa.get(id);
			if (treningKorpa == null) {
				treningKorpa = new TreningKorpa(id, naziv, trener, kratakOpis, tipTreninga, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan);
				treninziKorpa.put(treningKorpa.getId(), treningKorpa); // dodavanje u kolekciju
			}
		}

		public List<TreningKorpa> getTreninziKorpa() {
			return new ArrayList<>(treninziKorpa.values());
		}

	}

	@Override
	public TreningKorpa findOne(Long id) {
		String sql = 
				"SELECT k.id, k.naziv, k.trener, k.kratakOpis, k.tipTreninga, k.cena, k.vrstaTreninga, k.nivoTreninga, k.trajanjeUMinutima, k.prosecnaOcena, k.zakazan FROM treninziKorpa k " + 
				"WHERE k.id = ? " + 
				"ORDER BY k.id";

		TreningKorpaRowCallBackHandler rowCallbackHandler = new TreningKorpaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getTreninziKorpa().get(0);
	}

	@Override
	public List<TreningKorpa> findAll() {
		String sql = 
				"SELECT k.id, k.naziv, k.trener, k.kratakOpis, k.tipTreninga, k.cena, k.vrstaTreninga, k.nivoTreninga, k.trajanjeUMinutima, k.prosecnaOcena, k.zakazan FROM treninziKorpa k " + 
				"ORDER BY k.id";

		TreningKorpaRowCallBackHandler rowCallbackHandler = new TreningKorpaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getTreninziKorpa();
	}

	@Transactional
	@Override
	public int save(TreningKorpa treningKorpa) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO treninziKorpa (naziv, trener, kratakOpis, tipTreninga, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, treningKorpa.getNaziv());
				preparedStatement.setString(index++, treningKorpa.getTrener());
				preparedStatement.setString(index++, treningKorpa.getKratakOpis());
				preparedStatement.setString(index++, treningKorpa.getTipTreninga());
				preparedStatement.setInt(index++, treningKorpa.getCena());
				preparedStatement.setString(index++, treningKorpa.getVrstaTreninga());
				preparedStatement.setString(index++, treningKorpa.getNivoTreninga());
				preparedStatement.setInt(index++, treningKorpa.getTrajanjeUMinutima());
				preparedStatement.setInt(index++, treningKorpa.getProsecnaOcena());
				preparedStatement.setBoolean(index++, treningKorpa.isZakazan());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(TreningKorpa treningKorpa) {		
		String sql = "UPDATE treninziKorpa SET naziv = ?, trener = ?, kratakOpis = ?, tipTreninga = ?, cena = ?, vrstaTreninga = ?, nivoTreninga = ?, trajanjeUMinutima = ?, prosecnaOcena = ?, zakazan = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, treningKorpa.getNaziv(), treningKorpa.getTrener(), treningKorpa.getKratakOpis(), treningKorpa.getTipTreninga(), treningKorpa.getCena(), treningKorpa.getVrstaTreninga(), treningKorpa.getNivoTreninga(), treningKorpa.getTrajanjeUMinutima(), treningKorpa.getProsecnaOcena(), treningKorpa.isZakazan(), treningKorpa.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM treninziKorpa WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	private class TreningKorpaRowMapper implements RowMapper<TreningKorpa> {

		@Override
		public TreningKorpa mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long treningId = rs.getLong(index++);
			String treningNaziv = rs.getString(index++);
			String treningTrener = rs.getString(index++);
			String treningKratakOpis = rs.getString(index++);
			String treningTipTreninga = rs.getString(index++);
			Integer treningCena = rs.getInt(index++);
			String treningVrstaTreninga = rs.getString(index++);
			String treningNivoTreninga = rs.getString(index++);
			Integer treningTrajanjeUMinutima = rs.getInt(index++);
			Integer treningProsecnaOcena = rs.getInt(index++);

			TreningKorpa treningKorpa = new TreningKorpa(treningId, treningNaziv, treningTrener, treningKratakOpis, 
					treningTipTreninga, treningCena, treningVrstaTreninga, treningNivoTreninga, 
					treningTrajanjeUMinutima, treningProsecnaOcena);
			
			return treningKorpa;
		}

	}
	
	@Override
	public List<TreningKorpa> find(String naziv, String trener, String kratakOpis, String tipTreninga,
			Integer cenaOd, Integer cenaDo, String vrstaTreninga, 
			String nivoTreninga, Integer trajanjeUMinutimaOd, Integer trajanjeUMinutimaDo, 
			Integer prosecnaOcenaOd, Integer prosecnaOcenaDo) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT t.id, t.naziv, t.trener, t.kratakOpis, t.tipTreninga, t.cena, t.vrstaTreninga, t.nivoTreninga, t.trajanjeUMinutima, t.prosecnaOcena FROM treninziKorpa t "; 
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(naziv!=null) {
			naziv = "%" + naziv + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.naziv LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(naziv);
		}

		if(trener!=null) {
			trener = "%" + trener + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.trener LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(trener);
		}
		
		if(kratakOpis!=null) {
			kratakOpis = "%" + kratakOpis + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.kratakOpis LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(kratakOpis);
		}
		
		if(tipTreninga!=null) {
			tipTreninga = "%" + tipTreninga + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.tipTreninga LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(tipTreninga);
		}
		
		if(cenaOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.cena >= ?");
			imaArgumenata = true;
			listaArgumenata.add(cenaOd);
		}
		
		if(cenaDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.cena <= ?");
			imaArgumenata = true;
			listaArgumenata.add(cenaDo);
		}
		
		if(vrstaTreninga!=null) {
			vrstaTreninga = "%" + vrstaTreninga + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.vrstaTreninga LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(vrstaTreninga);
		}
		
		if(nivoTreninga!=null) {
			nivoTreninga = "%" + nivoTreninga + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.nivoTreninga LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(nivoTreninga);
		}
		
		if(trajanjeUMinutimaOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.trajanjeUMinutima >= ?");
			imaArgumenata = true;
			listaArgumenata.add(trajanjeUMinutimaOd);
		}
		
		if(trajanjeUMinutimaDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.trajanjeUMinutima <= ?");
			imaArgumenata = true;
			listaArgumenata.add(trajanjeUMinutimaDo);
		}
		
		if(prosecnaOcenaOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.prosecnaOcena >= ?");
			imaArgumenata = true;
			listaArgumenata.add(prosecnaOcenaOd);
		}
		
		if(prosecnaOcenaDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("t.prosecnaOcena <= ?");
			imaArgumenata = true;
			listaArgumenata.add(prosecnaOcenaDo);
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY t.id";
		else
			sql=sql + " ORDER BY t.id";
		System.out.println(sql);
		
		@SuppressWarnings("deprecation")
		List<TreningKorpa> treninziKorpa = jdbcTemplate.query(sql, listaArgumenata.toArray(), new TreningKorpaRowMapper());
		
		
		return treninziKorpa;
	}
	
}
