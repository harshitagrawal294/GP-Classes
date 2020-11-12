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

import com.harshit.dbms.model.Batch;
import com.harshit.dbms.model.TestSeries;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Exam;


@Transactional
@Repository
public class CourseDAO {
	
	@Autowired
	private JdbcTemplate jt;
	
	
	public void save(Course course) {
		
		String sql="insert into course(courseName,introducedInSession,duration,promotionalFeature,examID) values (?,?,?,?,?);";
		jt.update(sql,
				course.getCourseName(),
				course.getIntroducedInSession(),
				course.getDuration(),
				course.getPromotionalFeature(),
				course.getExam().getExamID()		
				);	
	}
	
	public void update(Course course) {
		
		
		String sql="update course set courseName=?,introducedInSession=?,duration=?,promotionalFeature=?,examID=? where courseID=?;";
		jt.update(sql,
				course.getCourseName(),
				course.getIntroducedInSession(),
				course.getDuration(),
				course.getPromotionalFeature(),
				course.getExam().getExamID(),
				course.getCourseID()
				);	
	}
	
	public void delete(int courseID) {
		
		String sql="delete from course where courseID=?;";
		jt.update(sql,courseID);	
	}
		
	public List<Course> allCourse() {
		
		String sql="select * from course natural join exam;";

		return jt.query(sql, new RowMapper<Course>() {
			
			public Course mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(row,rowNum);				
				Exam exam = (new BeanPropertyRowMapper<>(Exam.class)).mapRow(row,rowNum);
				course.setExam(exam);
					            
                return course;
			}
		});
	}
	
	public List<Course> getByExamID(int examID) {
		
		String sql="select * from course where examID=?;";
		
		try {
			return jt.query(sql, new Object[] { examID }, new RowMapper<Course>() {
				
				public Course mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(row,rowNum);					            
	                return course;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			
			return null;
			
		}
		
	}
	
	public Course getByCourseID(int courseID,boolean isBatchOpen) {
		
		String sql="select * from course c natural join exam e where c.courseID=?;";
		
		try {
			
			return jt.queryForObject(sql, new Object[] { courseID }, new RowMapper<Course>() {
				
				public Course mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(row,rowNum);				
					Exam exam = (new BeanPropertyRowMapper<>(Exam.class)).mapRow(row,rowNum);
					course.setExam(exam);
					
					
						            
	                return course;
				}
			});
			
		}catch(EmptyResultDataAccessException e) {
			
			return null;
			
		}

	}
}

