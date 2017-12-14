package it.dstech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.model.Ordine;
import it.dstech.repository.OrdineRepository;

@Service
public class OrdineServiceImpl implements OrdineService {

	@Autowired
	OrdineRepository repo;
	
	@Override
	public Ordine save(Ordine ordine) {
		return repo.save(ordine);
	}

}
