package br.concrete.api.controller.resource;

import org.hibernate.validator.constraints.NotBlank;

public class LoginResource {

	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	
	public String getEmail() {
		return email;
	}

	public LoginResource setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public LoginResource setPassword(String password) {
		this.password = password;
		return this;
	}

	
}
