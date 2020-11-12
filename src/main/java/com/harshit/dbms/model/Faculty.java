package com.harshit.dbms.model;

import javax.validation.Valid;

public class Faculty {
	
	private int facultyID;
	private int teachingExpirience;
	private String college;
	private int year;
	private String achievement;
	private String degree;
	@Valid
	private Employee employee;
	private Subject subject;
	
	public int getFacultyID() {
		return facultyID;
	}
	public int getTeachingExpirience() {
		return teachingExpirience;
	}
	public String getCollege() {
		return college;
	}
	public int getYear() {
		return year;
	}
	public String getAchievement() {
		return achievement;
	}
	public String getDegree() {
		return degree;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setFacultyID(int facultyID) {
		this.facultyID = facultyID;
	}
	public void setTeachingExpirience(int teachingExpirience) {
		this.teachingExpirience = teachingExpirience;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
//	@Override
//	public String toString() {
//		return "Faculty [facultyID=" + facultyID + "]";
//	}
	
	public Subject getSubject() {
		return subject;
	}
	@Override
	public String toString() {
		return "Faculty [facultyID=" + facultyID + ", teachingExpirience=" + teachingExpirience + ", college=" + college
				+ ", year=" + year + ", achievement=" + achievement + ", degree=" + degree + ", employee=" + employee
				+ ", subject=" + subject + "]";
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	
	
}
