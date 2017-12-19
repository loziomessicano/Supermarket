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
import it.dstech.model.Unita;
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

	private Random random=new Random();

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
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Prodotto> getProdById(@PathVariable("id") int id) {
		
		try {
			logger.info("found");
		    prodottoService.findById(id);
		    return new ResponseEntity<Prodotto>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error : " + e);
			return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
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
			
			LocalDate dNow = LocalDate.now();
			logger.info("anno" + dNow);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
			String date = card.getScadenza();
			YearMonth scadenzaMese = YearMonth.parse(date, formatter);
			LocalDate scadenza = scadenzaMese.atEndOfMonth();
			Ordine ordine = new Ordine(); //da controllare
			
			if (dNow.isBefore(scadenza)) {
				double costoTot = 0;
				for (Prodotto lista : carello) {
					costoTot = costoTot + lista.getPrezzoIvato();
				}
				 logger.info("Costo totale :"+costoTot);
				if (card.getCredito() >= costoTot) {
					logger.info("anno" + scadenza);
					logger.info("prova" + dNow.isBefore(scadenza));
					
					for (Prodotto prodottoCarello : carello) {
						logger.info(prodottoCarello+"");
						if (prodottoCarello.getQuantitaDaAcquistare() <= prodottoCarello.getQuantitaDisponibile()
								&& prodottoCarello.getQuantitaDisponibile() > 0) {
							logger.info("sono nell'if");
							
							ordine.getListaProdotti().add(prodottoCarello);
						    logger.info("articolo aggiunto"+ordine.getListaProdotti());
						    
						    prodottoService.findById(prodottoCarello.getId()).setQuantitaDisponibile(prodottoCarello.getQuantitaDisponibile()-1);
							prodottoService.saveOrUpdate(prodottoService.findById(prodottoCarello.getId()));
							double credito = card.getCredito();
							card.setCredito(credito - prodottoCarello.getPrezzoIvato());
						
						} else {
							return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
						}
					}
					logger.info("sono fuori dal for");
				    //userService.saveUser(user);
				   
				    cartaDiCreditoService.save(card);
				    logger.info("carta saved");
					ordine.setUser(user);
				    logger.info("setUser saved");
				    int numero=random.nextInt(10000);
				    logger.info("numero random: "+numero);
					ordine.setNumeroTransazione(numero);
					logger.info("setNumeroTransazione saved");
					ordine.setDataAcquisto(LocalDate.now());
				    logger.info("setDataAcquisto saved");
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
	
	@PostMapping("/popolaDB")
	public ResponseEntity<Prodotto> saveOrUpdate(){
		try {
			logger.info("popolo il db");
			Prodotto prod1 = new Prodotto("Rigatoni","Barilla","10/02/2018",Categoria.ALIMENTI,300.0,0.0,Unita.PEZZO,5.0,3.90,5.0,"../../assets/imgs/barilla_rigatoni.jpeg",0);
			prodottoService.saveOrUpdate(prod1);
			Prodotto prod2 = new Prodotto("Pesto","Buitoni","30/12/2017",Categoria.ALIMENTI,300.0,0.0,Unita.PEZZO,5.0,3.90,5.0,"../../assets/imgs/buitoni_pesto.jpeg",0);
			prodottoService.saveOrUpdate(prod2);
			Prodotto prod3 = new Prodotto("Salsiccia","Negroni","15/06/2028",Categoria.ALIMENTI,300.0,0.0,Unita.PEZZO,5.0,3.90,5.0,"../../assets/imgs/negroni_salsiccia.jpeg",0);
			prodottoService.saveOrUpdate(prod3);
			Prodotto prod4 = new Prodotto("Banane","Chichita","10/02/2018",Categoria.ALIMENTI,300.0,0.0,Unita.ETTO,5.0,3.90,5.0,"../../assets/imgs/chichita_banane.jpeg",0);
			prodottoService.saveOrUpdate(prod4);
			Prodotto prod5 = new Prodotto("TV LCD","Samsung","",Categoria.PROD_CASA,300.0,0.0,Unita.PEZZO,5.0,3.90,5.0,"../../assets/imgs/samsung_tv.jpeg",0);
			prodottoService.saveOrUpdate(prod5);
			Prodotto prod6 = new Prodotto("Armadio","Foppapedretti","",Categoria.PROD_CASA,300.0,0.0,Unita.PEZZO,5.0,3.90,5.0,"../../assets/imgs/foppapedretti_armadio.jpeg",0);
			prodottoService.saveOrUpdate(prod6);
			Prodotto prod7 = new Prodotto("Videocamera","GO_PRO","",Categoria.PROD_CASA,300.0,0.0,Unita.PEZZO,5.0,3.90,5.0,"../../assets/imgs/gopro_videocamera.jpeg",0);
			prodottoService.saveOrUpdate(prod7);
			Prodotto prod8 = new Prodotto("Shampoo","Garnier","20/08/2020",Categoria.PROD_PERSONA,300.0,0.0,Unita.LITRO,5.0,3.90,5.0,"../../assets/imgs/garnier_shampoo.jpeg",0);
			prodottoService.saveOrUpdate(prod8);
			Prodotto prod9 = new Prodotto("Deodorante","DOVE","16/01/2021",Categoria.PROD_PERSONA,300.0,0.0,Unita.PEZZO,5.0,3.90,5.0,"../../assets/imgs/dove_deodorante.jpeg",0);
			prodottoService.saveOrUpdate(prod9);
			Prodotto prod10 = new Prodotto("Profilattici","Durex","29/09/2022",Categoria.PROD_PERSONA,300.0,0.0,Unita.CHILO,5.0,3.90,5.0,"../../assets/imgs/durex_profilattici.jpeg",0);
			prodottoService.saveOrUpdate(prod10);
			Prodotto prod11 = new Prodotto("Cane","Labrador","",Categoria.ANIMALI,300.0,0.0,Unita.CHILO,5.0,3.90,5.0,"../../assets/imgs/labrador_cane.jpeg",0);
			prodottoService.saveOrUpdate(prod11);
			Prodotto prod12 = new Prodotto("Cervo","","",Categoria.ANIMALI,300.0,0.0,Unita.CHILO,5.0,3.90,5.0,"../../assets/imgs/cervo.jpeg",0);
			prodottoService.saveOrUpdate(prod12);
			Prodotto prod13 = new Prodotto("Elefante","","",Categoria.ANIMALI,300.0,0.0,Unita.CHILO,5.0,3.90,5.0,"../../assets/imgs/elefante.jpeg",0);
			prodottoService.saveOrUpdate(prod13);
			logger.info("db popolato");
			return new ResponseEntity<Prodotto>(HttpStatus.CREATED);
		}catch (Exception e) {
			logger.error("Errore " + e);
			return new ResponseEntity<Prodotto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	public void getOfferte() {
		LocalDate dataOggi = LocalDate.now();
		logger.info(dataOggi + " data");
			List<Prodotto> listaProdotti = prodottoService.findAll();
			for (Prodotto prodotto : listaProdotti) {
				prodottoService.findById(prodotto.getId()).setOfferta(0);
				prodottoService.findById(prodotto.getId()).getPrezzoScontato();
				prodottoService.saveOrUpdate(prodottoService.findById(prodotto.getId()));
			}
	
		
		
	}
	
	
	
	

}
