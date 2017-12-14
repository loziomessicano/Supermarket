package it.dstech.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@GeneratedValue
	int id;

	String username;

	String password;

	RoleUser role;

	TipoUtente tipo;

	String tel;

	String via;

	String cap;

	String citta;

	String prov;

	@JsonIgnore
	@OneToMany
	List<CartaDiCredito> listaCarte;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	List<Ordine> listaOrdini;

	public User() {

	}

	public List<CartaDiCredito> getListaCarte() {
		return listaCarte;
	}

	public void setListaCarte(List<CartaDiCredito> listaCarte) {
		this.listaCarte = listaCarte;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleUser getRole() {
		return role;
	}

	public void setRole(RoleUser role) {
		this.role = role;
	}

	public TipoUtente getTipo() {
		return tipo;
	}

	public void setTipo(TipoUtente tipo) {
		this.tipo = tipo;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	
	public List<Ordine> getListaOrdini() {
		return listaOrdini;
	}

	public void setListaOrdini(List<Ordine> listaOrdini) {
		this.listaOrdini = listaOrdini;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", tipo="
				+ tipo + ", tel=" + tel + ", via=" + via + ", cap=" + cap + ", citFta=" + citta + ", prov=" + prov
				+ ", listaCarte=" + listaCarte + ", listaOrdini=" + listaOrdini + "]";
	}

}
