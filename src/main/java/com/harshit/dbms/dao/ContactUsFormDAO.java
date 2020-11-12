package com.harshit.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.harshit.dbms.model.ContactUsForm;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Exam;

@Transactional
@Repository
public class ContactUsFormDAO {
	
	@Autowired
	private JdbcTemplate jt;
	
	public void save(ContactUsForm contactUsForm) {
		
		String sql="insert into contactusform(phoneNumber,emailID,query,name,replyGiven,showFAQ) values (?,?,?,?,?,false);";
		jt.update(sql,
				contactUsForm.getPhoneNumber(),
				contactUsForm.getEmailID(),
				contactUsForm.getQuery(),
				contactUsForm.getName(),
				contactUsForm.getReplyGiven()	
				);	
	}
	
	public void updateReply(int queryID,String query,String reply) {
		
		String sql="update contactusform set query=?,replyGiven=? where queryID=?;";
		jt.update(sql,
				query,
				reply,
				queryID
				);	
	}
	
	public void delete(int queryID) {
		
		String sql="delete from contactusform where queryID=?;";
		jt.update(sql,
				queryID
				);	
	}
	
	public void show(int queryID) {
		
		String sql="update contactusform set showFAQ=true where queryID=?;";
		jt.update(sql,
				queryID
				);	
	}
	
	public void hide(int queryID) {
		
		String sql="update contactusform set showFAQ=false where queryID=?;";
		jt.update(sql,
				queryID
				);	
	}
	
	public List<ContactUsForm> allContactUsForm() {
		
		String sql="select * from contactusform;";

		return jt.query(sql, new RowMapper<ContactUsForm>() {
			
			public ContactUsForm mapRow(ResultSet row, int rowNum) throws SQLException {
				
				ContactUsForm contactUsForm = (new BeanPropertyRowMapper<>(ContactUsForm.class)).mapRow(row,rowNum);				
					            
                return contactUsForm;
			}
		});
	}
	
	public ContactUsForm getByQueryID(int queryID) {
		
		String sql="select * from contactusform where queryID=?;";

		return jt.queryForObject(sql,new Object [] {queryID}, new RowMapper<ContactUsForm>() {
			
			public ContactUsForm mapRow(ResultSet row, int rowNum) throws SQLException {
				
				ContactUsForm contactUsForm = (new BeanPropertyRowMapper<>(ContactUsForm.class)).mapRow(row,rowNum);				
					            
                return contactUsForm;
			}
		});
	}

}
