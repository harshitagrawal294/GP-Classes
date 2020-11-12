package com.harshit.dbms.model;

import javax.persistence.Entity;
import javax.persistence.Id;

public class Enroll {

	private int enrollmentNumber;
	private int transactionID;
	private int studentID;
	private int batchID;
	private int courseID;
	private int session;
	
	public int getEnrollmentNumber() {
		return enrollmentNumber;
	}
	public int getTransactionID() {
		return transactionID;
	}
	public int getStudentID() {
		return studentID;
	}
	public int getBatchID() {
		return batchID;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setEnrollmentNumber(int enrollmentNumber) {
		this.enrollmentNumber = enrollmentNumber;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public int getSession() {
		return session;
	}
	public void setSession(int session) {
		this.session = session;
	}

	@Override
	public String toString() {
		return "Enroll [enrollmentNumber=" + enrollmentNumber + ", transactionID=" + transactionID + ", studentID="
				+ studentID + ", batchID=" + batchID + ", courseID=" + courseID + ", session=" + session + "]";
	}
	
	
	
	
	
	

}
