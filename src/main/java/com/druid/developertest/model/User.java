package com.druid.developertest.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	
	private Long id;

	private String name;
	
	private String surname;
	
	private Date birthDate;
	
	private String email;
	
}
