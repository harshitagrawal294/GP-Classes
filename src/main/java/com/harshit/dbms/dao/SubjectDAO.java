package com.harshit.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.harshit.dbms.Utils.Pair;
import com.harshit.dbms.model.Employee;
//import com.harshit.dbms.model.Employee;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.User;

@Transactional
@Repository
public class SubjectDAO {
	
	
	@Autowired
	private JdbcTemplate jt;
	
	public void save(Subject subject) {
		
		Integer facultyID;
		if(subject.getHead()==null) {
			facultyID=null;
		}else {
			facultyID=subject.getHead().getFacultyID();
		}
		
		String sql="insert into subject(subjectName,subjectIntroducedIn,headFacultyID) values (?,?,?);";
		jt.update(sql,
				subject.getSubjectName(),
				subject.getSubjectIntroducedIn(),
				facultyID		
				);	
	}
	
	public void update(Subject subject) {
		
		Integer facultyID;
		if(subject.getHead()==null) {
			facultyID=null;
		}else {
			facultyID=subject.getHead().getFacultyID();
		}
		
		String sql="update subject set subjectName=?,subjectIntroducedIn=?,headFacultyID=? where subjectID=?;";
		jt.update(sql,
				subject.getSubjectName(),
				subject.getSubjectIntroducedIn(),
				facultyID,
				subject.getSubjectID()
				);	
	}
	
	public void delete(int subjectID) {
		
		String sql="delete from subject where subjectID=?;";
		jt.update(sql,subjectID);	
	}
		
	public Pair<List<Subject>,List<Subject>> allSubject() {
		
		String sql="select * from subject s, faculty f, employee e, user u where s.headFacultyID=f.facultyID and f.employeeID=e.employeeID and e.username=u.username;";

		List<Subject> subjectWithHead=jt.query(sql, new RowMapper<Subject>() {
			
			public Subject mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Subject subjectWithHead = (new BeanPropertyRowMapper<>(Subject.class)).mapRow(row,rowNum);				
				Faculty faculty = (new BeanPropertyRowMapper<>(Faculty.class)).mapRow(row,rowNum);
				Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
				User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
				
				
				
				employee.setUser(user);
				faculty.setEmployee(employee);
				subjectWithHead.setHead(faculty);
	            
                return subjectWithHead;
			}
		});
		
		sql="select * from subject s where s.headFacultyID is null;";

		
		List<Subject> subjectWithoutHead=jt.query(sql, new RowMapper<Subject>() {
			
			public Subject mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Subject subjectWithoutHead = (new BeanPropertyRowMapper<>(Subject.class)).mapRow(row,rowNum);					
	            
                return subjectWithoutHead;
			}
		});
		
		
		return new Pair<List<Subject>,List<Subject>> (subjectWithHead,subjectWithoutHead);
		
	}
	
	public List<Subject> getByExamID(int examID) {
		String sql="select s.* from subject s, examaskssubject es where s.subjectID=es.subjectID and es.examID=?;";
		try {
			return jt.query(sql, new Object [] {examID}, new RowMapper<Subject>() {
				public Subject mapRow(ResultSet row, int rowNum) throws SQLException {				
					Subject subject = (new BeanPropertyRowMapper<>(Subject.class)).mapRow(row,rowNum);
	                return subject;
	            }
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Subject getBySubjectID(int subjectID) {
		String sql="select * from subject s where s.subjectID=?;";
		try {
			return jt.queryForObject(sql, new Object [] {subjectID}, new RowMapper<Subject>() {
				public Subject mapRow(ResultSet row, int rowNum) throws SQLException {				
					Subject subject = (new BeanPropertyRowMapper<>(Subject.class)).mapRow(row,rowNum);
					
					if(row.getObject("headFacultyID",Integer.class)!=null) {
						int facultyID=row.getInt("headFacultyID");
						Faculty faculty = new Faculty();
						faculty.setFacultyID(facultyID);
						subject.setHead(faculty);
					}
					
	                return subject;
	            }
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
}
