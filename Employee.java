package com.example.demo.Spring.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	private String name;
	private String email;
	private int mobile;
	private float salary;
	private String department;
	
	public Employee(Long Id,String name, String email, int mobile, float salary, String department) {
		this.Id = Id;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.salary = salary;
		this.department = department;
	}
	public Long getResourceId() {
		return Id;
		
	}
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public int getMobile() {
		return mobile;
	}

	public float getSalary() {
		return salary;
	}

	public String getDepartment() {
		return department;
	}
	
	

}

