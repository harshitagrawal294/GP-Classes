package com.harshit.dbms.model;

public class TeachesBatch {
	
	private Faculty faculty;
	private Batch batch;
	private Course course;
	
	public Faculty getFaculty() {
		return faculty;
	}
	public Batch getBatch() {
		return batch;
	}
	public Course getCourse() {
		return course;
	}
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	@Override
	public String toString() {
		return "FacultyTeachesBatch [faculty=" + faculty + ", batch=" + batch + ", course=" + course + "]";
	}
	
}
