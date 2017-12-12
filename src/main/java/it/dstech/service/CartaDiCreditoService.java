package it.dstech.service;

import java.util.List;

import it.dstech.model.CartaDiCredito;

public interface CartaDiCreditoService {
	
	CartaDiCredito save(CartaDiCredito carta);
	
	CartaDiCredito findById(int id);
	
	List<CartaDiCredito> findByUserId(int id);

}
