package it.dstech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dstech.model.CartaDiCredito;
import it.dstech.model.User;
import it.dstech.service.CartaDiCreditoService;
import it.dstech.service.UserService;

@RestController
@RequestMapping("/cartadicredito")
public class CartaDiCreditoController {
	
	@Autowired
	CartaDiCreditoService cartaService;
	
	@Autowired
	UserService userService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/getmodel")
	public ResponseEntity<CartaDiCredito> getmodel() {
		CartaDiCredito carta = new CartaDiCredito();
		return new ResponseEntity<CartaDiCredito>(carta, HttpStatus.CREATED);
	}
	
	@PostMapping("/save")
	public ResponseEntity<CartaDiCredito> save(@RequestBody CartaDiCredito carta) {
		try {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findByUsername(auth.getName());			
			CartaDiCredito saved = cartaService.save(carta);
			user.getCartaCredito().add(saved);
			userService.saveUser(user);
			logger.info(saved + " saved");
			return new ResponseEntity<CartaDiCredito>(saved, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<CartaDiCredito>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
