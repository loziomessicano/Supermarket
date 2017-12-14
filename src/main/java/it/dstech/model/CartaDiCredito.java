package it.dstech.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CartaDiCredito {

	@GeneratedValue
	@Id
	private int id;

	private String numero;

	private String scadenza;

	private String ccv;

	private double credito;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private User userC;

	public CartaDiCredito() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getScadenza() {
		return scadenza;
	}

	public void setScadenza(String scadenza) {
		this.scadenza = scadenza;
	}

	public String getCcv() {
		return ccv;
	}

	public void setCcv(String ccv) {
		this.ccv = ccv;
	}

	public double getCredito() {
		return credito;
	}

	public void setCredito(double credito) {
		this.credito = credito;
	}

	public User getUserC() {
		return userC;
	}

	public void setUserC(User userC) {
		this.userC = userC;
	}

	@Override
	public String toString() {
		return "CartaDiCredito [id=" + id + ", numero=" + numero + ", scadenza=" + scadenza + ", ccv=" + ccv
				+ ", credito=" + credito + ", userC=" + userC + "]";
	}

	
}
