package it.dstech.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;


@Entity
public class Ordine {
		
	@Id
	@Column(unique=true)
	int numeroTransazione;
	
	LocalDate dataAcquisto;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	User user;
	
	@JsonIgnore
	 @ManyToMany
	  @JoinTable(
	      name="ORDINE_PRODOTTO",
	      joinColumns=@JoinColumn(name="ORD_NUMTR", referencedColumnName="numeroTransazione"),
	      inverseJoinColumns=@JoinColumn(name="PROD_ID", referencedColumnName="ID"))
	List<Prodotto> listaProdotti;

	public Ordine() {

	}

	public int getNumeroTransazione() {
		return numeroTransazione;
	}

	public void setNumeroTransazione(int numeroTransazione) {
		this.numeroTransazione = numeroTransazione;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(LocalDate dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}

	public List<Prodotto> getListaProdotti() {
		return listaProdotti;
	}

	public void setListaProdotti(List<Prodotto> listaProdotti) {
		this.listaProdotti = listaProdotti;
	}

	@Override
	public String toString() {
		return "Ordine [numeroTransazione=" + numeroTransazione + ", dataAcquisto=" + dataAcquisto + ", user=" + user
				+ ", listaProdotti=" + listaProdotti + "]";
	}

	
}
