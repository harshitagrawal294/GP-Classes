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

import com.harshit.dbms.model.Employee;
import com.harshit.dbms.model.PaymentAndLeaves;



@Transactional
@Repository
public class PaymentAndLeavesDAO {
	
	@Autowired
	private JdbcTemplate jt;

	public void save(PaymentAndLeaves payment) {

		
		String sql="insert into paymentandleaves(month,year,leavesAllowed,leavesTaken,overtimeWorked,paid,employeeID) values (?,?,?,?,?,?,?);";
		jt.update(sql,
				payment.getMonth(),
				payment.getYear(),
				payment.getLeavesAllowed(),
				payment.getLeavesTaken(),
				payment.getOvertimeWorked(),
				payment.isPaid(),
				payment.getEmployee().getEmployeeID()		
				);	
	}
		
	public List<PaymentAndLeaves> findByEmployeeID(int employeeID) {
		
		String sql="select * from paymentandleaves where employeeID=?;";

		return jt.query(sql, new Object[] {employeeID},new RowMapper<PaymentAndLeaves>() {
			
			public PaymentAndLeaves mapRow(ResultSet row, int rowNum) throws SQLException {
				
				PaymentAndLeaves paymentAndLeaves = (new BeanPropertyRowMapper<>(PaymentAndLeaves.class)).mapRow(row,rowNum);				
					            
                return paymentAndLeaves;
			}
		});
	}
	
	public void update(PaymentAndLeaves payment) {

		
		String sql="update  paymentandleaves set month=?, year=?, leavesAllowed=?, leavesTaken=?, overtimeWorked=?, paid=?, employeeID=? where id=?;";
		jt.update(sql,
				payment.getMonth(),
				payment.getYear(),
				payment.getLeavesAllowed(),
				payment.getLeavesTaken(),				
				payment.getOvertimeWorked(),
				payment.isPaid(),
				payment.getEmployee().getEmployeeID(),
				payment.getId()
				);	
	}
	
	public PaymentAndLeaves getByID(int id) {
		
		String sql="select * from paymentandleaves where id=?;";
		try {
			return jt.queryForObject(sql, new Object [] {id}, new RowMapper<PaymentAndLeaves>() {
				public PaymentAndLeaves mapRow(ResultSet row, int rowNum) throws SQLException {				
					PaymentAndLeaves payment = (new BeanPropertyRowMapper<>(PaymentAndLeaves.class)).mapRow(row,rowNum);
					Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(row,rowNum);
					payment.setEmployee(employee);
			        return payment;
	            }
			});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public void delete(int id) {		
		String sql="delete from paymentandleaves where id=?;";
		jt.update(sql,id);	
	}

}
