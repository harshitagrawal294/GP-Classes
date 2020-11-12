package com.harshit.dbms.model;


public class ContactUsForm {

	int queryID;
	long phoneNumber;
	String emailID;
	String query;
	String name;
	String replyGiven;
	boolean showFAQ;
	@Override
	public String toString() {
		return "ContactUsForm [queryID=" + queryID + ", phoneNumber=" + phoneNumber + ", emailID=" + emailID
				+ ", query=" + query + ", name=" + name + ", replyGiven=" + replyGiven + ", show=" + showFAQ + "]";
	}
	public int getQueryID() {
		return queryID;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public String getEmailID() {
		return emailID;
	}
	public String getQuery() {
		return query;
	}
	public String getName() {
		return name;
	}
	public String getReplyGiven() {
		return replyGiven;
	}
	public boolean isShowFAQ() {
		return showFAQ;
	}
	public void setQueryID(int queryID) {
		this.queryID = queryID;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setReplyGiven(String replyGiven) {
		this.replyGiven = replyGiven;
	}
	public void setShowFAQ(boolean showFAQ) {
		this.showFAQ = showFAQ;
	}
	
	
	
}
