package com.harshit.dbms.model;

public class PaymentAndLeaves {
	
	private int id;
	private String month;
	private int year;
	private int leavesAllowed;
	private int leavesTaken;
	private int overtimeWorked;
	private boolean paid;
	private Employee employee;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getLeavesAllowed() {
		return leavesAllowed;
	}
	public void setLeavesAllowed(int leavesAllowed) {
		this.leavesAllowed = leavesAllowed;
	}
	public int getLeavesTaken() {
		return leavesTaken;
	}
	public void setLeavesTaken(int leavesTaken) {
		this.leavesTaken = leavesTaken;
	}
	public int getOvertimeWorked() {
		return overtimeWorked;
	}
	public void setOvertimeWorked(int overtimeWorked) {
		this.overtimeWorked = overtimeWorked;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	@Override
	public String toString() {
		return "PaymentAndLeaves [id=" + id + ", month=" + month + ", year=" + year + ", leavesAllowed=" + leavesAllowed
				+ ", leavesTaken=" + leavesTaken + ", overtimeWorked=" + overtimeWorked + ", isSalaryPaid="
				+ paid + ", employee=" + employee + "]";
	}


}
