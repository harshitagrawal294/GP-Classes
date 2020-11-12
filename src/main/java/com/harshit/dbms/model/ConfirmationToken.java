//package com.harshit.dbms.model;
//
//import java.util.Date;
//import java.util.UUID;
//
//public class ConfirmationToken {
//
//    private int tokenid;
//    private String confirmationToken;
//    private Date createdDate;
//    private FacultyApplicant facultyApplicant;
//
//    public ConfirmationToken(FacultyApplicant facultyApplicant) {
//        this.facultyApplicant = facultyApplicant;
//        createdDate = new Date();
//        confirmationToken = UUID.randomUUID().toString();
//    }
//    
//    public ConfirmationToken() {
//    }
//
//	public int getTokenid() {
//		return tokenid;
//	}
//
//	public String getConfirmationToken() {
//		return confirmationToken;
//	}
//
//	public Date getCreatedDate() {
//		return createdDate;
//	}
//
//	public FacultyApplicant getFacultyApplicant() {
//		return facultyApplicant;
//	}
//
//	public void setTokenid(int tokenid) {
//		this.tokenid = tokenid;
//	}
//
//	public void setConfirmationToken(String confirmationToken) {
//		this.confirmationToken = confirmationToken;
//	}
//
//	public void setCreatedDate(Date createdDate) {
//		this.createdDate = createdDate;
//	}
//
//	public void setFacultyApplicant(FacultyApplicant facultyApplicant) {
//		this.facultyApplicant = facultyApplicant;
//	}
//	
//
//    // getters and setters
//    
//    
//}
