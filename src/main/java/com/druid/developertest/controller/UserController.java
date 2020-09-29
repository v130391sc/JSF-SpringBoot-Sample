package com.druid.developertest.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;

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
		this.userService.save(this.selectedUser);
		this.selectedUser = null;
	}
	
	public void delete() {
		this.userService.deleteById(this.selectedUser.getId());
		this.users.remove(this.selectedUser);
		this.selectedUser = null;
	}
	
	public void editListener(RowEditEvent event) {
		this.userService.save((User) event.getObject());
	}
	


}
