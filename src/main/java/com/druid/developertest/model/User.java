package com.druid.developertest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	private String surname;
	
	private Date birthDate;
	
	private String email;
	
}
