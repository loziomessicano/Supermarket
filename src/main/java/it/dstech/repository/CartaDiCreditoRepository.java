package it.dstech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.dstech.model.CartaDiCredito;

public interface CartaDiCreditoRepository extends CrudRepository <CartaDiCredito,Integer> {
	
	CartaDiCredito findById(int id);
	
    List<CartaDiCredito> findByUserC_Id(int id);
}
