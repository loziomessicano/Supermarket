package it.dstech.service;

import java.util.List;

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

	@Override
	public CartaDiCredito findById(int id) {
		return cartaRepo.findById(id);
	}

	@Override
	public List<CartaDiCredito> findByUserId(int id) {
		
		return (List<CartaDiCredito>) cartaRepo.findByUserC_Id(id);
	}

}
