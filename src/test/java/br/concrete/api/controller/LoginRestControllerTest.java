package br.concrete.api.controller;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.concrete.api.controller.resource.LoginResource;
import br.concrete.api.exception.SenhaInvalidaException;
import br.concrete.api.exception.UsuarioNaoEncontradoException;
import br.concrete.api.model.Usuario;
import br.concrete.api.service.LoginService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoginRestController.class)
public class LoginRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private LoginService loginService;

	private ObjectMapper mapper;

	@Before
	public void setup() {
		mapper = new ObjectMapper();

	}

	@Test
	public void loginValidoRetorna200() throws Exception {
		when(loginService.doLogin(any(LoginResource.class))).thenReturn(new Usuario().setToken("token"));

		LoginResource dadosLogin = new LoginResource(); 

		dadosLogin
			.setEmail("leosouzabH@gmail.com")
			.setPassword("password"); 

		mvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dadosLogin)))				
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token").isNotEmpty());			
	}


	@Test
	public void loginInvalidoComUsuarioErradoRetorna400() throws Exception {
		when(loginService.doLogin(any(LoginResource.class))).thenThrow(UsuarioNaoEncontradoException.class);

		LoginResource dadosLogin = new LoginResource(); 

		dadosLogin
			.setEmail("leosouzabH@gmail.com")
			.setPassword("password"); 

		mvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dadosLogin)))				
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.token").doesNotExist() )			
			.andExpect(jsonPath("$.mensagem").value("Usuário e/ou senha inválidos"));			
	}
	
	
	@Test
	public void loginInvalidoComUsuarioCertoESenhaErradaRetorna401() throws Exception {
		when(loginService.doLogin(any(LoginResource.class))).thenThrow(SenhaInvalidaException.class);
		
		LoginResource dadosLogin = new LoginResource(); 
		
		dadosLogin
		.setEmail("leosouzabH@gmail.com")
		.setPassword("password"); 
		
		mvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dadosLogin)))				
		.andExpect(status().isUnauthorized())		
		.andExpect(jsonPath("$.token").doesNotExist() )
		.andExpect(jsonPath("$.mensagem").value("Usuário e/ou senha inválidos"));
	}
	
	@Test
	public void loginComErroInesperadoRetorna500() throws Exception {
		when(loginService.doLogin(any(LoginResource.class))).thenThrow(RuntimeException.class);
		
		LoginResource dadosLogin = new LoginResource(); 
		
		dadosLogin
		.setEmail("leosouzabH@gmail.com")
		.setPassword("password"); 
		
		mvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dadosLogin)))				
		.andExpect(status().isInternalServerError())		
		.andExpect(jsonPath("$.token").doesNotExist() )
		.andExpect(jsonPath("$.mensagem").exists());
	}
}

