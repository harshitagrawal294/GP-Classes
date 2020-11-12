package com.harshit.dbms.model;

import java.util.List;

import javax.validation.Valid;

public class Course {
	
	private int courseID;
	private String courseName;
	private int introducedInSession;
	private int duration;
	private String promotionalFeature;
	@Valid
	private Exam exam;
	private List<Batch> batches;
	private List<TestSeries> testSeries;
	
	public int getCourseID() {
		return courseID;
	}
	public String getCourseName() {
		return courseName;
	}
	public int getIntroducedInSession() {
		return introducedInSession;
	}
	public int getDuration() {
		return duration;
	}
	public String getPromotionalFeature() {
		return promotionalFeature;
	}
	public Exam getExam() {
		return exam;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public void setIntroducedInSession(int introducedInSession) {
		this.introducedInSession = introducedInSession;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public List<Batch> getBatches() {
		return batches;
	}
	public List<TestSeries> getTestSeries() {
		return testSeries;
	}
	public void setPromotionalFeature(String promotionalFeature) {
		this.promotionalFeature = promotionalFeature;
	}
	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}
	public void setTestSeries(List<TestSeries> testSeries) {
		this.testSeries = testSeries;
	}
	
	@Override
	public String toString() {
		return "Course [courseID=" + courseID + ", courseName=" + courseName + ", introducedInSession="
				+ introducedInSession + ", duration=" + duration + ", promotionalFeature=" + promotionalFeature
				+ ", exam=" + exam + "]";
	}
	
	
	
	
	
}
