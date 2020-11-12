package com.harshit.dbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.harshit.dbms.Utils.Pair;
import com.harshit.dbms.model.Batch;
import com.harshit.dbms.model.Employee;
import com.harshit.dbms.model.Course;
import com.harshit.dbms.model.Faculty;
import com.harshit.dbms.model.Subject;
import com.harshit.dbms.model.Test;
import com.harshit.dbms.model.User;

@Transactional
@Repository
public class BatchDAO {
	
	@Autowired
	private JdbcTemplate jt;
	
	
	public int getNextBatchID(int courseID,int session) {
		
		String sql= "select max(batchID) from batch where courseID = ? and session = ?";
		
		Integer batchCounts= jt.queryForObject(sql,Integer.class,courseID,session);
		
		if (batchCounts==null) return 1;
		else return batchCounts+1;
		
	}
	
	
	public void save(Batch batch) {
		
		String sql="insert into batch(batchID,courseID,session,fees,roomNo,capacity) values (?,?,?,?,?,?);";
		jt.update(sql,
				batch.getBatchID(),
				batch.getCourse().getCourseID(),
				batch.getSession(),
				batch.getFees(),
				batch.getRoomNo(),
				batch.getCapacity()
				);	
	}
	
	public void update(Batch batch,int batchID,int courseID,int session) {
		
		String sql="update batch set batchID=?,courseID=?,session=?,fees=?,roomNo=?,capacity=? where batchID=? and courseID=? and session=?;";
		jt.update(sql,
				batch.getBatchID(),
				batch.getCourse().getCourseID(),
				batch.getSession(),
				batch.getFees(),
				batch.getRoomNo(),
				batch.getCapacity(),
				batchID,
				courseID,
				session				
				);
		
	}
	
	public void delete(int batchID,int courseID,int session){
		
		String sql="delete from batch where batchID=? and courseID=? and session=?;";
		jt.update(sql,batchID,courseID,session);
		
	}
	
	public void deleteTeachesBatch(int batchID,int courseID,int session,int facultyID) {
		
		String sql="delete from teachesbatch where batchID=? and courseID=? and session=? and facultyID=?;";
		jt.update(sql,batchID,courseID,session,facultyID);
		
	}
	
	public void saveTeachesBatch(int batchID,int courseID,int session,int facultyID) {
		
		String sql="insert into teachesbatch(batchID,courseID,session,facultyID) values (?,?,?,?);";
		jt.update(sql,batchID,courseID,session,facultyID);
	
	}
	
	public List<Batch> allBatch() {
		
		String sql="select * from batch natural join course;";

		return jt.query(sql, new RowMapper<Batch>() {
			
			public Batch mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Batch batch = (new BeanPropertyRowMapper<>(Batch.class)).mapRow(row,rowNum);
				Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(row,rowNum);
				batch.setCourse(course);
					            
                return batch;
			}
		});
	}
	
	public List<Batch> getOpenByCourseID(int courseID) {
		
		String sql="select * from batch where courseID=? and session=YEAR(CURDATE()) and batchID not in (select batchID from enroll where session=YEAR(CURDATE()) and courseID=? group by batchID having count(*)>=capacity) ;";

		return jt.query(sql, new Object[] { courseID, courseID },new RowMapper<Batch>() {
			
			public Batch mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Batch batch = (new BeanPropertyRowMapper<>(Batch.class)).mapRow(row,rowNum);
					            
                return batch;
			}
		});
	}
	
	public boolean isOpen(int batchID,int session,int courseID) {
		
		String sql="select batch.batchID from batch where courseID=? and batchID=? and session=? and batchID not in (select batchID from enroll where session=? and courseID=? group by batchID having count(*)>=capacity) ;";
		
		try {
			
			jt.queryForObject(sql, new Object[] { courseID, batchID, session, session, courseID  },new RowMapper<Batch>() {
				public Batch mapRow(ResultSet row, int rowNum) throws SQLException {
					Batch batch = (new BeanPropertyRowMapper<>(Batch.class)).mapRow(row,rowNum);
	                return batch;
	            }		
			});	
			return true;
			
        }catch(EmptyResultDataAccessException e){
        	
        	return false;
        	
        } 
		
	}
	
	public List<Batch> getByCourseID(int courseID) {
		
		String sql="select * from batch where courseID=?;";
		
		try {
			
			return jt.query(sql, new Object[] { courseID },new RowMapper<Batch>() {
				
				public Batch mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Batch batch = (new BeanPropertyRowMapper<>(Batch.class)).mapRow(row,rowNum);
						            
	                return batch;
				}
			});
			
		}catch(EmptyResultDataAccessException e){
        	return null;
        } 

	}
	
	public List<Batch> allOpenBatch() {
		
		String sql= "select * from batch where session=YEAR(CURDATE()) and (batchID,courseID) not in "
				+ "(select batchID,courseID from enroll where session=YEAR(CURDATE()) group by batchID,courseID having count(*)>=capacity);"
		;
		return jt.query(sql, new Object[] {},new RowMapper<Batch>() {
			
			public Batch mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Batch batch = (new BeanPropertyRowMapper<>(Batch.class)).mapRow(row,rowNum);
					            
                return batch;
			}
		});

	}

	public Batch getByBatchID(int batchID,int session,int courseID) {
		
		String sql="select * from batch natural join course where courseID=? and session=? and batchID =?;";
		
		try {
			return jt.queryForObject(sql, new Object[] { courseID, session, batchID },new RowMapper<Batch>() {
				
				public Batch mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Batch batch = (new BeanPropertyRowMapper<>(Batch.class)).mapRow(row,rowNum);
					Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(row,rowNum);
					
					batch.setCourse(course);
						            
	                return batch;
				}
			});
        }catch(EmptyResultDataAccessException e){
        	return null;
        } 

	}
	
	public List<Batch> getEnrolledBatches(int studentID){
		
		String sql="select b.*, c.* from batch b, course c, enroll e where e.studentID=? and e.batchID=b.batchID and e.session=b.session and e.courseID=b.courseID and b.courseID=c.courseID;";
		
		try {
			return jt.query(sql, new Object[] { studentID },new RowMapper<Batch>() {
				
				public Batch mapRow(ResultSet row, int rowNum) throws SQLException {
					
					Batch batch = (new BeanPropertyRowMapper<>(Batch.class)).mapRow(row,rowNum);
					Course course= (new BeanPropertyRowMapper<>(Course.class)).mapRow(row,rowNum); 
					batch.setCourse(course);
						            
	                return batch;
				}
			});
        }catch(EmptyResultDataAccessException e){
        	return null;
        } 
		
	}
	
	
}

