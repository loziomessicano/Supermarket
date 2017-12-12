package it.dstech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.model.Categoria;
import it.dstech.model.Prodotto;
import it.dstech.repository.ProdottoRepository;

@Service
public class ProdottoServiceImpl implements ProdottoService{
	
	@Autowired
	ProdottoRepository prodottoRepository;

	@Override
	public List<Prodotto> findAll() {
		return (List<Prodotto>) prodottoRepository.findAll();

	}

	@Override
	public Prodotto saveOrUpdate(Prodotto prodotto) {
		return prodottoRepository.save(prodotto);
	}

	@Override
	public List<Prodotto> findByCategoria(Categoria categoria) {
		return prodottoRepository.findByCategoria(categoria);
	}

	@Override
	public List<Prodotto> findByQuantitaDisponibile(Double quantitaDisponibile) {
		return prodottoRepository.findByQuantitaDisponibile(quantitaDisponibile);
	}

	@Override
	public Prodotto findById(int id) {
		return prodottoRepository.findById(id);
	}

	@Override
	public void delete(int id) {
		prodottoRepository.delete(id);
		
	}
	
	

}
