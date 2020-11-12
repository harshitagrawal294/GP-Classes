package com.harshit.dbms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Transaction;

@Transactional
@Repository
public class TransactionDAO {
	
	@Autowired
	private JdbcTemplate jt;	
	
	public Transaction save(Transaction transaction) {
		
		String sql="insert into transaction(transactionDate,transactionTime,amount,mode,verified,token) values (NOW(),NOW(),?,?,?,?);";

	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jt.update(
	    		new PreparedStatementCreator() {
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	            PreparedStatement pst =
	    	                con.prepareStatement(sql, new String[] {"transactionId"});
	    	            SetParameters.setParameters(pst, 
	    	    				transaction.getAmount(),
	    	    				transaction.getMode(),
	    	    				transaction.isVerified(),
	    	    				transaction.getToken()
	    	    				);
	    	            return pst;
	    	        }
	    	    },
	    	    keyHolder);
	   
	    transaction.setTransactionID((keyHolder.getKey()).intValue());

		return transaction;
	
	}
	
	public Transaction updateVerified(String token) {
		
		String sql="update transaction set verified=1 where token=?;";
		jt.update(sql,token);
		
		sql="select * from transaction where token=?";
		
		return jt.queryForObject(sql, new Object[] {token},new RowMapper<Transaction>() {
			
			public Transaction mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Transaction transaction = (new BeanPropertyRowMapper<>(Transaction.class)).mapRow(row,rowNum);
				
				return transaction;
				
			}
		});
		
	}
	
	public void delete(int transactionID) {
		
		String sql="delete from transaction where transactionID=?;";
		jt.update(sql,transactionID);
		
	}
	
	public List<Pair<Transaction,Enroll>> getAllTransaction() {
		
		String sql="select * from transaction natural join enroll;";

		return jt.query(sql, new RowMapper<Pair<Transaction,Enroll>>() {
			
			public Pair<Transaction,Enroll> mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Transaction transaction = (new BeanPropertyRowMapper<>(Transaction.class)).mapRow(row,rowNum);
				Enroll enroll = (new BeanPropertyRowMapper<>(Enroll.class)).mapRow(row,rowNum);
					            
                return new Pair(transaction,enroll);
			}
		});
	}
	
	

}






