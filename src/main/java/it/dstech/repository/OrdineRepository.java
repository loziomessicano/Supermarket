package it.dstech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.dstech.model.Ordine;

public interface OrdineRepository extends CrudRepository<Ordine,Integer> {
	
	List<Ordine> findByUserId(int id);

}
