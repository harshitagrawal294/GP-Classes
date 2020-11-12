package com.harshit.dbms.model;

import java.util.Date;

import javax.validation.constraints.Size;

public class FacultyApplicant {
	
	private int applicationID;
	private String applicantName;
	private String resumeLink;
	private long applicantPhoneNumber;
	private String applicantEmailID;
	private Date applicationDate;
	private boolean applicationProcessed;
	private boolean applicationReviewStatus;
	private Subject subject;
	private String confirmationToken;
	
	
	public int getApplicationID() {
		return applicationID;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public String getResumeLink() {
		return resumeLink;
	}
	public long getApplicantPhoneNumber() {
		return applicantPhoneNumber;
	}
	public String getApplicantEmailID() {
		return applicantEmailID;
	}
	public boolean isApplicationProcessed() {
		return applicationProcessed;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setApplicationID(int applicationID) {
		this.applicationID = applicationID;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public void setResumeLink(String resumeLink) {
		this.resumeLink = resumeLink;
	}
	public void setApplicantPhoneNumber(long applicantPhoneNumber) {
		this.applicantPhoneNumber = applicantPhoneNumber;
	}
	public void setApplicantEmailID(String applicantEmailID) {
		this.applicantEmailID = applicantEmailID;
	}
	public void setApplicationProcessed(boolean applicationProcessed) {
		this.applicationProcessed = applicationProcessed;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getConfirmationToken() {
		return confirmationToken;
	}
	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	public boolean isApplicationReviewStatus() {
		return applicationReviewStatus;
	}
	public void setApplicationReviewStatus(boolean applicationReviewStatus) {
		this.applicationReviewStatus = applicationReviewStatus;
	}
	@Override
	public String toString() {
		return "FacultyApplicant [applicationID=" + applicationID + ", applicantName=" + applicantName + ", resumeLink="
				+ resumeLink + ", applicantPhoneNumber=" + applicantPhoneNumber + ", applicantEmailID="
				+ applicantEmailID + ", applicationDate=" + applicationDate + ", applicationProcessed="
				+ applicationProcessed + ", applicationReviewStatus=" + applicationReviewStatus + ", subject=" + subject
				+ ", confirmationToken=" + confirmationToken + "]";
	}
	
	
	
	
	
}
