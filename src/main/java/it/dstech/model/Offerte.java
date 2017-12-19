package it.dstech.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Offerte {
	
	@GeneratedValue
	@Id
	private int id;
	
	private LocalDate dataOdierna;

	

	public Offerte() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDataOdierna() {
		return dataOdierna;
	}

	public void setDataOdierna(LocalDate dataOdierna) {
		this.dataOdierna = dataOdierna;
	}

	@Override
	public String toString() {
		return "Offerte [id=" + id + ", dataOdierna=" + dataOdierna + "]";
	}
	
	

}
