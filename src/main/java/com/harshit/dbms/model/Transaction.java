package com.harshit.dbms.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

public class Transaction {
	
	private int transactionID;
	private Date transactionDate;
	private Date transactionTime;
	private float amount;
	private String mode;
	private boolean verified;
	private String token;
	public int getTransactionID() {
		return transactionID;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public Date getTransactionTime() {
		return transactionTime;
	}
	public float getAmount() {
		return amount;
	}
	public String getMode() {
		return mode;
	}
	public boolean isVerified() {
		return verified;
	}
	public String getToken() {
		return token;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", transactionDate=" + transactionDate
				+ ", transactionTime=" + transactionTime + ", amount=" + amount + ", mode=" + mode + ", verified="
				+ verified + ", token=" + token + "]";
	}
	
	
	
	
	
		
}
