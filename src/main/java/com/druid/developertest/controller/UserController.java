package com.druid.developertest.controller;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.security.access.prepost.PreAuthorize;

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
	
	private Date startDate;
	
	private Date endDate;
	
	/**
	 * Initialize the ManageBean
	 */
	@PostConstruct
	public void initialize() {
		fetchAll();
		this.selectedUser = new User();
	}
	
	/**
	 * Updates the selected user
	 */
	public void refresh() {
		this.selectedUser = new User();
	}
	
	/**
	 * Updates the user list in view
	 */
	public void fetchAll() {
		List<User> usersFound = this.userService.findAll();
		this.users = usersFound.isEmpty() ? new ArrayList<>() : usersFound;
	}

	/**
	 * Save new user
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void save() {
		try {
			this.selectedUser.validate();			
			this.userService.save(this.selectedUser);
			this.users.add(this.selectedUser);
			this.selectedUser = null;
	        PrimeFaces.current().executeScript("PF('userDialog').hide()");
		} catch (ValidationException e) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("addUserError", facesMessage);
		}
        
	}
	
	
	/**
	 * Deletes an user
	 */
	@PreAuthorize("hasRole('ROLE_VIEWER')")
	public void delete() {
		this.userService.delete(this.selectedUser);
		this.users.remove(this.selectedUser);
		this.selectedUser = null;
	}
	
	/**
	 * Edit an user
	 * @param event
	 */
	public void editListener(RowEditEvent event) {
		this.userService.save((User) event.getObject());
	}
	
	/**
	 * Filter to search users by their birth dates
	 */
	public void filterByRangeDates() {
		if(this.startDate != null && this.endDate != null) {
			List<User> usersFound = this.userService.findBeetweenBirthDates(this.startDate, this.endDate);
			this.users = usersFound.isEmpty() ? new ArrayList<>() : usersFound;
			this.startDate = null;
			this.endDate = null;			
		}
    }
	
	/**
	 * Logout
	 * @throws IOException
	 */
	public void logout() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/logout");
	}

}
