package it.dstech.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Prodotto {

	@Id
	@GeneratedValue
	private int id;
	private String nome;
	private String marca;
	private String dataScadenza;
	private Categoria categoria;
	private Double quantitaDisponibile;
	private Double quantitaDaAcquistare;
	private Unita unita;
	private Double prezzoUnitario;
	private Double prezzoSenzaIva;
	private Double prezzoIvato;
	private String img;
	private int offerta;
	private Double prezzoScontato;
	
	@ManyToMany(mappedBy="listaProdotti")
	private List<Ordine> listaOrdini;

	public Prodotto() {

	}
	

	public Prodotto(String nome, String marca, String dataScadenza, Categoria categoria, Double quantitaDisponibile,
			Double quantitaDaAcquistare, Unita unita, Double prezzoUnitario, Double prezzoSenzaIva, Double prezzoIvato,
			String img, int offerta) {
		this.nome = nome;
		this.marca = marca;
		this.dataScadenza = dataScadenza;
		this.categoria = categoria;
		this.quantitaDisponibile = quantitaDisponibile;
		this.quantitaDaAcquistare = quantitaDaAcquistare;
		this.unita = unita;
		this.prezzoUnitario = prezzoUnitario;
		this.prezzoSenzaIva = prezzoSenzaIva;
		this.prezzoIvato = prezzoIvato;
		this.img = img;
		this.offerta = offerta;
		this.prezzoScontato=prezzoIvato-prezzoIvato*offerta/100;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Double getQuantitaDisponibile() {
		return quantitaDisponibile;
	}

	public void setQuantitaDisponibile(Double quantitaDisponibile) {
		this.quantitaDisponibile = quantitaDisponibile;
	}

	public Double getQuantitaDaAcquistare() {
		return quantitaDaAcquistare;
	}

	public void setQuantitaDaAcquistare(Double quantitaDaAcquistare) {
		this.quantitaDaAcquistare = quantitaDaAcquistare;
	}

	public Unita getUnita() {
		return unita;
	}

	public void setUnita(Unita unita) {
		this.unita = unita;
	}

	public Double getPrezzoUnitario() {
		return prezzoUnitario;
	}

	public void setPrezzoUnitario(Double prezzoUnitario) {
		this.prezzoUnitario = prezzoUnitario;
	}

	public Double getPrezzoSenzaIva() {
		return prezzoSenzaIva;
	}

	public void setPrezzoSenzaIva(Double prezzoSenzaIva) {
		this.prezzoSenzaIva = prezzoSenzaIva;
	}

	public Double getPrezzoIvato() {
		return prezzoIvato;
	}

	public void setPrezzoIvato(Double prezzoIvato) {
		this.prezzoIvato = prezzoIvato;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getOfferta() {
		return offerta;
	}

	public void setOfferta(int offerta) {
		this.offerta = offerta;
	}
	
	

	public Double getPrezzoScontato() {
		return prezzoScontato;
	}


	public void setPrezzoScontato(Double prezzoScontato) {
		this.prezzoScontato = prezzoScontato;
	}


	public List<Ordine> getListaOrdini() {
		return listaOrdini;
	}

	public void setListaOrdini(List<Ordine> listaOrdini) {
		this.listaOrdini = listaOrdini;
	}

	
	


		
}
