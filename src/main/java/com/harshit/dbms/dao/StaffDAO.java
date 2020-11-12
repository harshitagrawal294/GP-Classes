package com.harshit.dbms.dao;

import com.harshit.dbms.Utils.SetParameters;
import com.harshit.dbms.model.Employee;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.Staff;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.User;

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
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class StaffDAO {
	
	@Autowired
	private JdbcTemplate jt;

	
	public void update(Staff staff) {
		
		 String sql="update staff set work=? where employeeID=?;";
		 jt.update(sql,
				 staff.getWork(),
				 staff.getEmployee().getEmployeeID());
	}
	


	public Staff save(Staff staff) {
		
		String sql="insert into staff(work,employeeID) values (?,?);";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
	    jt.update(
	    		new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement pst = con.prepareStatement(sql, new String[] {"id"});
	    	            SetParameters.setParameters(pst, 
				    	            		staff.getWork(),	    	    				
				    	    				staff.getEmployee().getEmployeeID());
				    	return pst;
	    	        }
	    	    },
	    	    keyHolder);
	   
	    staff.setStaffID((keyHolder.getKey()).intValue());


		return staff;
	}
	
	public List<Staff> allStaff() {
		
		String sql="select * from staff  natural join employee natural join user";
		return jt.query(sql, new RowMapper<Staff>() {
			
			public Staff mapRow(ResultSet row, int rowNum) throws SQLException {
            	
				Staff staff = (new BeanPropertyRowMapper<>(Staff.class)).mapRow(row,rowNum);
        		Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
        		User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
        		employee.setUser(user);
        		staff.setEmployee(employee); 
        		                     
                return staff;
            }
	
		});
		
	}
	
	public Staff findByStaffID(int staffID) {
        String sql = "select * from staff natural join employee natural join user where staffID=?;";
        
        try {
	        return jt.queryForObject(sql, new Object [] {staffID},new RowMapper<Staff>() {
	
	        	public Staff mapRow(ResultSet row, int rowNum) throws SQLException {
	            	
	        		Staff staff = (new BeanPropertyRowMapper<>(Staff.class)).mapRow(row,rowNum);
	        		Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
	        		User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
	      	        		
	        		employee.setUser(user);
	        		staff.setEmployee(employee);
	        		
	        		
	        		return staff;
	            }
	        	
	        });
        }catch(EmptyResultDataAccessException e){
        	return null;
        } 
        
    }
	
	public Staff findByUsername(String username) {
        String sql = "select * from staff natural join employee natural join user where username=?;";
        
        try {
	        return jt.queryForObject(sql, new Object [] {username},new RowMapper<Staff>() {
	
	        	public Staff mapRow(ResultSet row, int rowNum) throws SQLException {
	            	
	        		Staff staff = (new BeanPropertyRowMapper<>(Staff.class)).mapRow(row,rowNum);
	        		Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
	        		User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
	        		
	        		employee.setUser(user);
	        		staff.setEmployee(employee);
	        		
	        		return staff;
	            }
	        	
	        });
        }catch(EmptyResultDataAccessException e){
        	return null;
        } 
        
    }
	

}





