package it.dstech.service;

import java.time.LocalDate;


import it.dstech.model.Offerte;

public interface OfferteService {
	
	Offerte save(LocalDate dataOdierna);


}
