package com.harshit.dbms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import com.harshit.dbms.Utils.SetParameters;
import com.harshit.dbms.model.Alumnus;
import com.harshit.dbms.model.AttemptTest;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.User;

@Transactional
@Repository
public class StudentDAO {
	
	@Autowired
	JdbcTemplate jt;
	
	public void update(Student student) {
		
	
		 String sql="update student set parentName=?,parentOccupation=?,collegePGrad=?,percentagePGrad=?,collegeGrad=?,percentageGrad=?,board12th=?,percentage12th=?,board10th=?,percentage10th=?,stream=?,achievement=? where username=?;";
		 jt.update(sql,
				student.getParentName(),
 				student.getParentOccupation(),
 				student.getCollegePGrad(),
 				student.getPercentagePGrad(),
 				student.getCollegeGrad(),
 				student.getPercentageGrad(),
				student.getBoard12th(),
				student.getPercentage12th(),
				student.getBoard10th(),
				student.getPercentage10th(),
				student.getStream(),
				student.getAchievement(),
 				student.getUser().getUsername());
	}
	
	public Student save(Student student) {		

			
		String sql="insert into student(parentName,parentOccupation,collegePGrad,percentagePGrad,collegeGrad,percentageGrad,board12th,percentage12th,board10th,percentage10th,stream,achievement,username) values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
		
					
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jt.update(
	    		new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement pst =
	    	                con.prepareStatement(sql, new String[] {"id"});
	    	            SetParameters.setParameters(pst, 
	    	            		student.getParentName(),
	    	    				student.getParentOccupation(),
	    	    				student.getCollegePGrad(),
	    	    				student.getPercentagePGrad(),
	    	    				student.getCollegeGrad(),
	    	    				student.getPercentageGrad(),
								student.getBoard12th(),
								student.getPercentage12th(),
								student.getBoard10th(),
								student.getPercentage10th(),
								student.getStream(),
								student.getAchievement(),
	    	    				student.getUser().getUsername());
	    	            
	    	            return pst;
	    	            
	    	        }
	    	    },
	    	    keyHolder);
	    student.setStudentID((keyHolder.getKey()).intValue());
	    		
		return student;
		
	}
	
	public List<Student> allStudentNotAlumnus(){
		
		String sql = "select * from student natural join user where studentID not in (select studentID from alumnus);";
		
		try {
			return jt.query(sql, new RowMapper<Student>() {
				
				public Student mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(row,rowNum);	
					User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);	
					student.setUser(user);
					return student;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	public List<Student> allStudent(){
		
		String sql = "select * from student natural join user;";
		
		try {
			return jt.query(sql, new RowMapper<Student>() {
				
				public Student mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(row,rowNum);	
					User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);	
					student.setUser(user);
					
	                return student;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	public List<Student> getByBatch(int courseID, int session, int batchID){
		
		String sql = "select * from student natural join user natural join enroll where courseID=? and session=? and batchID=?;";
		
		try {
			return jt.query(sql, new Object [] {courseID,session,batchID}, new RowMapper<Student>() {
				
				public Student mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(row,rowNum);	
					User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);	
					student.setUser(user);
					
	                return student;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	public List<Student> allStudentNotInBatch(int courseID, int session, int batchID){
		
		String sql = "select * from student s natural join user where s.studentID not in (select e.studentID from enroll e where s.studentID=e.studentID and  courseID=? and session=? and batchID=?);";
		
		try {
			return jt.query(sql, new Object [] {courseID,session,batchID}, new RowMapper<Student>() {
				
				public Student mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(row,rowNum);	
					User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);	
					student.setUser(user);
					
	                return student;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	
	public Student findByUsername(String username){
		
		String sql = "select * from student natural join user where username=?;";
		
		try {
			return jt.queryForObject(sql, new Object[] { username },new RowMapper<Student>() {
				
				public Student mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(row,rowNum);	
					User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);	
					student.setUser(user);
	                return student;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	public List<Student> studentCanTakeTest(int testNumber,int testSeriesID){
		
		String sql="select distinct(studentID) from enroll e, testSeries t where e.courseID=t.courseID and t.testSeriesID=? and studentID not in (select studentID from attempttest where testNumber=? and testSeriesID=?);";

		try {
			return jt.query(sql, new Object[] { testSeriesID, testNumber, testSeriesID }, new RowMapper<Student>() {
				
				public Student mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Student student=new Student();
					student.setStudentID(row.getInt("studentID"));
						            
	                return student;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	public Student findByStudentID(int studentID){
		
		String sql = "select * from student natural join user where studentID=?;";
		
		try {
			return jt.queryForObject(sql, new Object[] { studentID },new RowMapper<Student>() {
				
				public Student mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(row,rowNum);	
					User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);	
					student.setUser(user);
	                return student;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
	}

}

