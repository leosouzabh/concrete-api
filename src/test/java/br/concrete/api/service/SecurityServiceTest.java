package br.concrete.api.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.concrete.api.config.auth.JwtUtil;
import br.concrete.api.exception.ApiException;
import br.concrete.api.exception.NaoAutorizadoException;
import br.concrete.api.exception.RegistroDuplicadoException;
import br.concrete.api.exception.SessaoInvalidaException;
import br.concrete.api.model.Usuario;
import br.concrete.api.repository.UsuarioRepository;

public class SecurityServiceTest {

	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private JwtUtil jwtUtil;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected=NaoAutorizadoException.class)
	public void testaValidacaoSemToken() throws ApiException {
		SecurityService securityService = new SecurityService(usuarioRepository, jwtUtil);
		securityService.checkToken(null, null);
	}
	
	@Test(expected=NaoAutorizadoException.class)
	public void testaValidacaoComTokenInexistente() throws ApiException {
		when(usuarioRepository.findById(any(String.class))).thenReturn(null);
		SecurityService securityService = new SecurityService(usuarioRepository, jwtUtil);
		securityService.checkToken("Bearer token", "");
	}
	
	@Test(expected=SessaoInvalidaException.class)
	public void testaValidacaoComTokenExpirado() throws ApiException {
		when(usuarioRepository.findById(any(String.class))).thenThrow(SessaoInvalidaException.class);
		SecurityService securityService = new SecurityService(usuarioRepository, jwtUtil);
		securityService.checkToken("Bearer token", "");
	}
	
	@Test
	public void testaValidacaoComTokenValido() throws ApiException {
		when(usuarioRepository.findById(any(String.class))).thenReturn(new Usuario().setToken("tokenvalido"));
		when(jwtUtil.parseToken(any(String.class))).thenReturn(new Usuario().setId("uuid"));
		
		SecurityService securityService = new SecurityService(usuarioRepository, jwtUtil);
		securityService.checkToken("Bearer tokenvalido", "uuidvalido");
	}
	
}
