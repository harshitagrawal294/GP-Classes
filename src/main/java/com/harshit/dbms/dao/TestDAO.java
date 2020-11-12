package com.harshit.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.harshit.dbms.Utils.Pair;
import com.harshit.dbms.model.Test;
import com.harshit.dbms.model.AttemptTest;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Employee;
import com.harshit.dbms.model.TestSeries;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.Staff;
import com.harshit.dbms.model.Student;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.User;

@Transactional
@Repository
public class TestDAO {
	
	@Autowired
	private JdbcTemplate jt;
	
	public int getNextTestNumber(int testSeriesID) {
		
		String sql= "select max(testNumber) from test where testSeriesID= ?";
		
		Integer testCounts= jt.queryForObject(sql,Integer.class,testSeriesID);
		
		if (testCounts==null) return 1;
		else return testCounts+1;
		
	}
	
	public void save(Test test) {
		
		String sql="insert into test(testNumber,testSeriesID,testDate,testTime,testDuration,questionPaperLink,answerKeyLink,difficulty,maximumMarks) values (?,?,?,?,?,?,?,?,?);";
		jt.update(sql,
				test.getTestNumber(),
				test.getTestSeries().getTestSeriesID(),
				test.getTestDate(),
				test.getTestTime(),
				test.getTestDuration(),
				test.getQuestionPaperLink(),
				test.getAnswerKeyLink(),
				test.getDifficulty(),
				test.getMaximumMarks()
				);	
	}
	
	public void update(Test test,int testNumber,int testSeriesID) {
		
		String sql="update test set maximumMarks=?, testNumber=?, testSeriesID=?, testDate=?,testTime=?,testDuration=?,questionPaperLink=?,answerKeyLink=?,difficulty=? where testNumber=? and testSeriesID=?;";
		jt.update(sql,
				test.getMaximumMarks(),
				test.getTestNumber(),
				test.getTestSeries().getTestSeriesID(),
				test.getTestDate(),                     
				test.getTestTime(),                     
				test.getTestDuration(),                 
				test.getQuestionPaperLink(),            
				test.getAnswerKeyLink(),                
				test.getDifficulty(),
				testNumber,                   
				testSeriesID
				);	
	}
	
	public void delete(int testNumber,int testSeriesID) {
		
		String sql="delete from test where testNumber=? and testSeriesID=?;";
		jt.update(sql,testNumber,testSeriesID);	
	}
		
	public List<Test> allTest() {
		
		String sql="select * from test natural join testSeries;";

		return jt.query(sql, new RowMapper<Test>() {
			
			public Test mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Test test = (new BeanPropertyRowMapper<>(Test.class)).mapRow(row,rowNum);				
				TestSeries testSeries = (new BeanPropertyRowMapper<>(TestSeries.class)).mapRow(row,rowNum);
				test.setTestSeries(testSeries);
					            
                return test;
			}
		});
	}
	
	
	public void saveTestAttempt(AttemptTest attemptTest) {
		
		String sql="insert into attempttest(testNumber,testSeriesID, studentID, marksObtained, ratingGiven) values (?,?,?,?,?);";
		jt.update(sql,
				attemptTest.getTest().getTestNumber(),
				attemptTest.getTest().getTestSeries().getTestSeriesID(),
				attemptTest.getStudent().getStudentID(),
				attemptTest.getMarksObtained(),
				attemptTest.getRatingGiven()			
				);
		
	}
	
	public void deleteTestAttempt(int testSeriesID,int testNumber,int studentID) {
		
		String sql="delete from attempttest where testNumber=? and testSeriesID=? and studentID=?;";
		jt.update(sql,testNumber,testSeriesID,studentID);	
		
	}
	
	public Test getByTestNumber(int testNumber,int testSeriesID) {
		
		String sql="select * from test natural join testseries where testNumber=? and testSeriesID=?";
		
		try {
			return jt.queryForObject(sql, new Object[] { testNumber, testSeriesID }, new RowMapper<Test>() {
				
				public Test mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Test test = (new BeanPropertyRowMapper<>(Test.class)).mapRow(row,rowNum);				
					TestSeries testSeries = (new BeanPropertyRowMapper<>(TestSeries.class)).mapRow(row,rowNum);	
					test.setTestSeries(testSeries);
						            
	                return test;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		} 
		
	}
	
	public List<Test> getByTestSeriesID(int testSeriesID) {
		
		String sql="select * from test where testSeriesID=?";
		
		return jt.query(sql, new Object[] { testSeriesID }, new RowMapper<Test>() {
			
			public Test mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Test test = (new BeanPropertyRowMapper<>(Test.class)).mapRow(row,rowNum);				
					            
                return test;
			}
		});
		
	}
	
	public List<AttemptTest> getStudentTakingTest(int testNumber,int testSeriesID){
		
		String sql="select * from attempttest natural join student natural join user where testNumber=? and testSeriesID=?";
		
		try {
			return jt.query(sql, new Object[] {testNumber, testSeriesID }, new RowMapper<AttemptTest>() {
				
				public AttemptTest mapRow(ResultSet row, int rowNum) throws SQLException {
					
					TestSeries testSeries = new TestSeries(row.getInt("testSeriesID"));
					Test test= new Test(testNumber,testSeries);
					
					
					AttemptTest attemptTest = (new BeanPropertyRowMapper<>(AttemptTest.class)).mapRow(row,rowNum);
					Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(row,rowNum);
					User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);	
					
					student.setUser(user);
					attemptTest.setStudent(student);
					attemptTest.setTest(test);
					
	                return attemptTest;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
		
	}
	
	public List<AttemptTest> getAttemptsForStudent(int studentID){
		
		String sql="select * from attempttest natural join test where studentID=?";
		
		try {
			return jt.query(sql, new Object[] {studentID }, new RowMapper<AttemptTest>() {
				
				public AttemptTest mapRow(ResultSet row, int rowNum) throws SQLException {
					
					TestSeries testSeries = new TestSeries(row.getInt("testSeriesID"));
					
					AttemptTest attemptTest = (new BeanPropertyRowMapper<>(AttemptTest.class)).mapRow(row,rowNum);
					Test test = (new BeanPropertyRowMapper<>(Test.class)).mapRow(row,rowNum);
					
					test.setTestSeries(testSeries);
					attemptTest.setTest(test);
					
	                return attemptTest;
				}
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
		
	}
	
	
}

