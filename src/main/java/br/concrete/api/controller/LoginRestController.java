package br.concrete.api.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.concrete.api.controller.resource.ErroResource;
import br.concrete.api.controller.resource.LoginResource;
import br.concrete.api.exception.SenhaInvalidaException;
import br.concrete.api.exception.UsuarioNaoEncontradoException;
import br.concrete.api.model.Usuario;
import br.concrete.api.service.LoginService;
import br.concrete.api.util.ResourceUtils;

@RestController
@RequestMapping("/api/login")
public class LoginRestController {

	private static final Logger log = LoggerFactory.getLogger(LoginRestController.class);
	
	private final LoginService loginService;
	
	public LoginRestController(LoginService loginService) {
		this.loginService = loginService;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> login(@RequestBody @Valid LoginResource login) {
		try {
			
			Usuario usuario = loginService.doLogin(login);		
			return ResponseEntity.ok( ResourceUtils.toUsuarioResource(usuario));
			
		} catch (UsuarioNaoEncontradoException e) {
			return ResponseEntity.badRequest().body( new ErroResource("Usuário e/ou senha inválidos") );
			
		} catch (SenhaInvalidaException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( new ErroResource("Usuário e/ou senha inválidos") );
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ErroResource("Erro Interno: " + e.getMessage()) );
		}
	}

}
