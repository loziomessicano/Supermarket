package it.dstech.service;

import java.util.List;

import it.dstech.model.Categoria;
import it.dstech.model.Prodotto;

public interface ProdottoService {
	
	List<Prodotto> findAll();
	
	Prodotto saveOrUpdate(Prodotto prodotto);
	
	Prodotto findById(int id);
	
	void delete(int id);
	
	public List<Prodotto> findByCategoria(Categoria categoria);
	
	public List<Prodotto> findByQuantitaDisponibile(Double quantitaDisponibile);
	
}
