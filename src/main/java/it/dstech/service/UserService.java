package it.dstech.service;

import it.dstech.model.User;

public interface UserService {

	User saveUser(User user);

	User findByUsername(String username);
	
	User findById(int id);
	
	void deleteUser(int id);
}
