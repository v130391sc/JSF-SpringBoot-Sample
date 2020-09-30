package com.druid.developertest.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;

import com.druid.developertest.constants.UserConstants;
import com.druid.developertest.exceptions.ValidationException;
import com.druid.developertest.model.User;
import com.druid.developertest.service.UserService;

import lombok.Data;

@Data
@Named
@RequestScoped
public class UserController implements Serializable {
	
	private User selectedUser;
	
	private List<User> users;
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void initialize() {
		this.users = this.userService.findAll();
		this.selectedUser = new User();
	}
	
	public void refresh() {
		this.selectedUser = new User();
	}

	public void save() {
		try {
			validateUser();			
			this.userService.save(this.selectedUser);
			this.selectedUser = null;
	        PrimeFaces.current().executeScript("PF('userDialog').hide()");
		} catch (ValidationException e) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("addUserError", facesMessage);
		}
        
	}
	
	public void delete() {
		this.userService.deleteById(this.selectedUser.getId());
		this.users.remove(this.selectedUser);
		this.selectedUser = null;
	}
	
	public void editListener(RowEditEvent event) {
		this.userService.save((User) event.getObject());
	}
	
	private void validateUser() throws ValidationException {
		if(!this.selectedUser.getName().matches(UserConstants.REGEX_ONLY_TEXT)) {
			throw new ValidationException(UserConstants.VALIDATION_NAME_ERROR);
		} else if(!this.selectedUser.getSurname().matches(UserConstants.REGEX_ONLY_TEXT)) {
			throw new ValidationException(UserConstants.VALIDATION_SURNAME_ERROR);
		} else if(calculateUserAge(this.selectedUser.getBirthDate()) < UserConstants.MIN_AGE) {
			throw new ValidationException(UserConstants.VALIDATION_BIRTH_DATE_ERROR);
		} else if(!this.selectedUser.getEmail().matches(UserConstants.REGEX_EMAIL)) {
			throw new ValidationException(UserConstants.VALIDATION_EMAIL_ERROR);
		}
	}
	
	private int calculateUserAge(Date birthDate) {
		DateFormat formatter = new SimpleDateFormat(UserConstants.FORMAT_DATE_YYMMHHH);                           
	    int d1 = Integer.parseInt(formatter.format(birthDate));                            
	    int d2 = Integer.parseInt(formatter.format(new Date()));                          
	    return (d2 - d1) / 10000;
	}
	


}
