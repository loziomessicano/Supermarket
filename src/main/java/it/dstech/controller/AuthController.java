package it.dstech.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.dstech.model.RoleUser;
import it.dstech.model.User;
import it.dstech.service.UserService;
import it.dstech.service.auth.AuthService;


@RestController
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	@PostMapping("/login")
	public UserDetails authenticate(@RequestBody User principal) throws Exception {
		return authService.authenticate(principal);
	}

	@PostMapping("/register")
	public User addUser(@RequestBody User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(RoleUser.ROLE_USER);
		return userService.saveUser(user);
	}
	
	@CrossOrigin
	@RequestMapping(value="/logout", method= RequestMethod.OPTIONS)
	public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
	  Authentication auth= SecurityContextHolder.getContext().getAuthentication();
	  if(auth != null) {
		  new SecurityContextLogoutHandler().logout(request, response, auth);
	  }
	}

	@GetMapping("/getusermodel")
	public User getModel() {
		return new User();
	}
	
	@RequestMapping("/delete")
	public void deleteUser(int id) {
		userService.deleteUser(id);
	}

}
