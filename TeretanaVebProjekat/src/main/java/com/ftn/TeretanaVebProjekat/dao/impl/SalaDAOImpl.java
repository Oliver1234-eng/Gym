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
public class SalaDAOImpl implements SalaDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class SalaRowMapper implements RowMapper<Sala> {

		@Override
		public Sala mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			String oznakaSale = rs.getString(index++);
			int kapacitet = rs.getInt(index++);

			Sala sala = new Sala(id, oznakaSale, kapacitet);
			return sala;
		}

	}
	
	@Override
	public Sala findOne(Long id) {
		String sql = "SELECT id, oznakaSale, kapacitet FROM sale WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new SalaRowMapper(), id);
	}
	
	@Override
	public List<Sala> findAll() {
		String sql = "SELECT id, oznakaSale, kapacitet FROM sale";
		return jdbcTemplate.query(sql, new SalaRowMapper());
	}
	
	@Override
	public int save(Sala sala) {
		String sql = "INSERT INTO sale (oznakaSale, kapacitet) VALUES (?, ?)";
		return jdbcTemplate.update(sql, sala.getOznakaSale(), sala.getKapacitet());
	}
	
	@Override
	public int [] save(ArrayList<Sala> sale) {
		String sql = "INSERT INTO sale (oznakaSale, kapacitet) VALUES (?, ?)";
		
		return jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, sale.get(i).getOznakaSale());
						ps.setInt(1, sale.get(i).getKapacitet());
					}
					
					@Override
					public int getBatchSize() {
						return sale.size();
					}
				});
	}
	
	@Override
	public int update(Sala sala) {
		String sql = "UPDATE sale SET oznakaSale = ?, kapacitet = ? WHERE id = ?";
		return jdbcTemplate.update(sql, sala.getOznakaSale(), sala.getKapacitet(), sala.getId());
	}
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM sale WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	@Override
	public List<Sala> find(String oznakaSale, Integer kapacitetOd, Integer kapacitetDo) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = "SELECT s.id, s.oznakaSale, s.kapacitet FROM sale s "; 
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(oznakaSale!=null) {
			oznakaSale = "%" + oznakaSale + "%";
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("s.oznakaSale LIKE ?");
			imaArgumenata = true;
			listaArgumenata.add(oznakaSale);
		}
		
		if(kapacitetOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("s.kapacitet >= ?");
			imaArgumenata = true;
			listaArgumenata.add(kapacitetOd);
		}
		
		if(kapacitetDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("s.kapacitet <= ?");
			imaArgumenata = true;
			listaArgumenata.add(kapacitetDo);
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY s.id";
		else
			sql=sql + " ORDER BY s.id";
		System.out.println(sql);
		
		@SuppressWarnings("deprecation")
		List<Sala> sale = jdbcTemplate.query(sql, listaArgumenata.toArray(), new SalaRowMapper());
		
		return sale;
	}
	
}
