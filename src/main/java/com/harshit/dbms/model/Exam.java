package com.harshit.dbms.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Exam{
	
	private int examID;
	@Size(min=2)
	private String examName;
	private String tentativeDate;
	private String prospect;
	private String websiteLink;
	private List<Subject> subjects;
	private List<Course> courses;
	
	public int getExamID() {
		return examID;
	}
	public String getExamName() {
		return examName;
	}
	public String getTentativeDate() {
		return tentativeDate;
	}
	public String getProspect() {
		return prospect;
	}
	public String getWebsiteLink() {
		return websiteLink;
	}
	public void setExamID(int examID) {
		this.examID = examID;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public void setTentativeDate(String tentativeDate) {
		this.tentativeDate = tentativeDate;
	}
	public void setProspect(String prospect) {
		this.prospect = prospect;
	}
	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}
	public List<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	@Override
	public String toString() {
		return "Exam [examID=" + examID + ", examName=" + examName + ", tentativeDate=" + tentativeDate + ", prospect="
				+ prospect + ", websiteLink=" + websiteLink + ", subjects=" + subjects + "]";
	}
	
	
}
