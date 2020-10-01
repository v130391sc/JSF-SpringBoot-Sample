package com.druid.developertest.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.druid.developertest.model.User;
import com.druid.developertest.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userDao;
	
	@Autowired
	public UserService(UserRepository userDao) {
		this.userDao = userDao;
	}
	
	/**
	 * Find All users in BBDD
	 * @return
	 */
	public List<User> findAll(){
		return this.userDao.findAll();
	}
	
	/**
	 * Save an user in BBDD
	 * @param user
	 */
	public void save(User user) {
		this.userDao.save(user);
	}
	
	/**
	 * Delete an User
	 * @param user
	 */
	public void delete(User user) {
		this.userDao.delete(user);
	}
	
	/**
	 * Find users filter by a range of their birth dates.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<User> findBeetweenBirthDates(Date startDate, Date endDate) {
		return this.userDao.findByBirthDateBetween(startDate, endDate);
	}
	
	/**
	 * Find an user by his username
	 * @param username
	 * @return
	 */
	public User findByUsername(String username){
		return this.userDao.findByUsername(username);
	}
}
