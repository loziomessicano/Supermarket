package it.dstech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.dstech.model.Categoria;
import it.dstech.model.Prodotto;

public interface ProdottoRepository extends CrudRepository<Prodotto, Integer>{
	
	List<Prodotto> findByCategoria(Categoria categoria);
	
	List<Prodotto> findByQuantitaDisponibile(Double quantitaDisponibile);
	
	Prodotto findById(int id);
		
	
	

}
