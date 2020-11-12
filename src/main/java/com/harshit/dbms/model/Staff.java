package com.harshit.dbms.model;

import javax.validation.Valid;

public class Staff {

	private int staffID;
	private String work;
	@Valid
	private Employee employee;
	public int getStaffID() {
		return staffID;
	}
	public String getWork() {
		return work;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@Override
	public String toString() {
		return "Staff [staffID=" + staffID + ", work=" + work + ", employee=" + employee + "]";
	}	
	
}
