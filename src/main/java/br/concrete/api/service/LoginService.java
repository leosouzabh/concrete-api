package br.concrete.api.service;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.concrete.api.config.auth.JwtUtil;
import br.concrete.api.controller.resource.LoginResource;
import br.concrete.api.exception.ApiException;
import br.concrete.api.exception.SenhaInvalidaException;
import br.concrete.api.exception.UsuarioNaoEncontradoException;
import br.concrete.api.model.Usuario;
import br.concrete.api.repository.UsuarioRepository;

@Service
public class LoginService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private JwtUtil jwtUtil;

	public LoginService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	public Usuario doLogin(LoginResource login) throws ApiException {
		Usuario usuarioDb = usuarioRepository.findByEmail(login.getEmail());
		
		if ( usuarioDb == null ){
			throw new UsuarioNaoEncontradoException();
		
		} else if ( ! passwordEncoder.matches(login.getPassword(), usuarioDb.getPassword()) ){
			throw new SenhaInvalidaException();
		} 

		geraNovoTokenEdataLogin(usuarioDb);		
		return usuarioDb;
			
	}

	private void geraNovoTokenEdataLogin(Usuario usuarioDb) {
		usuarioDb.setToken( jwtUtil.generateToken(usuarioDb) );		
		usuarioDb.setUltimoLogin(new Date());
		usuarioRepository.save(usuarioDb);
	}




}