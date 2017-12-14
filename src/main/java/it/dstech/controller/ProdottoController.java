package it.dstech.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dstech.model.CartaDiCredito;
import it.dstech.model.Categoria;
import it.dstech.model.Ordine;
import it.dstech.model.Prodotto;
import it.dstech.model.User;
import it.dstech.service.CartaDiCreditoService;
import it.dstech.service.OrdineService;
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
	
	@Autowired
	private OrdineService ordineService;

	private Random random;

	@GetMapping("/getmodel")
	public ResponseEntity<Prodotto> getmodel() {
		Prodotto prod = new Prodotto();
		return new ResponseEntity<Prodotto>(prod, HttpStatus.CREATED);
	}

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

	@PostMapping("/saveOrUpdate")
	public ResponseEntity<Prodotto> saveOrUpdate(@RequestBody Prodotto prodotto) {
		try {
			Prodotto saved = prodottoService.saveOrUpdate(prodotto);
			logger.info(saved + " saved");
			return new ResponseEntity<Prodotto>(saved, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/addprodotto/{idCarta}")
	public ResponseEntity<Prodotto> addProdotto(@RequestBody List<Prodotto> carello,
			@PathVariable("idCarta") int idCarta) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findByUsername(auth.getName());
			CartaDiCredito card = cartaDiCreditoService.findById(idCarta);
			// Prodotto prodotto = prodottoService.findById(id);
			Ordine ordine = new Ordine(); // da rivedere
			
			LocalDate dNow = LocalDate.now();
			logger.info("anno" + dNow);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
			String date = card.getScadenza();
			YearMonth scadenzaMese = YearMonth.parse(date, formatter);
			LocalDate scadenza = scadenzaMese.atEndOfMonth();
			
			if (dNow.isBefore(scadenza)) {
				double costoTot = 0;
				for (Prodotto lista : carello) {
					costoTot = costoTot + lista.getPrezzoIvato();
				}
				if (card.getCredito() >= costoTot) {
					logger.info("anno" + scadenza);
					logger.info("prova" + dNow.isBefore(scadenza));
					for (Prodotto prodottoCarello : carello) {
						if (prodottoCarello.getQuantitaDaAcquistare() <= prodottoCarello.getQuantitaDisponibile()
								&& prodottoCarello.getQuantitaDisponibile() > 0) {
							ordine.getListaProdotti().add(prodottoCarello);
							prodottoCarello.setQuantitaDisponibile(prodottoCarello.getQuantitaDisponibile() - 1);
							prodottoService.saveOrUpdate(prodottoCarello);
							double credito = card.getCredito();
							card.setCredito(credito - prodottoCarello.getPrezzoIvato());
							cartaDiCreditoService.save(card);
						
						} else {
							return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
						}
					}
				    userService.saveUser(user);
				    cartaDiCreditoService.save(card);
					ordine.setUser(user);
					ordine.setNumeroTransazione(random.nextInt(1000) + 9000);
					ordine.setDataAcquisto(LocalDate.now());
					ordineService.save(ordine);

				} else {
					return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
				}

			} else {
				return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<Prodotto>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
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
			for (Prodotto prodotto : listaProdotti) {
				if (prodotto.getQuantitaDisponibile() > 0) {
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

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Prodotto> delete(@PathVariable("id") int id) {
		try {
			prodottoService.delete(id);
			logger.info(id + " deleted");
			return new ResponseEntity<Prodotto>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
