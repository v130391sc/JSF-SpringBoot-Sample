package com.druid.developertest.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.druid.developertest.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	public List<User> findByBirthDateBetween(Date startDate, Date endDate);
	
}
