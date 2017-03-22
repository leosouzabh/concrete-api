package br.concrete.api.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.*;

import br.concrete.api.config.auth.JwtUtil;
import br.concrete.api.exception.ApiException;
import br.concrete.api.exception.RegistroDuplicadoException;
import br.concrete.api.model.Usuario;
import br.concrete.api.repository.UsuarioRepository;

public class LoginServiceTest {

	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private JwtUtil jwtUtil;
	
	private PasswordEncoder passwordEncoder;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		this.passwordEncoder = NoOpPasswordEncoder.getInstance();
	}
	
	@Test
	public void testaCadastroComSucesso() throws ApiException {
		
		when(usuarioRepository.save(any(Usuario.class))).thenAnswer((InvocationOnMock i) -> {
			return i.getArgumentAt(0, Usuario.class)
					.setId("uuid123123")
					.setToken("token123token")
					.setCreated(new Date())
					.setModified(new Date())
					.setUltimoLogin(new Date());
		});
		
		when(jwtUtil.generateToken(Mockito.any(Usuario.class))).thenReturn("123");
		
		UsuarioService usuarioService = new UsuarioService(usuarioRepository, passwordEncoder, jwtUtil);
		Usuario usuarioParaSalvar = new Usuario()
				.setName("Leonardo")
				.setPassword("Leonardo");
		usuarioParaSalvar = usuarioService.insert(usuarioParaSalvar);
		
		assertThat(usuarioParaSalvar.getId()).isEqualTo("uuid123123");
		assertThat(usuarioParaSalvar.getToken()).isEqualTo("token123token");
	}
	
	
	
	@Test(expected=RegistroDuplicadoException.class)
	public void testaCadastroComRegistroDuplicado() throws ApiException {
		
		when(usuarioRepository.save(any(Usuario.class))).thenThrow(RegistroDuplicadoException.class);
		
		when(jwtUtil.generateToken(Mockito.any(Usuario.class))).thenReturn("123");
		
		UsuarioService usuarioService = new UsuarioService(usuarioRepository, passwordEncoder, jwtUtil);
		Usuario usuarioParaSalvar = new Usuario()
				.setName("Leonardo")
				.setPassword("Leonardo");
		usuarioParaSalvar = usuarioService.insert(usuarioParaSalvar);
	}
	
}
