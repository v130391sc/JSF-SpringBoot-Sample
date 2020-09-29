package com.druid.developertest.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.druid.developertest.model.User;

@Component
public class UserDaoImpl implements UserDao {

	private List<User> users;

	public UserDaoImpl() {
		this.users = new ArrayList<>();
	}

	public List<User> findAll() {
		return users;
	}

	public User findByEmail(String email) {
		return users.stream()
				.filter(user -> user.getEmail().equals(email))
				.findAny()
				.orElse(null);
	}
	
	public List<User> findBetweenBirthDates(Date startDate, Date endDate){
		return users.stream()
				.filter(user -> user.getBirthDate().after(startDate)&&user.getBirthDate().before(endDate))
				.collect(Collectors.toList());
	}

	public void save(User user) {
		if (users.contains(user)) {
			users.forEach(userLoop -> {
				if (userLoop.getId().equals(user.getId())) {
					userLoop.setName(user.getName());
					userLoop.setSurname(user.getSurname());
					userLoop.setEmail(user.getEmail());
					userLoop.setBirthDate(user.getBirthDate());
				}
			});
		} else {
			Long lastIdFound = users.stream()
					.reduce((first, second) -> second).map(User::getId)
					.orElse(Long.valueOf(0));
			user.setId(++lastIdFound);
			users.add(user);
		}
	}
	
	public void remove(Long id) {
		users.removeIf(user -> user.getId().equals(id));
	}
	
	
}
