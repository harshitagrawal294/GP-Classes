package com.harshit.dbms.model;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class Subject {
	
	private int subjectID;
	private String subjectName;
	private int subjectIntroducedIn;
	private Faculty head;
	
	public int getSubjectID() {
		return subjectID;
	}
	public String getSubjectName() {
		return subjectName;
	}

	public int getSubjectIntroducedIn() {
		return subjectIntroducedIn;
	}
	public Faculty getHead() {
		return head;
	}
	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public void setSubjectIntroducedIn(int subjectIntroducedIn) {
		this.subjectIntroducedIn = subjectIntroducedIn;
	}
	public void setHead(Faculty head) {
		this.head = head;
	}
	@Override
	public String toString() {
		return "Subject [subjectID=" + subjectID + ", subjectName=" + subjectName + ", introducedInSession=" + subjectIntroducedIn
				+ ", head=" + head + "]";
	}
	public Subject(int subjectID, String subjectName, int subjectIntroducedIn, Faculty head) {
		super();
		this.subjectID = subjectID;
		this.subjectName = subjectName;
		this.subjectIntroducedIn = subjectIntroducedIn;
		this.head = head;
	}
	public Subject() {
		super();
	}
		
}
