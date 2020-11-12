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
import org.springframework.stereotype.Repository;

import com.harshit.dbms.Utils.Pair;
import com.harshit.dbms.Utils.SetParameters;
import com.harshit.dbms.model.Batch;
import com.harshit.dbms.model.Enroll;
import com.harshit.dbms.model.Transaction;

@Transactional
@Repository
public class EnrollDAO {
	
	@Autowired
	private JdbcTemplate jt;
	
	public boolean isEnrolledInCourse(int studentID,int session,int courseID) {
		
		String sql="select count(*) from enroll where courseID=? and session=? and studentID=?;";
		
		Integer count= jt.queryForObject(sql,Integer.class,courseID,session,studentID);
		
		if(count==0) return false;
		else return true;
		
		
	}
	
	public int isEnrolledInBatch(int studentID,int session,int courseID, int batchID) {
		
		String sql="select enrollmentNumber from enroll where courseID=? and session=? and studentID=? and batchID=?;";
		
		try {
			
			Integer enrollmentNumber= jt.queryForObject(sql,Integer.class,courseID,session,studentID,batchID);
			return (enrollmentNumber).intValue();
			
		}catch(EmptyResultDataAccessException e){
			
			return 0;
			
		}
		
	}
	
	public Pair<Enroll,Transaction> save(Transaction transaction,int studentID,int batchID,int session,int courseID) {
		
		
		int transactionID=transaction.getTransactionID();
		
		String sql="insert into enroll(transactionID,studentID,batchID,session,courseID) values (?,?,?,?,?);";

	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jt.update(
	    		new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement pst =
	    	                con.prepareStatement(sql, new String[] {"enrollmentNumber"});
	    	            SetParameters.setParameters(pst, 
	    	            		transactionID,
	    	            		studentID,
	    	            		batchID,
	    	            		session,
	    	            		courseID
	    	            		);
	    	            return pst;
	    	        }
	    	    },
	    	    keyHolder);
	    
	    Enroll enroll = new Enroll();
	    enroll.setEnrollmentNumber((keyHolder.getKey()).intValue());
	    enroll.setCourseID(courseID);
	    enroll.setBatchID(batchID);
	    enroll.setSession(session);
	    enroll.setTransactionID(transactionID);
	    enroll.setStudentID(studentID);

		return new Pair<Enroll,Transaction> (enroll,transaction);
		
	}
	
	

}
