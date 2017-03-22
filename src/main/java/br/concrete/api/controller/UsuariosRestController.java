package br.concrete.api.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.concrete.api.controller.resource.ErroResource;
import br.concrete.api.controller.resource.SalvarUsuarioResource;
import br.concrete.api.controller.resource.TelefoneResource;
import br.concrete.api.exception.ApiException;
import br.concrete.api.model.Telefone;
import br.concrete.api.model.Usuario;
import br.concrete.api.service.UsuarioService;
import br.concrete.api.util.ResourceUtils;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosRestController {

	private static final Logger log = LoggerFactory.getLogger(UsuariosRestController.class);
	
	private UsuarioService usuarioService;
	
	public UsuariosRestController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> save(@RequestBody @Valid SalvarUsuarioResource usuario) {
		try {
			log.debug("POST para salvar usuario");

			Set<Telefone> telefones = new HashSet<>();
			
			if ( usuario.getPhones() != null ){
				for ( TelefoneResource resource : usuario.getPhones() ){
					telefones.add(new Telefone().setDdd(resource.getDdd()).setNumber(resource.getNumber()) );
				}
			}
			
			Usuario usuarioS = new Usuario()
				.setName(usuario.getName())
				.setEmail(usuario.getEmail())
				.setPassword(usuario.getPassword())
				.setUltimoLogin(new Date())
				.setPhones(telefones);			
			
			usuarioS = usuarioService.insert(usuarioS);
			
			return ResponseEntity.status(HttpStatus.CREATED).body( ResourceUtils.toUsuarioResource(usuarioS) );
			
		} catch (ApiException e) {
			return ResponseEntity.badRequest().body( new ErroResource("E-mail já existente") );
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ErroResource(e.getMessage()) );
		}
	}


	

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Iterable<Usuario>> list(Pageable pageable) {
		log.debug("GET para pesquisar usuários size {}, page {}, sort {}", pageable.getPageSize(), pageable.getPageNumber(), pageable.getSort()); //?size=2&page=30&sort=login,desc
		return ResponseEntity.ok(usuarioService.pesquisa(new Usuario(), pageable));
	}
	
	
		

}
