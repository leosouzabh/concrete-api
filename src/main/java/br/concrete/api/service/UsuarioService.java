package br.concrete.api.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.concrete.api.config.auth.JwtUtil;
import br.concrete.api.exception.ApiException;
import br.concrete.api.exception.RegistroDuplicadoException;
import br.concrete.api.model.Usuario;
import br.concrete.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;
	private PasswordEncoder passwordEncoder;
	private JwtUtil jwtUtil;
	
	public UsuarioService(UsuarioRepository usuarioRepository,
			PasswordEncoder passwordEncoder,
			JwtUtil jwtUtil ) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	public Usuario insert(Usuario usuario) throws ApiException {
		try {
			usuario.setToken(jwtUtil.generateToken(usuario));
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));			
			return usuarioRepository.save(usuario);
		} catch (DataIntegrityViolationException e) {
			throw new RegistroDuplicadoException();
		}
	}

	public Page<Usuario> pesquisa(Usuario filtro, Pageable pageable) {
		Example<Usuario> example = Example.of(filtro,
				ExampleMatcher.matching()
					.withMatcher("login", contains().ignoreCase()));

		return usuarioRepository.findAll(example, pageable);
	}

	
		
}