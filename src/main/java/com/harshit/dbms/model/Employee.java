package com.harshit.dbms.model;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

public class Employee {
	
	private int employeeID;
	private float salary;
	private long accountNumber;
	private String PAN;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date joiningDate;
	@Valid
	private User user;
	public int getEmployeeID() {
		return employeeID;
	}
	public float getSalary() {
		return salary;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public String getPAN() {
		return PAN;
	}
	
	public Date getJoiningDate() {
		return joiningDate;
	}

	public User getUser() {
		return user;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setPAN(String pAN) {
		PAN = pAN;
	}
	
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", salary=" + salary + ", accountNumber="
				+ accountNumber + ", PAN=" + PAN
				+ ", joiningDate=" + joiningDate + ", user=" + user + "]";
	}
	
	
}
	