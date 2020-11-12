package com.harshit.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.User;

@Lazy
@Transactional
@Repository
public class UserDAO {
	
	@Autowired
	private JdbcTemplate jt;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void save(User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		String sql="insert into user(username,password,birthDate,active,gender,adhaarNumber,emailID,firstName,lastName,middleName,street,city,state,country,role,token,phone) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		jt.update(sql,
				user.getUsername(),
				user.getPassword(),
				user.getBirthDate(),
				user.isActive(),
				user.getGender(),
				user.getAdhaarNumber(),
				user.getEmailID(),
				user.getFirstName(),
				user.getLastName(),
				user.getMiddleName(),
				user.getStreet(),
				user.getCity(),
				user.getState(),
				user.getCountry(),
				user.getRole(),
				user.getToken(),
				user.getPhone()
				);
		
	}
	
	public boolean updatePassword(String username,String oldPassword,String oldPasswordEntered, String newPassword, boolean isAdmin) {
		
		if(bCryptPasswordEncoder.matches(oldPasswordEntered,oldPassword) || isAdmin) {
			String sql="update user set password=? where username=?;";
			String encodedNewPassword=bCryptPasswordEncoder.encode(newPassword);
			jt.update(sql,encodedNewPassword,username);
			return true;
		}else {
			return false;
		}
		
	}
	
	public void update(User user) {

		String sql="update user set birthDate=?,active=?,gender=?,adhaarNumber=?,firstName=?,lastName=?,middleName=?,street=?,city=?,state=?,country=?,phone=? where username=?;";
		jt.update(sql,
				user.getBirthDate(),
				user.isActive(),
				user.getGender(),
				user.getAdhaarNumber(),
				user.getFirstName(),
				user.getLastName(),
				user.getMiddleName(),
				user.getStreet(),
				user.getCity(),
				user.getState(),
				user.getCountry(),
				user.getPhone(),
				user.getUsername()
				);		
	}
	

	public void delete(String username) {

		String sql = "delete from user where username = ?";
	    jt.update(sql, username);
	}
	
	public boolean userExists(String username) {
		
       String sql = "select count(*) from user where username='" + username + "'";
       
       int found=jt.queryForObject(sql, Integer.class);
       
       if(found==1) return true;
       else return false;
       
	}
	
	public boolean userAdhaarExists(long adhaarNumber) {
		
		String sql = "select count(*) from user where adhaarNumber='" + adhaarNumber + "'";
	       
	    int found=jt.queryForObject(sql, Integer.class);
	       
	    if(found==1) return true;
	    else return false;
	       
	}
	
	

	public boolean userEmailExist(String emailID) {
		
       String sql = "select count(*) from user where emailID='" + emailID + "'";
       
       int found=jt.queryForObject(sql, Integer.class);
       
       if(found>0) return true;
       else return false;
       
	}
	
	public User findByConfirmationToken(String token) {
        String sql = "select * from user where token='" + token + "'";
        try{
        	return jt.queryForObject(sql, new RowMapper<User>() {
                public User mapRow(ResultSet row, int rowNum) throws SQLException {                	
                	User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
                	return user;
                }
            });        
        }catch(EmptyResultDataAccessException e){
        	return null;
        }         
    }
	
	
	public User findByUsername(String username) {
        String sql = "select * from user where username='" + username + "'";        
        try{        	
        	return jt.queryForObject(sql, new RowMapper<User>() {
                public User mapRow(ResultSet row, int rowNum) throws SQLException {                	
                	User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);    
                    return user;
                }
            });        
        }catch(EmptyResultDataAccessException e){
        	return null;
        }         
    }
	

}
