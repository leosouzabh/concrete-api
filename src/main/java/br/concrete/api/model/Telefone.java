package br.concrete.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Telefone {
	
	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	private String ddd;
	private String number;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDdd() {
		return ddd;
	}
	public Telefone setDdd(String ddd) {
		this.ddd = ddd;
		return this;
	}
	public String getNumber() {
		return number;
	}
	public Telefone setNumber(String number) {
		this.number = number;
		return this;
	}
		
}
