package it.dstech.repository;

import org.springframework.data.repository.CrudRepository;

import it.dstech.model.CartaDiCredito;

public interface CartaDiCreditoRepository extends CrudRepository <CartaDiCredito,Integer> {
	
	CartaDiCredito findById(int id);

}
