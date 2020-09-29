package com.druid.developertest.repository;

import java.util.Date;
import java.util.List;

import com.druid.developertest.model.User;

public interface UserDao {

	public List<User> findAll();
	public User findByEmail(String email);
	public List<User> findBetweenBirthDates(Date startDate, Date endDate);
	public void save(User user);
	public void remove(Long id);
}
