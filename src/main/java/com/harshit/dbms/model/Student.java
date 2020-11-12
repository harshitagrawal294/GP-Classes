package com.harshit.dbms.model;

import javax.validation.Valid;


public class Student {
	
	private int studentID;
	private String parentName;
	private String parentOccupation;
	private String collegePGrad;
	private int percentagePGrad;
	private String collegeGrad;
	private int percentageGrad;
	private String board12th;
	private int percentage12th;
	private String board10th;
	private int percentage10th;
	private String stream;
	private String achievement;
	@Valid
	private User user;
	
	public int getStudentID() {
		return studentID;
	}
	public String getParentName() {
		return parentName;
	}
	public String getParentOccupation() {
		return parentOccupation;
	}
	public String getCollegePGrad() {
		return collegePGrad;
	}
	public int getPercentagePGrad() {
		return percentagePGrad;
	}
	public String getCollegeGrad() {
		return collegeGrad;
	}
	public int getPercentageGrad() {
		return percentageGrad;
	}
	public String getBoard12th() {
		return board12th;
	}
	public int getPercentage12th() {
		return percentage12th;
	}
	public String getBoard10th() {
		return board10th;
	}
	public int getPercentage10th() {
		return percentage10th;
	}
	public String getStream() {
		return stream;
	}
	public String getAchievement() {
		return achievement;
	}
	public User getUser() {
		return user;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public void setParentOccupation(String parentOccupation) {
		this.parentOccupation = parentOccupation;
	}
	public void setCollegePGrad(String collegePGrad) {
		this.collegePGrad = collegePGrad;
	}
	public void setPercentagePGrad(int percentagePGrad) {
		this.percentagePGrad = percentagePGrad;
	}
	public void setCollegeGrad(String collegeGrad) {
		this.collegeGrad = collegeGrad;
	}
	public void setPercentageGrad(int percentageGrad) {
		this.percentageGrad = percentageGrad;
	}
	public void setBoard12th(String board12th) {
		this.board12th = board12th;
	}
	public void setPercentage12th(int percentage12th) {
		this.percentage12th = percentage12th;
	}
	public void setBoard10th(String board10th) {
		this.board10th = board10th;
	}
	public void setPercentage10th(int percentage10th) {
		this.percentage10th = percentage10th;
	}
	public void setStream(String stream) {
		this.stream = stream;
	}
	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Student(int studentID) {
		super();
		this.studentID = studentID;
	}
	public Student() {
		super();
	}
	@Override
	public String toString() {
		return "Student [studentID=" + studentID +  ", parentName=" + parentName
				+ ", parentOccupation=" + parentOccupation + ", collegePGrad="
				+ collegePGrad + ", percentagePGrad=" + percentagePGrad + ", collegeGrad=" + collegeGrad
				+ ", percentageGrad=" + percentageGrad + ", board12th=" + board12th + ", percentage12th="
				+ percentage12th + ", board10th=" + board10th + ", percentage10th=" + percentage10th + ", stream="
				+ stream + ", achievement=" + achievement + ", user=" + user + "]";
	}
	
	
	

		
}
