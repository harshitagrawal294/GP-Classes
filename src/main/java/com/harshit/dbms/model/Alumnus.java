package com.harshit.dbms.model;

import javax.validation.constraints.Size;

public class Alumnus {

	private int alumnusID;
	private String reviewWritten;
	private int guestLectures;
	private String alumAchievement;
	private Student student;
	
	public int getAlumnusID() {
		return alumnusID;
	}
	public String getReviewWritten() {
		return reviewWritten;
	}
	public int getGuestLectures() {
		return guestLectures;
	}
	public String getAlumAchievement() {
		return alumAchievement;
	}
	public Student getStudent() {
		return student;
	}
	public void setAlumnusID(int alumnusID) {
		this.alumnusID = alumnusID;
	}
	public void setReviewWritten(String reviewWritten) {
		this.reviewWritten = reviewWritten;
	}
	public void setGuestLectures(int guestLectures) {
		this.guestLectures = guestLectures;
	}
	public void setAlumAchievement(String alumAchievement) {
		this.alumAchievement = alumAchievement;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	@Override
	public String toString() {
		return "Alumnus [alumnusID=" + alumnusID + ", reviewWritten=" + reviewWritten + ", guestLectures="
				+ guestLectures + ", alumAchievement=" + alumAchievement + ", student=" + student + "]";
	}
	
	
	
	
}
