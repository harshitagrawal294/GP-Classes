package com.harshit.dbms.model;

import javax.persistence.Entity;
import javax.persistence.Id;

public class Feedback {
	
	private int feedbackID;
	private Student student;
	private Faculty faculty;
	private int rating;
	private String opinion;
	private String complaint;
	
	
	public int getFeedbackID() {
		return feedbackID;
	}
	public void setFeedbackID(int feedbackID) {
		this.feedbackID = feedbackID;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getComplaint() {
		return complaint;
	}
	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}
	
	public Student getStudent() {
		return student;
	}
	public Faculty getFaculty() {
		return faculty;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	@Override
	public String toString() {
		return "Feedback [feedbackID=" + feedbackID + ", studentID=" + student + ", facultyID=" + faculty
				+ ", rating=" + rating + ", opinion=" + opinion + ", complaint=" + complaint + "]";
	}
	
}
