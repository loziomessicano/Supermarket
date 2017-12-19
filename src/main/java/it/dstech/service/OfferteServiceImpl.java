package it.dstech.service;

import java.time.LocalDate;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;

import it.dstech.model.Offerte;
import it.dstech.repository.OfferteRepository;

@Entity
public class OfferteServiceImpl implements OfferteService {
	
	@Autowired
	OfferteRepository offerteRepo;

	@Override
	public Offerte save(LocalDate dataOdierna) {
		
		return offerteRepo.save(dataOdierna);
	}

}
