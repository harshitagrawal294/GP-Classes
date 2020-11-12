package com.harshit.dbms.model;

public class AttemptTest {
	
	private Student student;
	private Test test;
	private int marksObtained;
	private int ratingGiven;
	
	public Student getStudent() {
		return student;
	}
	public Test getTest() {
		return test;
	}
	public int getMarksObtained() {
		return marksObtained;
	}
	public int getRatingGiven() {
		return ratingGiven;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public void setTest(Test test) {
		this.test = test;
	}
	public void setMarksObtained(int marksObtained) {
		this.marksObtained = marksObtained;
	}
	public void setRatingGiven(int ratingGiven) {
		this.ratingGiven = ratingGiven;
	}
	@Override
	public String toString() {
		return "AttemptTest [student=" + student + ", test=" + test + ", marksObtained=" + marksObtained
				+ ", ratingGiven=" + ratingGiven + "]";
	}
	
	
	
}
