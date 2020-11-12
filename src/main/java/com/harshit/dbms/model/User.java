package com.harshit.dbms.model;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.harshit.dbms.validator.UniqueUsername;

public class User {

	@Size(min=5, max=255, message = "Username must be between {min} and {max} characters long")	
	@UniqueUsername(message = "This username is not available")
	private String username;
	@Size(min=5, max=255, message = "Password must be between {min} and {max} characters long")	
	private String password;
	private String oldPassword;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
	private boolean active;
	private String gender;
	private long adhaarNumber;
	@Email(message="Please provide a valid email address")
	private String emailID;
	private String firstName;
	private String lastName;
	private String middleName;
	private String street;
	private String city;
	private String state;
	private String country;
	private String role;
	private String token;
	private long phone;
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public boolean isActive() {
		return active;
	}
	public String getGender() {
		return gender;
	}
	public long getAdhaarNumber() {
		return adhaarNumber;
	}
	public String getEmailID() {
		return emailID;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getCountry() {
		return country;
	}
	public String getRole() {
		return role;
	}	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setAdhaarNumber(long adhaarNumber) {
		this.adhaarNumber = adhaarNumber;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", oldPassword=" + oldPassword + ", birthDate="
				+ birthDate + ", active=" + active + ", gender=" + gender + ", adhaarNumber=" + adhaarNumber
				+ ", emailID=" + emailID + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName="
				+ middleName + ", street=" + street + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", role=" + role + ", token=" + token + ", phone=" + phone +"]";
	}
	
	
	
}
