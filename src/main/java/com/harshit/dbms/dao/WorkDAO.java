package com.harshit.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.Test;
import com.harshit.dbms.model.TestSeries;
import com.harshit.dbms.model.Work;

@Transactional
@Repository
public class WorkDAO {
	
	@Autowired
	private JdbcTemplate jt;
	
	public int getNextWorkID(int studentID) {
		
		String sql= "select max(workID) from work where studentID= ?";
		
		Integer workCounts= jt.queryForObject(sql,Integer.class,studentID);
		
		if (workCounts==null) return 1;
		else return workCounts+1;
		
	}
	
	public void save(Work work) {
		
		
		String sql="insert into work(workID,company,workJoiningDate,workRole,workLeavingDate,workDescription,studentID) values (?,?,?,?,?,?,?);";
		jt.update(sql,
				work.getWorkID(),
				work.getCompany(),
				work.getWorkJoiningDate(),
				work.getWorkRole(),
				work.getWorkLeavingDate(),
				work.getWorkDescription(),
				work.getStudent().getStudentID()				
				);	
	}
	
	public void delete(int workID,int studentID) {
		
		String sql="delete from work where workID=? and studentID=?;";
		jt.update(sql,workID,studentID);	
	}
	
	public List<Work> findByStudentID(int studentID){
		
		String sql="select * from work where studentID=? order by workLeavingDate;";

		return jt.query(sql, new Object [] {studentID}, new RowMapper<Work>() {
			
			public Work mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Work work = (new BeanPropertyRowMapper<>(Work.class)).mapRow(row,rowNum);				
					            
                return work;
			}
		});
	}

}
