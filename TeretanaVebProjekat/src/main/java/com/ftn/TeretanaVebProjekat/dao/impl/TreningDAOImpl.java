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
	
	/*
	 * @Override public List<Knjiga> findAll(LocalDate datumOd, LocalDate datumDo) {
	 * 
	 * ArrayList<Object> listaArgumenata = new ArrayList<Object>();
	 * 
	 * String sql =
	 * "SELECT k.id, k.naziv, k.registarskiBrojPrimerka, k.jezik, k.brojStranica, k.izdata, k.datum, a.id FROM knjige k "
	 * + "LEFT JOIN knjigeAutori ka ON ka.knjigaId = k.id " +
	 * "LEFT JOIN autori a ON ka.autorId = a.id ";
	 * 
	 * StringBuffer whereSql = new StringBuffer(" WHERE "); boolean imaArgumenata =
	 * false;
	 * 
	 * if(datumOd!=null) { if(imaArgumenata) whereSql.append(" AND ");
	 * whereSql.append("k.datum >= ?"); imaArgumenata = true;
	 * listaArgumenata.add(datumOd); }
	 * 
	 * if(datumDo!=null) { if(imaArgumenata) whereSql.append(" AND ");
	 * whereSql.append("k.datum <= ?"); imaArgumenata = true;
	 * listaArgumenata.add(datumDo); }
	 * 
	 * if(imaArgumenata) sql=sql + whereSql.toString()+" ORDER BY k.id"; else
	 * sql=sql + " ORDER BY k.id"; System.out.println(sql);
	 * 
	 * KnjigaRowCallBackHandler rowCallbackHandler = new KnjigaRowCallBackHandler();
	 * jdbcTemplate.query(sql, rowCallbackHandler);
	 * 
	 * return rowCallbackHandler.getKnjige(); }
	 */
	
	/*
	 * @Override public int findCount(LocalDate datumOd, LocalDate datumDo) { String
	 * sql = "SELECT count(k.id) FROM knjige k " +
	 * "where k.datum >= ? AND k.datum <= ?";
	 * 
	 * return jdbcTemplate.queryForObject(sql, Integer.class, datumOd, datumDo); }
	 */
	
	/*
	 * @Override public int findCount(LocalDate datumOd, LocalDate datumDo, Autor
	 * autor) { String sql = "SELECT count(k.id) FROM knjige k " +
	 * "left join knjigeAutori ka on k.id = ka.knjigaId " +
	 * "left join autori a on ka.autorId = a.id " +
	 * "where datum >= ? and datum <= ? and a.id = ?";
	 * 
	 * return jdbcTemplate.queryForObject(sql, Integer.class, datumOd, datumDo,
	 * autor.getId()); }
	 */
	
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
	
}
