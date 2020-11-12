package com.harshit.dbms.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

import com.harshit.dbms.model.Alumnus;
import com.harshit.dbms.model.Batch;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Exam;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.TestSeries;
import com.harshit.dbms.model.User;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;

@Transactional
@Repository
public class AlumnusDAO{
	
	
	@Autowired
	private JdbcTemplate jt;
	
	public List<Alumnus> allAlumnus(){
		
		String sql = "select * from  alumnus natural join student natural join user;";
		
		try {
			return jt.query(sql, new RowMapper<Alumnus>() {
				
				public Alumnus mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Alumnus alumnus = (new BeanPropertyRowMapper<>(Alumnus.class)).mapRow(row,rowNum);
					Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(row,rowNum);	
					User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);	
					student.setUser(user);
					alumnus.setStudent(student);
					
	                return alumnus;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	public Alumnus getByAlumnusID(int alumnusID) {
		
		String sql = "select * from  alumnus natural join student natural join user where alumnusID=?;";
		
		try {
			return jt.queryForObject(sql, new Object [] {alumnusID}, new RowMapper<Alumnus>() {
				
				public Alumnus mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Alumnus alumnus = (new BeanPropertyRowMapper<>(Alumnus.class)).mapRow(row,rowNum);
					Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(row,rowNum);	
					User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);	
					student.setUser(user);
					alumnus.setStudent(student);
					
	                return alumnus;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}

	}
	
	public void save(Alumnus alumnus) {
		
		String sql="insert into alumnus(reviewWritten,guestLectures,alumAchievement,studentID) values (?,?,?,?);";
		jt.update(sql,
				alumnus.getReviewWritten(),
				alumnus.getGuestLectures(),
				alumnus.getAlumAchievement(),
				alumnus.getStudent().getStudentID()			
				);
		
	}
	
	public void update(Alumnus alumnus) {
		
		String sql="update alumnus set reviewWritten=?, guestLectures=?, alumAchievement=?, studentID=? where alumnusID=?;";
		jt.update(sql,
				alumnus.getReviewWritten(),
				alumnus.getGuestLectures(),
				alumnus.getAlumAchievement(),
				alumnus.getStudent().getStudentID(),
				alumnus.getAlumnusID()
				);
		
	}
	
	public void delete(int alumnusID) {
		
		String sql="delete from alumnus where alumnusID=?;";
		jt.update(sql,alumnusID);
		
	}

}


