package com.druid.developertest.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.druid.developertest.model.User;
import com.druid.developertest.repository.UserDaoImpl;

@Service
public class UserService {

	private UserDaoImpl userDao;
	
	@Autowired
	public UserService(UserDaoImpl userDao) {
		this.userDao = userDao;
	}
	
	public List<User> findAll(){
		return this.userDao.findAll();
	}
	
	public void save(User user) {
		this.userDao.save(user);
	}
	
	public void deleteById(Long id) {
		this.userDao.remove(id);
	}
	
	public List<User> findBeetweenBirthDates(Date startDate, Date endDate) {
		return this.userDao.findBetweenBirthDates(startDate, endDate);
	}
	
	public User findByEmail(String email) {
		return this.userDao.findByEmail(email);
	}
}
