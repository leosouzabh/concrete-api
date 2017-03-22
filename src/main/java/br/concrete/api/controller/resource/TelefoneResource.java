package br.concrete.api.controller.resource;

public class TelefoneResource {

	private String ddd;
	
	private String number;
	
	
	public String getDdd() {
		return ddd;
	}
	public TelefoneResource setDdd(String ddd) {
		this.ddd = ddd;
		return this;
	}
	public String getNumber() {
		return number;
	}
	public TelefoneResource setNumber(String number) {
		this.number = number;
		return this;
	}
	
	
	
}
