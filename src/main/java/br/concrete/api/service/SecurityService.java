package br.concrete.api.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import br.concrete.api.config.auth.JwtUtil;
import br.concrete.api.exception.NaoAutorizadoException;
import br.concrete.api.exception.SessaoInvalidaException;
import br.concrete.api.exception.TokenInexistenteException;
import br.concrete.api.model.Usuario;
import br.concrete.api.repository.UsuarioRepository;

@Service
public class SecurityService {

	private UsuarioRepository usuarioRepository;
	private JwtUtil jwtUtil;

	public SecurityService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
		this.usuarioRepository = usuarioRepository;
		this.jwtUtil = jwtUtil;
	}



	public void checkToken(String token) throws NaoAutorizadoException, SessaoInvalidaException {
		
		if ( StringUtils.isEmpty(token) ){
			throw new NaoAutorizadoException();
		
		} else {
			token = token.replace("Bearer ", "");
			
			try {
				Usuario usuario = usuarioRepository.findByToken(token);
				if ( usuario == null ){
					throw new NaoAutorizadoException();		
				}			
				jwtUtil.parseToken(token);

			} catch (TokenInexistenteException e) {
				throw new NaoAutorizadoException();
			}
			
		}
		
		
	}




}