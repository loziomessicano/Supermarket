package it.dstech.service;

import java.util.List;

import it.dstech.model.Ordine;

public interface OrdineService {

	Ordine save(Ordine ordine);

    List<Ordine> findAll(int id);
}
