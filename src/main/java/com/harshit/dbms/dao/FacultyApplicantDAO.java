package com.harshit.dbms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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

import com.harshit.dbms.Utils.SetParameters;
import com.harshit.dbms.model.FacultyApplicant;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.Test;
import com.harshit.dbms.model.TestSeries;

@Transactional
@Repository
public class FacultyApplicantDAO {
	
	@Autowired
	private JdbcTemplate jt;
	
	public FacultyApplicant save(FacultyApplicant facultyApplicant) {
		
		
		String sql="insert into facultyApplicant(applicantName,resumeLink,applicantPhoneNumber,applicantEmailID,applicationProcessed,applicationDate,applicationReviewStatus,subjectID,confirmationToken) values (?,?,?,?,?,?,?,?,?);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
	    jt.update(
	    		new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement pst = con.prepareStatement(sql, new String[] {"applicationID"});
	    	            SetParameters.setParameters(pst, 
	    	            		facultyApplicant.getApplicantName(),
	    	    				facultyApplicant.getResumeLink(),
	    	    				facultyApplicant.getApplicantPhoneNumber(),
	    	    				facultyApplicant.getApplicantEmailID(),
	    	    				facultyApplicant.isApplicationProcessed(),
	    	    				facultyApplicant.getApplicationDate(),
	    	    				facultyApplicant.isApplicationReviewStatus(),
	    	    				facultyApplicant.getSubject().getSubjectID(),
	    	    				facultyApplicant.getConfirmationToken()
	    	            		);
	    	            
	    	            return pst;
	    	            
	    	        }
	    	    },
	    	    keyHolder);
	    facultyApplicant.setApplicationID((keyHolder.getKey()).intValue());
	   
		return facultyApplicant;
	}
	
	public FacultyApplicant processed(FacultyApplicant facultyApplicant) {
		
		String sql="update facultyApplicant set applicationProcessed=true where applicationID=?;";

		jt.update(sql,facultyApplicant.getApplicationID());	
	   
		return facultyApplicant;
	}
	
	public void reviewed(int applicationID) {
		
		String sql="update facultyApplicant set applicationReviewStatus=true where applicationID=?;";

		jt.update(sql,applicationID);	

	}
	
	public void delete(int applicationID) {
		
		String sql="delete from facultyApplicant where applicationID=?;";

		jt.update(sql,applicationID);	

	}
		
	public List<FacultyApplicant> allFacultyApplicant() {
		
		String sql="select * from facultyApplicant natural join subject order by applicationID;";

		return jt.query(sql, new RowMapper<FacultyApplicant>() {
			
			public FacultyApplicant mapRow(ResultSet row, int rowNum) throws SQLException {
				
				FacultyApplicant facultyApplicant = (new BeanPropertyRowMapper<>(FacultyApplicant.class)).mapRow(row,rowNum);				
				Subject subject = (new BeanPropertyRowMapper<>(Subject.class)).mapRow(row,rowNum);
				facultyApplicant.setSubject(subject);
					            
                return facultyApplicant;
			}
		});
	}
	
	
	
	public FacultyApplicant findByConfirmationToken(String confirmationToken) {
		
		String sql="select * from facultyapplicant where confirmationToken=?;";
		
		try {
			return jt.queryForObject(sql, new Object[] { confirmationToken }, new RowMapper<FacultyApplicant>() {
				
				public FacultyApplicant mapRow(ResultSet row, int rowNum) throws SQLException {
					
					FacultyApplicant facultyApplicant = (new BeanPropertyRowMapper<>(FacultyApplicant.class)).mapRow(row,rowNum);
						            
	                return facultyApplicant;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		} 
		
	}
	
	public FacultyApplicant findByApplicationID(int applicationID) {
		
		String sql="select * from facultyapplicant where applicationID=?";
		
		try {
			return jt.queryForObject(sql, new Object[] { applicationID }, new RowMapper<FacultyApplicant>() {
				
				public FacultyApplicant mapRow(ResultSet row, int rowNum) throws SQLException {
					
					FacultyApplicant facultyApplicant = (new BeanPropertyRowMapper<>(FacultyApplicant.class)).mapRow(row,rowNum);				
					Subject subject = (new BeanPropertyRowMapper<>(Subject.class)).mapRow(row,rowNum);
					facultyApplicant.setSubject(subject);
						            
	                return facultyApplicant;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		} 
		
		
	}
	
	

}


