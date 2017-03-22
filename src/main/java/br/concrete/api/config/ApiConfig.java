package br.concrete.api.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.concrete.api.config.auth.JwtUtil;
import br.concrete.api.config.auth.SecurityTokenFilter;
import br.concrete.api.service.SecurityService;

@Configuration
@EnableJpaAuditing
public class ApiConfig {
	
	public JwtUtil jwtUtil;
	private SecurityService securityService;
	
	public ApiConfig(JwtUtil jwtUtil, SecurityService securityService) {
		this.jwtUtil = jwtUtil;
		this.securityService = securityService;
	}

	@Bean
	public FilterRegistrationBean someFilterRegistration() {

	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(securityTokenFilter());
	    registration.addUrlPatterns("/api/perfil/*");
	    registration.setName("securityTokenFilter");
	    registration.setOrder(1);
	    return registration;
	} 

	@Bean 
	public Filter securityTokenFilter() {
	    return new SecurityTokenFilter(objectMapper(), securityService);
	}
	
	@Bean 
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	
}