package com.harshit.dbms.model;

import java.util.List;

public class TestSeries {
	
	private int testSeriesID;
	private String testSeriesName;
	private String mode;
	private int level;
	private Course course;
	private List<Test> testList;
	public int getTestSeriesID() {
		return testSeriesID;
	}
	public String getTestSeriesName() {
		return testSeriesName;
	}
	public String getMode() {
		return mode;
	}
	public int getLevel() {
		return level;
	}
	public Course getCourse() {
		return course;
	}
	public void setTestSeriesID(int testSeriesID) {
		this.testSeriesID = testSeriesID;
	}
	public void setTestSeriesName(String testSeriesName) {
		this.testSeriesName = testSeriesName;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public List<Test> getTestList() {
		return testList;
	}
	public void setTestList(List<Test> testList) {
		this.testList = testList;
	}
	@Override
	public String toString() {
		return "TestSeries [testSeriesID=" + testSeriesID + ", testSeriesName=" + testSeriesName + ", mode=" + mode
				+ ", level=" + level + ", course=" + course + "]";
	}
	public TestSeries(int testSeriesID) {
		super();
		this.testSeriesID = testSeriesID;
	}
	public TestSeries() {
		super();
	}

}
