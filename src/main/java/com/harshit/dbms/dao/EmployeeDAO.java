package com.harshit.dbms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.harshit.dbms.Utils.SetParameters;
import com.harshit.dbms.model.Employee;
//import com.harshit.dbms.service.UserService;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.User;

@Transactional
@Repository
public class EmployeeDAO {
	
	@Autowired
	private JdbcTemplate jt;
	
	public void update(Employee employee) {
		

		 String sql="update employee set salary=?, accountNumber=?, PAN=?,  joiningDate=? where username=?;";
		 jt.update(sql,
				employee.getSalary(),
 				employee.getAccountNumber(),
 				employee.getPAN(),
 				employee.getJoiningDate(),
 				employee.getUser().getUsername());
				
	}
	
	public Employee save(Employee employee) {
		
		
		String sql="insert into employee(salary,accountNumber,PAN,joiningDate,username) values (?,?,?,?,?);";
		
		
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jt.update(
	    		new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement pst =
	    	                con.prepareStatement(sql, new String[] {"id"});
	    	            SetParameters.setParameters(pst, 
	    	            		employee.getSalary(),
	    	    				employee.getAccountNumber(),
	    	    				employee.getPAN(),
	    	    				employee.getJoiningDate(),
	    	    				employee.getUser().getUsername());
	    	            
	    	            return pst;
	    	            
	    	        }
	    	    },
	    	    keyHolder);
	    employee.setEmployeeID((keyHolder.getKey()).intValue());
	    		

		return employee;
		
	}
	
	public List<Employee> allEmployee() {
		
		String sql="select * from employee natural join user";
		return jt.query(sql, new RowMapper<Employee>() {
			
			public Employee mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
				User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
                employee.setUser(user);
                
                return employee;
            }
	
		});
		
	}
	
	public Employee findByEmployeeID(int employeeID) {
        String sql = "select * from employee natural join user where employeeID='" + employeeID + "'";
        return jt.queryForObject(sql, new RowMapper<Employee>() {
        	
        	public Employee mapRow(ResultSet row, int rowNum) throws SQLException {
				
        		Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
				User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
                employee.setUser(user);
                
                return employee;
            }            
            
        });
    }
	

}
