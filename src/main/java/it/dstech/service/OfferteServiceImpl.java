package it.dstech.service;

import java.time.LocalDate;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.model.Offerte;
import it.dstech.repository.OfferteRepository;

@Service
public class OfferteServiceImpl implements OfferteService {
	
	@Autowired
	OfferteRepository offerteRepo;

	@Override
	public Offerte save(LocalDate dataOdierna) {
		
		return offerteRepo.save(dataOdierna);
	}

}
