package it.dstech.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dstech.model.CartaDiCredito;
import it.dstech.model.Categoria;
import it.dstech.model.Ordine;
import it.dstech.model.Prodotto;
import it.dstech.model.User;
import it.dstech.service.CartaDiCreditoService;
import it.dstech.service.ProdottoService;
import it.dstech.service.UserService;






@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProdottoService prodottoService;
	
	@Autowired
	private CartaDiCreditoService cartaDiCreditoService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<Prodotto>> getAll() {
		try {
			List<Prodotto> listaProdotti = prodottoService.findAll();
			logger.info(listaProdotti.toString());
			return new ResponseEntity<List<Prodotto>>(listaProdotti, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<List<Prodotto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/addprodotto/{prodottoid}/{carta}")
	public ResponseEntity<User> addProdotto(@PathVariable("prodottoid") int id,@PathVariable("carta") int idCarta) {
		try {
			CartaDiCredito card = cartaDiCreditoService.findById(idCarta);
			Prodotto prodotto = prodottoService.findById(id);
			Ordine ordine=new Ordine(); //da rivedere
			LocalDate dNow = LocalDate.now();
		    logger.info("anno" + dNow);
		    //-----
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
		    String date = card.getScadenza().toString();
		    YearMonth scadenzaMese = YearMonth.parse(date, formatter);
		    LocalDate scadenza = scadenzaMese.atEndOfMonth();
		    //-----
		    logger.info("anno" + scadenza);
		    logger.info("prova" + dNow.isBefore(scadenza));
			if(prodotto.getQuantitaDisponibile()>0 && dNow.isBefore(scadenza) && card.getCredito() >= prodotto.getPrezzoIvato()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findByUsername(auth.getName());	
			ordine.getListaProdotti().add(prodottoService.findById(id));
			//user.getListaProdotti().add(prodottoService.findById(id)); da rivedere comando sopra
			userService.saveUser(user);
			prodotto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile()-1);
			prodottoService.saveOrUpdateProdotto(prodotto);
			//------
			double credito = card.getCredito();
			card.setCredito(credito-prodotto.getPrezzoIvato());
			cartaDiCreditoService.save(card);
			//---------
			return new ResponseEntity<User>(HttpStatus.OK);
			}else {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getByCategoria")
	public ResponseEntity<List<Prodotto>> getByCategoria(@RequestHeader Categoria categoria) {
		try {
			List<Prodotto> listaProdotti = prodottoService.findByCategoria(categoria);
			logger.info(listaProdotti.toString());
			return new ResponseEntity<List<Prodotto>>(listaProdotti, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<List<Prodotto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getByDisponibilita")
	public ResponseEntity<List<Prodotto>> getByDisponibilita() {
		try {
			List<Prodotto> listaProdotti = prodottoService.findAll();
			List<Prodotto> listaProdDisp = new ArrayList<Prodotto>();
			for(Prodotto prodotto  : listaProdotti) {
				if(prodotto.getQuantitaDisponibile() > 0) {
					listaProdDisp.add(prodotto);
				}
			}
			logger.info(listaProdotti.toString());
			return new ResponseEntity<List<Prodotto>>(listaProdDisp, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<List<Prodotto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

}
