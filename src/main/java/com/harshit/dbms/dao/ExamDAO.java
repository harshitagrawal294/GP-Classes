package com.harshit.dbms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

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

import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Exam;

@Transactional
@Repository
public class ExamDAO {
	
	
	@Autowired
	private JdbcTemplate jt;

	
	public void save(Exam exam) {
				
		KeyHolder keyHolder = new GeneratedKeyHolder();
	    jt.update(
	    		new PreparedStatementCreator() {
	    			
	    	        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	    	        	String sql="insert into exam(examName,tentativeDate,prospect,websiteLink) values (?,?,?,?);";
	    	            PreparedStatement pst =
	    	                con.prepareStatement(sql, new String[] {"examID"});
	    	            SetParameters.setParameters(pst, 
	    	            		exam.getExamName(),
	    	    				exam.getTentativeDate(),
	    	    				exam.getProspect(),
	    	    				exam.getWebsiteLink());
	    	            return pst;
	    	        }
	    	    },
	    	    keyHolder);
	   
	    int examID=(keyHolder.getKey()).intValue();   	    
	    List<Subject> subjectsList=exam.getSubjects();
	    ListIterator<Subject> listIterator = subjectsList.listIterator();
	    
	    while(listIterator.hasNext()) {
	    	String sql="insert into examaskssubject(examID,subjectID) values (?,?);";
	        Subject subject=listIterator.next();
	        jt.update(sql,examID,subject.getSubjectID());
	    }
	
	}
		
	public List<Exam> allExam() {
		
		String sql="select * from exam e,subject s,examaskssubject es where e.examID=es.examID and es.subjectID=s.subjectID";
		
		HashMap<Integer, List<Subject>> subjectMap = new HashMap<Integer, List<Subject>>();
		HashMap<Integer, Exam> examMap = new HashMap<Integer, Exam>();
		
		jt.query(sql, new RowMapper<Exam>() {
			
			public Exam mapRow(ResultSet row, int rowNum) throws SQLException {				
				
				Exam exam = (new BeanPropertyRowMapper<>(Exam.class)).mapRow(row,rowNum);
				Subject subject = (new BeanPropertyRowMapper<>(Subject.class)).mapRow(row,rowNum);
				
				if(examMap.containsKey(exam.getExamID())) {
					List<Subject> curSubjectList= subjectMap.get(exam.getExamID());
					curSubjectList.add(subject);				
					subjectMap.replace(exam.getExamID(),curSubjectList);					
				}else {		
					List<Subject> curSubjectList=new ArrayList<Subject>();
					curSubjectList.add(subject);				
					subjectMap.put(exam.getExamID(),curSubjectList);	
					examMap.put(exam.getExamID(),exam);
				}

                return exam;
            }
	
		});
		
		List<Exam> examList= new ArrayList<Exam>();
		Iterator<Entry<Integer, Exam>> hmIterator = examMap.entrySet().iterator(); 
   
        while (hmIterator.hasNext()) { 
            Map.Entry<Integer, Exam> mapElement = (Map.Entry<Integer, Exam>)hmIterator.next(); 
            
            int examID=mapElement.getKey();
            Exam exam=mapElement.getValue();
            List<Subject> subjects=subjectMap.get(examID);
            
            exam.setSubjects(subjects);
            examList.add(exam);
            
        } 
               
        return examList;
		
	}
	
	public Exam getByExamID(int examID) {
		
		String sql="select * from exam e where examID=?;";
		
		try {
			
			return jt.queryForObject(sql, new Object [] {examID}, new RowMapper<Exam>() {
				
				public Exam mapRow(ResultSet row, int rowNum) throws SQLException {				
					
					Exam exam = (new BeanPropertyRowMapper<>(Exam.class)).mapRow(row,rowNum);

	                return exam;
	            }
		
			});
			
		}catch(EmptyResultDataAccessException e) {
			
			return null;
		}
		
	}
	
	public void update(Exam exam) {
		
		String sql="update exam set examName=?,tentativeDate=?,prospect=?,websiteLink=? where examID=?";
		
		jt.update(sql,
				exam.getExamName(),
				exam.getTentativeDate(),
				exam.getProspect(),
				exam.getWebsiteLink(),
				exam.getExamID()
		);
		
		sql = "delete from examaskssubject where examID=?";
		
		jt.update(sql,exam.getExamID());
		
		List<Subject> subjectsList=exam.getSubjects();
	    ListIterator<Subject> listIterator = subjectsList.listIterator();
	    
	    while(listIterator.hasNext()) {
	    	sql="insert into examaskssubject(examID,subjectID) values (?,?);";
	        Subject subject=listIterator.next();
	        jt.update(sql,exam.getExamID(),subject.getSubjectID());
	    }
		
		
	}
	
	public void delete(int examID) {		
		
		String sql = "delete from exam where examID=?";		
		jt.update(sql,examID);
		
	}
	
	
}
