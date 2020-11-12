package com.harshit.dbms.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Work {
	
	private int workID;
	private String company;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date workJoiningDate;
	private Student student;
	private String workRole;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date workLeavingDate;
	private String workDescription;
	public int getWorkID() {
		return workID;
	}
	public String getCompany() {
		return company;
	}
	public Date getWorkJoiningDate() {
		return workJoiningDate;
	}
	public Student getStudent() {
		return student;
	}
	public String getWorkRole() {
		return workRole;
	}
	public Date getWorkLeavingDate() {
		return workLeavingDate;
	}
	public String getWorkDescription() {
		return workDescription;
	}
	public void setWorkID(int workID) {
		this.workID = workID;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public void setWorkJoiningDate(Date workJoiningDate) {
		this.workJoiningDate = workJoiningDate;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public void setWorkRole(String workRole) {
		this.workRole = workRole;
	}
	public void setWorkLeavingDate(Date workLeavingDate) {
		this.workLeavingDate = workLeavingDate;
	}
	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}
	@Override
	public String toString() {
		return "work [workID=" + workID + ", company=" + company + ", workJoiningDate=" + workJoiningDate + ", student="
				+ student + ", workRole=" + workRole + ", workLeavingDate=" + workLeavingDate + ", workDescription="
				+ workDescription + "]";
	}
	
	
	
}
