package br.concrete.api.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="USERS")
public class Usuario {

	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;

	@NotBlank
	private String name;

	@NotBlank
	@Column(name = "username", unique = true)
	private String email;
	
	@NotBlank
	private String token;
	
	@NotBlank
	private String password;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Telefone> phones;

	@CreatedDate
	private Date created;

	@LastModifiedDate 
	private Date modified;
	
	private Date ultimoLogin;

	@Version
	private Long version;

	public String getId() {
		return id;
	}

	public Usuario setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Usuario setName(String name) {
		this.name = name;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Usuario setEmail(String email) {
		this.email = email;
		return this;
	}


	public String getPassword() {
		return password;
	}

	public Usuario setPassword(String password) {
		this.password = password;
		return this;
	}

	public Set<Telefone> getPhones() {
		return phones;
	}

	public Usuario setPhones(Set<Telefone> phones) {
		this.phones = phones;
		return this;
	}

	public Long getVersion() {
		return version;
	}

	public Usuario setVersion(Long version) {
		this.version = version;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public Usuario setCreated(Date created) {
		this.created = created;
		return this;
	}

	public Date getModified() {
		return modified;
	}

	public Usuario setModified(Date modified) {
		this.modified = modified;
		return this;		
	}

	public String getToken() {
		return token;
	}

	public Usuario setToken(String token) {
		this.token = token;
		return this;
	}

	public Date getUltimoLogin() {
		return ultimoLogin;
	}

	public Usuario setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
		return this;
	}

	

}
