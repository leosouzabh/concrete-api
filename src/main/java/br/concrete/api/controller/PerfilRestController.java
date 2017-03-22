package br.concrete.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.concrete.api.model.Usuario;
import br.concrete.api.repository.UsuarioRepository;
import br.concrete.api.util.ResourceUtils;

@RestController
@RequestMapping("/api/perfil")
public class PerfilRestController {

	private UsuarioRepository usuarioRepository;
	
	public PerfilRestController(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> list(@PathVariable("id") String id, @RequestHeader(value="Authorization") String token) {
		Usuario usuario = usuarioRepository.findById(id);
		return ResponseEntity.ok( ResourceUtils.toUsuarioResource(usuario) );
	}


}
