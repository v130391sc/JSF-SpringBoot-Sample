package com.druid.developertest.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.druid.developertest.constants.UserConstants;
import com.druid.developertest.exceptions.ValidationException;

import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	private String password;

	private String name;

	private String surname;

	private Date birthDate;

	private String email;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "authority_id") })
	private Set<Authority> authorities = new HashSet<>();

	/**
	 * Calculating user's age
	 * @param birthDate
	 * @return
	 */
	public int calculateUserAge() {
		DateFormat formatter = new SimpleDateFormat(UserConstants.FORMAT_DATE_YYMMHHH);                           
	    int d1 = Integer.parseInt(formatter.format(this.birthDate));                            
	    int d2 = Integer.parseInt(formatter.format(new Date()));                          
	    return (d2 - d1) / 10000;
	}
	
	/**
	 * Validate input parameters from user
	 * @throws ValidationException
	 */
	public void validate() throws ValidationException {
		if(!this.name.matches(UserConstants.REGEX_ONLY_TEXT)) {
			throw new ValidationException(UserConstants.VALIDATION_NAME_ERROR);
		} else if(!this.surname.matches(UserConstants.REGEX_ONLY_TEXT)) {
			throw new ValidationException(UserConstants.VALIDATION_SURNAME_ERROR);
		} else if(calculateUserAge() < UserConstants.MIN_AGE) {
			throw new ValidationException(UserConstants.VALIDATION_BIRTH_DATE_ERROR);
		} else if(!this.email.matches(UserConstants.REGEX_EMAIL)) {
			throw new ValidationException(UserConstants.VALIDATION_EMAIL_ERROR);
		}
	}
}
