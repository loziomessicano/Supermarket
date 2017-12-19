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
	
	private LocalDate dataOfferta;

	

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
	
	

	public LocalDate getDataOfferta() {
		return dataOfferta;
	}

	public void setDataOfferta(LocalDate dataOfferta) {
		this.dataOfferta = dataOfferta;
	}

	@Override
	public String toString() {
		return "Offerte [id=" + id + ", dataOdierna=" + dataOdierna + ", dataOfferta=" + dataOfferta + "]";
	}

	
	
	

}
