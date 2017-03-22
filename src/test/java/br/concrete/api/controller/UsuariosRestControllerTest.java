package br.concrete.api.controller;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.concrete.api.config.auth.JwtUtil;
import br.concrete.api.controller.UsuariosRestController;
import br.concrete.api.controller.resource.SalvarUsuarioResource;
import br.concrete.api.controller.resource.TelefoneResource;
import br.concrete.api.exception.RegistroDuplicadoException;
import br.concrete.api.model.Usuario;
import br.concrete.api.service.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UsuariosRestController.class)
public class UsuariosRestControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private JwtUtil jwtUtil;

	private ObjectMapper mapper;
	
	@Before
	public void setup() {
		mapper = new ObjectMapper();

	}
	
	@Test
	public void cadastraUsuarioComValoresValidosRetorna201() throws Exception {
		when(usuarioService.insert(any(Usuario.class))).thenAnswer((InvocationOnMock i) -> {
			return i.getArgumentAt(0, Usuario.class)
					.setId("uuid123123")
					.setToken("token123token")
					.setCreated(new Date())
					.setModified(new Date())
					.setUltimoLogin(new Date());
		});
		
		SalvarUsuarioResource usuarioParaSalvar = new SalvarUsuarioResource(); 

		List<TelefoneResource> phones = new ArrayList<>();
		phones.add(new TelefoneResource().setDdd("31").setNumber("9999-8888") );
		phones.add(new TelefoneResource().setDdd("21").setNumber("9999-8888") );
		phones.add(new TelefoneResource().setDdd("11").setNumber("9999-8888") );
		usuarioParaSalvar
			.setName("Leonardo")
			.setEmail("leosouzabH@gmail.com")
			.setPassword("password")
			.setPhones(phones); 
		
		mvc.perform(post("/api/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(usuarioParaSalvar)))				
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value("uuid123123"))
			.andExpect(jsonPath("$.token").isNotEmpty())
			.andExpect(jsonPath("$.created").isNotEmpty())
			.andExpect(jsonPath("$.modified").isNotEmpty())
			.andExpect(jsonPath("$.ultimoLogin").isNotEmpty())
			.andExpect(jsonPath("$.name").value("Leonardo"))
			.andExpect(jsonPath("$.email").value("leosouzabH@gmail.com"))
			.andExpect(jsonPath("$.phones[0]").exists()  )
			.andExpect(jsonPath("$.phones[1]").exists()  )
			.andExpect(jsonPath("$.phones[2]").exists()  )
			.andExpect(jsonPath("$.phones[3]").doesNotExist()  );
			
	}
	
	
	@Test
	public void cadastraUsuarioComValoresJaCadastradosRetorna400() throws Exception {
		when(usuarioService.insert(any(Usuario.class))).thenThrow(RegistroDuplicadoException.class);
		
		
		List<TelefoneResource> phones = new ArrayList<>();
		phones.add(new TelefoneResource().setDdd("11").setNumber("9999-8888") );
		
		SalvarUsuarioResource usuarioParaSalvar = new SalvarUsuarioResource()
			.setName("Leonardo")
			.setEmail("leosouzabH@gmail.com")
			.setPassword("password")
			.setPhones(phones); 
		
		mvc.perform(post("/api/usuarios")
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(usuarioParaSalvar)))		
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.mensagem").isNotEmpty() );		
	}
	
	
	@Test
	public void cadastraUsuarioComNomeEmBrancoRetorna400() throws Exception {

		SalvarUsuarioResource usuarioParaSalvar = new SalvarUsuarioResource()
				.setName("")
				.setEmail("leosouzabH@gmail.com")
				.setPassword("password"); 
		
		mvc.perform(post("/api/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(usuarioParaSalvar)))		
		.andExpect(status().isBadRequest());
	}
	
	
	/*
	@Test
	public void postWithInvalidBodyReturns400() throws Exception {
		mvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void putWithValidBodyAndExistingProductReturns200() throws Exception {
		when(avenueAPIService.findProduct(1L)).thenReturn(new Product().setId(1L).setImages(new HashSet<>()).setChildProducts(new HashSet<>()));
		when(avenueAPIService.saveProduct(any(Product.class))).thenAnswer((InvocationOnMock i) -> {
			return i.getArgumentAt(0, Product.class).setId(1L);
		});
		when(transformer.updateProductFromResource(any(ProductResource.class), any(Product.class))).thenReturn(new Product());
		when(transformer.createProductResourceWithRelationships(any(Product.class))).thenReturn(new ProductResource());
		
		ProductResource resource = new ProductResource()
				.setName("name test")
				.setDescription("description test");
		
		mvc.perform(put("/api/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(resource)))				
			.andExpect(status().isOk());
	}
	
	@Test
	public void putWithValidBodyButNonExistingProductReturns404() throws Exception {
		ProductResource resource = new ProductResource()
				.setName("name test")
				.setDescription("description test");
		
		mvc.perform(put("/api/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(resource)))				
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void putWithInvalidBodyReturns400() throws Exception {
		mvc.perform(put("/api/products/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteAlwaysReturn204() throws Exception {
		mvc.perform(delete("/api/products/1")).andExpect(status().isNoContent());
	}*/
}
