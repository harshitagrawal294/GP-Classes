package com.harshit.dbms.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class Batch {

	private int batchID;
	private Course course;
	private int session;

	private float fees;
	private int roomNo;
	private int capacity;
	private List<Faculty> faculties; 
	
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getBatchID() {
		return batchID;
	}
	public Course getCourse() {
		return course;
	}
	public int getSession() {
		return session;
	}

	public float getFees() {
		return fees;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public void setSession(int session) {
		this.session = session;
	}

	public void setFees(float fees) {
		this.fees = fees;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	
	public List<Faculty> getFaculties() {
		return faculties;
	}
	public void setFaculties(List<Faculty> faculties) {
		this.faculties = faculties;
	}
	@Override
	public String toString() {
		return "Batch [batchID=" + batchID + ", course=" + course + ", session=" + session 
				 + ", fees=" + fees + ", roomNo=" + roomNo + ", capacity=" + capacity + "]";
	}

	
	
	
	
}
