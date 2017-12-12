package it.dstech.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CartaDiCredito {

	@GeneratedValue
	@Id
	private int id;

	private String numero;

	private LocalDate scadenza;

	private String ccv;

	private double credito;

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

	public LocalDate getScadenza() {
		return scadenza;
	}

	public void setScadenza(LocalDate scadenza) {
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
