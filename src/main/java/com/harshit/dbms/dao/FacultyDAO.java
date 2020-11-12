package com.harshit.dbms.dao;

import com.harshit.dbms.Utils.SetParameters;
import com.harshit.dbms.model.Employee;
import com.harshit.dbms.model.Enroll;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class FacultyDAO {
	
	@Autowired
	private JdbcTemplate jt;
	
	public void update(Faculty faculty) {
		
		 String sql="update faculty set teachingExpirience=?, college=?, year=?, achievement=?, degree=?, teachessubjectID=? where employeeID=?;";
		 jt.update(sql,
				faculty.getTeachingExpirience(),
 				faculty.getCollege(),
 				faculty.getYear(),
 				faculty.getAchievement(),
 				faculty.getDegree(),
 				faculty.getSubject().getSubjectID(),
 				faculty.getEmployee().getEmployeeID());
				
	}
	

	public Faculty save(Faculty faculty) {
		
		
		
		
		String sql="insert into faculty(teachingExpirience,college,year,achievement,degree,employeeID,teachessubjectID) values (?,?,?,?,?,?,?);";
		
		
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jt.update(
	    		new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement pst =
	    	                con.prepareStatement(sql, new String[] {"facultyId"});
	    	            SetParameters.setParameters(pst, 
	    	            		faculty.getTeachingExpirience(),
	    	    				faculty.getCollege(),
	    	    				faculty.getYear(),
	    	    				faculty.getAchievement(),
	    	    				faculty.getDegree(),
	    	    				faculty.getEmployee().getEmployeeID(),
	    	    				faculty.getSubject().getSubjectID());
	    	            return pst;
	    	        }
	    	    },
	    	    keyHolder);
	   
	    faculty.setFacultyID((keyHolder.getKey()).intValue());


		return faculty;
	}
	
	public List<Faculty> allFaculty() {
		
		String sql="select * from faculty natural join employee natural join user, subject where subjectID=teachessubjectID order by facultyID;";

		List<Faculty> f1= jt.query(sql, new RowMapper<Faculty>() {
			
			public Faculty mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Faculty faculty = (new BeanPropertyRowMapper<>(Faculty.class)).mapRow(row,rowNum);
        		Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
        		User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
        		Subject subject = (new BeanPropertyRowMapper<>(Subject.class)).mapRow(row,rowNum);
        		employee.setUser(user);
        		faculty.setEmployee(employee);
        		faculty.setSubject(subject);
        		
        		return faculty;
                     
            }
	
		});
		
		sql="select * from faculty natural join employee natural join user where teachessubjectID is null order by facultyID;";
		
		List<Faculty> f2= jt.query(sql, new RowMapper<Faculty>() {
			
			public Faculty mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Faculty faculty = (new BeanPropertyRowMapper<>(Faculty.class)).mapRow(row,rowNum);
        		Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
        		User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
        		employee.setUser(user);
        		faculty.setEmployee(employee);        		
        		return faculty;
                     
            }
	
		});
		
		List<Faculty> facultyList = new ArrayList<Faculty>();
		facultyList.addAll(f1);
		facultyList.addAll(f2);
		
		return facultyList;
		
	}
	
	public Faculty findByFacultyID(int facultyID) {
        String sql = "select * from faculty natural join employee natural join user where facultyID=?;";
        
        try {
	        return jt.queryForObject(sql, new Object [] {facultyID},new RowMapper<Faculty>() {
	
	        	public Faculty mapRow(ResultSet row, int rowNum) throws SQLException {
	            	
	        		Faculty faculty = (new BeanPropertyRowMapper<>(Faculty.class)).mapRow(row,rowNum);
	        		Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
	        		User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
	        		
	        		Subject subject = null;
	        		if(row.getObject("teachessubjectID",Integer.class)!=null) {
	        			subject=new Subject();
	        			subject.setSubjectID(row.getInt("teachessubjectID"));
	        		}
	        		faculty.setSubject(subject);
	        		
	        		employee.setUser(user);
	        		faculty.setEmployee(employee);
	        		
	        		
	        		return faculty;
	            }
	        	
	        });
        }catch(EmptyResultDataAccessException e){
        	return null;
        } 
        
    }
	
	public Faculty findByUsername(String username) {
        String sql = "select * from faculty natural join employee natural join user where username=?;";
        
        try {
	        return jt.queryForObject(sql, new Object [] {username},new RowMapper<Faculty>() {
	
	        	public Faculty mapRow(ResultSet row, int rowNum) throws SQLException {
	            	
	        		Faculty faculty = (new BeanPropertyRowMapper<>(Faculty.class)).mapRow(row,rowNum);
	        		Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
	        		User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
	        		
	        		employee.setUser(user);
	        		faculty.setEmployee(employee);
	        		
	        		Subject subject = null;
	        		if(row.getObject("teachessubjectID",Integer.class)!=null) {
	        			subject=new Subject();
	        			subject.setSubjectID(row.getInt("teachessubjectID"));
	        		}
	        		faculty.setSubject(subject);
	        		
	        		return faculty;
	            }
	        	
	        });
        }catch(EmptyResultDataAccessException e){
        	return null;
        } 
        
    }
	
		
	public List<Faculty> getFacultyForBatch(int batchID,int session,int courseID) {
		
		String sql="select * from faculty f, employee e, user u, teachesbatch fb where u.username=e.username and "
				+ "e.employeeID=f.employeeID and f.facultyID=fb.facultyID and fb.batchID=? and fb.session=? and fb.courseID=?;";
		
		try {
	        return jt.query(sql, new Object [] {batchID,session,courseID}, new RowMapper<Faculty>() {
	
	        	public Faculty mapRow(ResultSet row, int rowNum) throws SQLException {
	        		Faculty faculty = (new BeanPropertyRowMapper<>(Faculty.class)).mapRow(row,rowNum);
	        		Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
	        		User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
	        		employee.setUser(user);
	        		faculty.setEmployee(employee);
	        		
	        		return faculty;
	                
	            }
	        	
	        });
        }catch(EmptyResultDataAccessException e){
        	return null;
        } 
	}
	
	public boolean hasTaughtStudent(int facultyID,int studentID) {
		
		String sql= "select count(*) from enroll natural join teachesbatch where studentID=? and facultyID=?;";
		
		Integer count= jt.queryForObject(sql,Integer.class,studentID,facultyID);
		
		
		if(count==0) return false;
		else return true;
		
		
	}

}





