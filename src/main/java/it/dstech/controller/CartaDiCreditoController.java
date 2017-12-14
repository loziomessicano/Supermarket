package it.dstech.controller;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
			
			byte[] encodedBytes = Base64.getEncoder().encode(carta.getNumero().getBytes());
			logger.info("encodedBytes " + new String(encodedBytes));
			String encoded = new String(encodedBytes);
			carta.setNumero(encoded);
			
			
			carta.setUserC(user);
			CartaDiCredito saved =cartaService.save(carta);
			logger.info(saved + " saved");

			return new ResponseEntity<CartaDiCredito>(saved, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<CartaDiCredito>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findByUserId/{id}")
	public ResponseEntity<List<CartaDiCredito>> findByUserId(@PathVariable int id) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findByUsername(auth.getName());
			List<CartaDiCredito> find = (List<CartaDiCredito>) cartaService.findByUserId(id);
			logger.info(find + " founded");
			if(find!=null)
			return new ResponseEntity<List<CartaDiCredito>>(find,HttpStatus.OK);
			else
			return new ResponseEntity<List<CartaDiCredito>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("Errore  " + e);
			return new ResponseEntity<List<CartaDiCredito>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<CartaDiCredito> findById(@PathVariable int id) {
		try {
			CartaDiCredito find = cartaService.findById(id);
			logger.info(find + " founded");
			if(find!=null)
			return new ResponseEntity<CartaDiCredito>(find,HttpStatus.OK);
			else
			return new ResponseEntity<CartaDiCredito>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("Errore  " + e);
			return new ResponseEntity<CartaDiCredito>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
