package it.dstech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.model.User;
import it.dstech.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repo;
	
	@Override
	public User saveUser(User user) {
		return repo.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public User findById(int id) {
		return repo.findById(id);
	}

	@Override
	public void deleteUser(int id) {
        repo.delete(id);		
	}

}
