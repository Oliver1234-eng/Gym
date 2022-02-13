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

import com.ftn.TeretanaVebProjekat.dao.SalaDAO;
import com.ftn.TeretanaVebProjekat.model.Sala;

@Repository
@Primary
public class SalaDAOImpl implements SalaDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class SalaRowMapper implements RowMapper<Sala> {

		@Override
		public Sala mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			Integer oznaka = rs.getInt(index++);
			Integer kapacitet = rs.getInt(index++);

			Sala sala = new Sala(id, oznaka, kapacitet);
			return sala;
		}

	}
	@Override
	public Sala findOne(Long id) {
		String sql = "SELECT id, oznaka, kapacitet FROM sale WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new SalaRowMapper(), id);
	}
	@Override
	public List<Sala> findAll() {
		String sql = "SELECT id, oznaka, kapacitet FROM sale";
		return jdbcTemplate.query(sql, new SalaRowMapper());
	}
	@Override
	public List<Sala> find(Integer oznaka, Integer kapacitetOd, Integer kapacitetDo) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT id, oznaka, kapacitet FROM sale ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(oznaka!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("oznaka = ?");
			imaArgumenata = true;
			listaArgumenata.add(oznaka);
		}
		
		if(kapacitetOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("kapacitet >= ?");
			imaArgumenata = true;
			listaArgumenata.add(kapacitetOd);
		}
		
		if(kapacitetDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("kapacitet <= ?");
			imaArgumenata = true;
			listaArgumenata.add(kapacitetDo);
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY id";
		else
			sql=sql + " ORDER BY id";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, listaArgumenata.toArray(), new SalaRowMapper());
	}
	@Override
	public int save(Sala sala) {
		String sql = "INSERT INTO sale (oznaka, kapacitet) VALUES (?, ?)";
		return jdbcTemplate.update(sql, sala.getOznaka(), sala.getKapacitet());
	}
	
	@Override
	public int [] save(ArrayList<Sala> sale) {
		String sql = "INSERT INTO sale (oznaka, kapacitet) VALUES (?, ?)";
		
		return jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setInt(1, sale.get(i).getOznaka());
						ps.setInt(2, sale.get(i).getKapacitet());
					}
					
					@Override
					public int getBatchSize() {
						return sale.size();
					}
				});
	}
	
	@Override
	public int update(Sala sala) {
		String sql = "UPDATE sale SET oznaka = ?, kapacitet = ? WHERE id = ?";
		return jdbcTemplate.update(sql, sala.getOznaka(), sala.getKapacitet(), sala.getId());
	}
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM sale WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
}
	
