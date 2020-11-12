package com.harshit.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.harshit.dbms.Utils.Pair;
import com.harshit.dbms.model.TestSeries;
import com.harshit.dbms.model.Employee;
import com.harshit.dbms.model.Batch;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.Test;
import com.harshit.dbms.model.User;

@Transactional
@Repository
public class TestSeriesDAO {
	
	@Autowired
	private JdbcTemplate jt;
	
	public void save(TestSeries testSeries) {
		
		String sql="insert into testSeries(testSeriesName,mode,level,courseID) values (?,?,?,?);";
		jt.update(sql,
				testSeries.getTestSeriesName(),
				testSeries.getMode(),
				testSeries.getLevel(),
				testSeries.getCourse().getCourseID()		
				);	
	}
	
	public void update(TestSeries testSeries) {
		
		
		String sql="update testSeries set testSeriesName=?,mode=?,level=?,courseID=? where testSeriesID=?;";
		jt.update(sql,
				testSeries.getTestSeriesName(),
				testSeries.getMode(),
				testSeries.getLevel(),
				testSeries.getCourse().getCourseID(),
				testSeries.getTestSeriesID()
				);	
	}
	
	public void delete(int testSeriesID) {
		
		String sql="delete from testseries where testSeriesID=?;";
		jt.update(sql,testSeriesID);	
	}
		
	public List<TestSeries> allTestSeries() {
		
		String sql="select * from testseries natural join course;";

		return jt.query(sql, new RowMapper<TestSeries>() {
			
			public TestSeries mapRow(ResultSet row, int rowNum) throws SQLException {
				
				TestSeries testSeries = (new BeanPropertyRowMapper<>(TestSeries.class)).mapRow(row,rowNum);				
				Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(row,rowNum);
				testSeries.setCourse(course);
					            
                return testSeries;
			}
		});
	}
	
	public List<TestSeries> getByCourseID(int courseID) {
		
		String sql="select * from testseries where courseID=?;";
		
		return jt.query(sql, new Object[] { courseID },new RowMapper<TestSeries>() {
			
			public TestSeries mapRow(ResultSet row, int rowNum) throws SQLException {
				
				TestSeries testSeries = (new BeanPropertyRowMapper<>(TestSeries.class)).mapRow(row,rowNum);
				
                return testSeries;
			}
		});
	}
	
	public TestSeries getByTestSeriesID(int testSeriesID) {
		
		String sql="select * from testseries natural join course where testSeriesID=?;";
		
		try {
			
			return jt.queryForObject(sql, new Object[] { testSeriesID },new RowMapper<TestSeries>() {
				
				public TestSeries mapRow(ResultSet row, int rowNum) throws SQLException {
					
					TestSeries testSeries = (new BeanPropertyRowMapper<>(TestSeries.class)).mapRow(row,rowNum);
					
					Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(row,rowNum);
					testSeries.setCourse(course);  
						            
	                return testSeries;
				}
			});
			
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
		
	}
}

