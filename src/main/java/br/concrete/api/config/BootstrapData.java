package br.concrete.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.concrete.api.config.auth.JwtUtil;
import br.concrete.api.model.Usuario;
import br.concrete.api.repository.UsuarioRepository;

@Component
public class BootstrapData {

	private static final Logger log = LoggerFactory.getLogger(BootstrapData.class);
	
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	private final Boolean fakeData;

	private JwtUtil jwtUtil;

	
	public BootstrapData(UsuarioRepository usuarioRepository, 
					PasswordEncoder passwordEncoder,
			@Value("${modelo.fakedata:false}") Boolean fakeData, JwtUtil jwtUtil) {
		
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.fakeData = fakeData;
		this.jwtUtil = jwtUtil;
	}
	
	@EventListener
	public void onApplicationRefresh(ContextRefreshedEvent event) {
		if(fakeData) {
			log.info("Inserindo dados fake de dev...");
			Usuario usr = new Usuario()
				.setName("Leo")
				.setEmail("leosouzabh@gmail.com")
				.setPassword(passwordEncoder.encode("senha"));
			usr.setToken( jwtUtil.generateToken(usr) );
						
			usuarioRepository.save(usr);
		}		
	}
}
