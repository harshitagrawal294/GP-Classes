package com.harshit.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.harshit.dbms.model.AttemptTest;
import com.harshit.dbms.model.Employee;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.Feedback;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.Test;
import com.harshit.dbms.model.TestSeries;
import com.harshit.dbms.model.User;

@Transactional
@Repository
public class FeedbackDAO {

	
	@Autowired
	private JdbcTemplate jt;
	
	public void save(Feedback feedback) {
		
		String sql="insert into feedback(studentID,facultyID,rating,opinion,complaint) values (?,?,?,?,?);";
		jt.update(sql,
				feedback.getStudent().getStudentID(),
				feedback.getFaculty().getFacultyID(),
				feedback.getRating(),
				feedback.getOpinion(),
				feedback.getComplaint()		
				);	
	}
	
	public List<Feedback> allFeedback() {
		
		String sql="select * from feedback natural join faculty natural join employee natural join user;";
		
		return jt.query(sql,  new RowMapper<Feedback>() {

			public Feedback mapRow(ResultSet row, int rowNum) throws SQLException {
				
				
				Feedback feedback = (new BeanPropertyRowMapper<>(Feedback.class)).mapRow(row,rowNum);
				Faculty faculty = (new BeanPropertyRowMapper<>(Faculty.class)).mapRow(row,rowNum);
				Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);				
				User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);	
				
				Student student = new Student();
				student.setStudentID(row.getInt("studentID"));
				
				employee.setUser(user);
				faculty.setEmployee(employee);
				feedback.setFaculty(faculty);
				feedback.setStudent(student);
				
                return feedback;
			}
		});
		
	}
	
	public void delete(int feedbackID) {
		
		String sql="delete from feedback where feedbackID=?;";

		jt.update(sql,feedbackID);	

	}

	
}