package it.dstech.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import it.dstech.model.Offerte;

public interface OfferteRepository extends CrudRepository <Offerte,Integer> {

	Offerte save(LocalDate dataOdierna);

}
