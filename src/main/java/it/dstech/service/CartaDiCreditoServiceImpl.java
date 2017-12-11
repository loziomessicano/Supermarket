package it.dstech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.model.CartaDiCredito;
import it.dstech.repository.CartaDiCreditoRepository;

@Service
public class CartaDiCreditoServiceImpl implements CartaDiCreditoService {
	
	@Autowired
	CartaDiCreditoRepository cartaRepo; 

	@Override
	public CartaDiCredito save(CartaDiCredito carta) {
		
		return cartaRepo.save(carta);
	}

}
