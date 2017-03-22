package br.concrete.api.controller.resource;

import java.util.Date;
import java.util.List;

public class UsuarioResource  {

	private String id;
	private String name;
	private String email;
	private String password;
	private String token;
	private Date created;
	private Date modified;
	private Date ultimoLogin;
	
	private List<TelefoneResource> phones;
	
	public String getName() {
		return name;
	}
	public UsuarioResource setName(String name) {
		this.name = name;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public UsuarioResource setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public UsuarioResource setPassword(String password) {
		this.password = password;
		return this;		
	}
	public Date getCreated() {
		return created;
	}
	public UsuarioResource setCreated(Date created) {
		this.created = created;
		return this;
	}
	public Date getModified() {
		return modified;
	}
	public UsuarioResource setModified(Date modified) {
		this.modified = modified;
		return this;
	}
	public String getToken() {
		return token;
	}
	public UsuarioResource setToken(String token) {
		this.token = token;
		return this;
	}
	public String getId() {
		return id;		
	}
	public UsuarioResource setId(String id) {
		this.id = id;
		return this;
	}
	public List<TelefoneResource> getPhones() {
		return phones;
	}
	public UsuarioResource setPhones(List<TelefoneResource> phones) {
		this.phones = phones;
		return this;
	}
	public Date getUltimoLogin() {
		return ultimoLogin;
	}
	public UsuarioResource setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
		return this;
	}
	
		
}
