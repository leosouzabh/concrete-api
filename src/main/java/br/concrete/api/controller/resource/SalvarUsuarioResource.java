package br.concrete.api.controller.resource;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class SalvarUsuarioResource {

	@NotBlank
	private String name;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	private List<TelefoneResource> phones;

	public String getName() {
		return name;
	}

	public SalvarUsuarioResource setName(String name) {
		this.name = name;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public SalvarUsuarioResource setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public SalvarUsuarioResource setPassword(String password) {
		this.password = password;
		return this;
	}

	public List<TelefoneResource> getPhones() {
		return phones;
	}

	public SalvarUsuarioResource setPhones(List<TelefoneResource> phones) {
		this.phones = phones;
		return this;
	}
	
	
	
	
}
