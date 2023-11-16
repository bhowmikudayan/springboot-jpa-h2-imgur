package com.imgur.demo.project.spring.h2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

	private long id;
	private String firstName;
	private String lastName;
	private String emailId;
	private String userName;
	private String password;

	public String getImageurl() {
		return imageurl;
	}

	@Column(name = "image_url", nullable = true)
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	private String imageurl;

	@Column(name = "user_name", nullable = false)
	public String getUserName() {return userName;}
	public void setUserName(String userName) {this.userName = userName;	}


	@Column(name = "password", nullable = false)
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}


	
	public Employee() {
		
	}
	
	public Employee(String firstName, String lastName, String emailId, String userName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.userName = userName;
		this.password = password;

	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {

		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "first_name", nullable = false)
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}
	
	@Column(name = "last_name", nullable = false)
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName) {

		this.lastName = lastName;
	}
	
	@Column(name = "email_address", nullable = false)
	public String getEmailId() {

		return emailId;
	}
	public void setEmailId(String emailId) {

		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", userName="+userName + ", password="+password+"]";
	}
	
}
