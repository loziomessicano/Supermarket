package it.dstech.model;

import java.time.LocalDate;
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
	private LocalDate dataScadenza;
	private Categoria categoria;
	private Double quantitaDisponibile;
	private Double quantitaDaAcquistare;
	private Unita unita;
	private Double prezzoUnitario;
	private Double prezzoSenzaIva;
	private Double prezzoIvato;
	private String img;
	private int offerta;

	@ManyToMany(mappedBy = "listaProdotti")
	private List<User> listaUtenti;

	@ManyToMany(mappedBy = "listaProdotti")
	private int numeroTransazione;

	public List<User> getListaUtenti() {
		return listaUtenti;
	}

	public void setListaUtenti(List<User> listaUtenti) {
		this.listaUtenti = listaUtenti;
	}

	public int getNumeroTransazione() {
		return numeroTransazione;
	}

	public void setNumeroTransazione(int numeroTransazione) {
		this.numeroTransazione = numeroTransazione;
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

	public LocalDate getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(LocalDate dataScadenza) {
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

	@Override
	public String toString() {
		return "Prodotto [id=" + id + ", nome=" + nome + ", marca=" + marca + ", dataScadenza=" + dataScadenza
				+ ", categoria=" + categoria + ", quantitaDisponibile=" + quantitaDisponibile
				+ ", quantitaDaAcquistare=" + quantitaDaAcquistare + ", unita=" + unita + ", prezzoUnitario="
				+ prezzoUnitario + ", prezzoSenzaIva=" + prezzoSenzaIva + ", prezzoIvato=" + prezzoIvato + ", img="
				+ img + ", offerta=" + offerta + ", listaUtenti=" + listaUtenti + ", numeroTransazione="
				+ numeroTransazione + "]";
	}

}
