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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.TeretanaVebProjekat.dao.TreningDAO;
import com.ftn.TeretanaVebProjekat.model.Trening;

@Repository
public class TreningDAOImpl implements TreningDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class TreningRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Trening> treninzi = new LinkedHashMap<>();
		
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

			Trening trening = treninzi.get(id);
			if (trening == null) {
				trening = new Trening(id, naziv, trener, kratakOpis, tipTreninga, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan);
				treninzi.put(trening.getId(), trening); // dodavanje u kolekciju
			}
			
		}

		public List<Trening> getTreninzi() {
			return new ArrayList<>(treninzi.values());
		}

	}

	@Override
	public Trening findOne(Long id) {
		String sql = 
				"SELECT t.id, t.naziv, t.trener, t.kratakOpis, t.tipTreninga, t.cena, t.vrstaTreninga, t.nivoTreninga, t.trajanjeUMinutima, t.prosecnaOcena, t.zakazan FROM treninzi t " + 
				"WHERE t.id = ? " + 
				"ORDER BY t.id";

		TreningRowCallBackHandler rowCallbackHandler = new TreningRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getTreninzi().get(0);
	}

	@Override
	public List<Trening> findAll() {
		String sql = 
				"SELECT t.id, t.naziv, t.trener, t.kratakOpis, t.tipTreninga, t.cena, t.vrstaTreninga, t.nivoTreninga, t.trajanjeUMinutima, t.prosecnaOcena, t.zakazan FROM treninzi t " + 
				"ORDER BY t.id";

		TreningRowCallBackHandler rowCallbackHandler = new TreningRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getTreninzi();
	}
	
	@Transactional
	@Override
	public int save(Trening trening) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO treninzi (naziv, trener, kratakOpis, tipTreninga, cena, vrstaTreninga, nivoTreninga, trajanjeUMinutima, prosecnaOcena, zakazan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, trening.getNaziv());
				preparedStatement.setString(index++, trening.getTrener());
				preparedStatement.setString(index++, trening.getKratakOpis());
				preparedStatement.setString(index++, trening.getTipTreninga());
				preparedStatement.setInt(index++, trening.getCena());
				preparedStatement.setString(index++, trening.getVrstaTreninga());
				preparedStatement.setString(index++, trening.getNivoTreninga());
				preparedStatement.setInt(index++, trening.getTrajanjeUMinutima());
				preparedStatement.setInt(index++, trening.getProsecnaOcena());
				preparedStatement.setBoolean(index++, trening.isZakazan());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Trening trening) {	
		
		String sql = "UPDATE treninzi SET naziv = ?, trener = ?, kratakOpis = ?, tipTreninga = ?, cena = ?, vrstaTreninga = ?, nivoTreninga = ?, trajanjeUMinutima = ?, prosecnaOcena = ?, zakazan = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, trening.getNaziv(), trening.getTrener(), trening.getKratakOpis(), trening.getTipTreninga(), trening.getCena(), trening.getVrstaTreninga(), trening.getNivoTreninga(), trening.getTrajanjeUMinutima(), trening.getProsecnaOcena(), trening.isZakazan(), trening.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM treninzi WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	private class TreningRowMapper implements RowMapper<Trening> {

		@Override
		public Trening mapRow(ResultSet rs, int rowNum) throws SQLException {
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

			Trening trening = new Trening(treningId, treningNaziv, treningTrener, treningKratakOpis, 
					treningTipTreninga, treningCena, treningVrstaTreninga, treningNivoTreninga, 
					treningTrajanjeUMinutima, treningProsecnaOcena);
			return trening;
		}

	}
	
	@Override
	public List<Trening> find(String naziv, String trener, String kratakOpis, 
			String tipTreninga, Integer cenaOd, Integer cenaDo, String vrstaTreninga, 
			String nivoTreninga, Integer trajanjeUMinutimaOd, Integer trajanjeUMinutimaDo, 
			Integer prosecnaOcenaOd, Integer prosecnaOcenaDo) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT t.id, t.naziv, t.trener, t.kratakOpis, t.tipTreninga, t.cena, t.vrstaTreninga, t.nivoTreninga, t.trajanjeUMinutima, t.prosecnaOcena FROM treninzi t "; 
		
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
		
		List<Trening> treninzi = jdbcTemplate.query(sql, listaArgumenata.toArray(), new TreningRowMapper());
		
		return treninzi;
	}
	
}
