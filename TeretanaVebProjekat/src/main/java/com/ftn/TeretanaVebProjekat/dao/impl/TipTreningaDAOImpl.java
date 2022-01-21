package com.ftn.TeretanaVebProjekat.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ftn.TeretanaVebProjekat.dao.TipTreningaDAO;
import com.ftn.TeretanaVebProjekat.model.TipTreninga;

@Repository
@Primary
public class TipTreningaDAOImpl implements TipTreningaDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class ZanrRowMapper implements RowMapper<TipTreninga> {

		@Override
		public TipTreninga mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			String naziv = rs.getString(index++);

			TipTreninga tipTreninga = new TipTreninga(id, naziv);
			return tipTreninga;
		}

	}
	@Override
	public TipTreninga findOne(Long id) {
		String sql = "SELECT id, naziv FROM tipTreninga WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new ZanrRowMapper(), id);
	}
	@Override
	public List<TipTreninga> findAll() {
		String sql = "SELECT id, naziv FROM tipTreninga";
		return jdbcTemplate.query(sql, new ZanrRowMapper());
	}
	@Override
	public List<TipTreninga> find(String naziv) {
		naziv = "%" + naziv + "%";
		String sql = "SELECT id, naziv FROM tipTreninga WHERE naziv LIKE ?";
		return jdbcTemplate.query(sql, new ZanrRowMapper(), naziv);
	}
	@Override
	public int save(TipTreninga tipTreninga) {
		String sql = "INSERT INTO tipTreninga (naziv) VALUES (?)";
		return jdbcTemplate.update(sql, tipTreninga.getNaziv());
	}
	
	@Override
	public int [] save(ArrayList<TipTreninga> tipoviTreninga) {
		String sql = "INSERT INTO tipTreninga (naziv) VALUES (?)";
		
		return jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, tipoviTreninga.get(i).getNaziv());
					}
					
					@Override
					public int getBatchSize() {
						return tipoviTreninga.size();
					}
				});
	}
	
	@Override
	public int update(TipTreninga tipTreninga) {
		String sql = "UPDATE tipTreninga SET naziv = ? WHERE id = ?";
		return jdbcTemplate.update(sql, tipTreninga.getNaziv(), tipTreninga.getId());
	}
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM tipTreninga WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
