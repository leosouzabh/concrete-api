package br.concrete.api.controller;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import br.concrete.api.repository.UsuarioRepository;
import br.concrete.api.service.LoginService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PerfilRestController.class)
public class PerfilRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private LoginService loginService;
	
	@MockBean
	private UsuarioRepository usuarioRepository;

	private ObjectMapper mapper;

	@Before
	public void setup() {
		mapper = new ObjectMapper();

	}

	/*@Test
	public void loginValidoRetorna200() throws Exception {
		when(loginService.doLogin(any(LoginResource.class))).thenReturn(new Usuario().setToken("token"));

		LoginResource dadosLogin = new LoginResource(); 

		dadosLogin
			.setEmail("leosouzabH@gmail.com")
			.setPassword("password"); 

		mvc.perform(get("/api/perfil")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2FvQHNpbHZhMi5vcmciLCJleHAiOjE0OTAxMjQwMDR9.8Hll6szZJN1eWYvn5wKbtZC1W2tL87lHFdAX6QQ85DPiYmxBSa715F6xplDHlXL8jnYgxUYJ6AoODCZZ-Ui-gw")
				.content(mapper.writeValueAsString(dadosLogin)))				
			.andExpect(status().isOk());			
	}*/

}