package com.harshit.dbms.model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


public class Test {
	
	int testNumber;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date testDate;
	@DateTimeFormat(pattern = "HH:mm")
	Date testTime;
	@DateTimeFormat(pattern = "HH:mm")
	Date testDuration;
	String questionPaperLink;
	String answerKeyLink;
	int difficulty;
	int maximumMarks;
	TestSeries testSeries;
	public int getTestNumber() {
		return testNumber;
	}
	public Date getTestDate() {
		return testDate;
	}
	public Date getTestTime() {
		return testTime;
	}
	public Date getTestDuration() {
		return testDuration;
	}
	public String getQuestionPaperLink() {
		return questionPaperLink;
	}
	public String getAnswerKeyLink() {
		return answerKeyLink;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setTestNumber(int testNumber) {
		this.testNumber = testNumber;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}
	public void setTestDuration(Date testDuration) {
		this.testDuration = testDuration;
	}
	public void setQuestionPaperLink(String questionPaperLink) {
		this.questionPaperLink = questionPaperLink;
	}
	public void setAnswerKeyLink(String answerKeyLink) {
		this.answerKeyLink = answerKeyLink;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public TestSeries getTestSeries() {
		return testSeries;
	}
	public void setTestSeries(TestSeries testSeries) {
		this.testSeries = testSeries;
	}
	public int getMaximumMarks() {
		return maximumMarks;
	}
	public void setMaximumMarks(int maximumMarks) {
		this.maximumMarks = maximumMarks;
	}
	@Override
	public String toString() {
		return "Test [testNumber=" + testNumber  + ", testDate=" + testDate
				+ ", testTime=" + testTime + ", testDuration=" + testDuration + ", questionPaperLink="
				+ questionPaperLink + ", answerKeyLink=" + answerKeyLink + ", difficulty=" + difficulty + ", testSeries="
				+ testSeries + "]";
	}
	public Test(int testNumber, TestSeries testSeries) {
		super();
		this.testNumber = testNumber;
		this.testSeries = testSeries;
	}
	public Test() {
		super();
	}
	
	
	
	
	
	
}
