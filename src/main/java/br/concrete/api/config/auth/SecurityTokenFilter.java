package br.concrete.api.config.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.concrete.api.controller.resource.ErroResource;
import br.concrete.api.exception.NaoAutorizadoException;
import br.concrete.api.exception.SessaoInvalidaException;
import br.concrete.api.service.SecurityService;

public class SecurityTokenFilter  extends GenericFilterBean {

	private ObjectMapper objectMapper;
	private SecurityService securityService;

	public SecurityTokenFilter(ObjectMapper objectMapper, SecurityService securityService){
		this.objectMapper = objectMapper;
		this.securityService = securityService;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String uuid = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()).replace("/api/perfil/", "");
		
		
		try {
			securityService.checkToken(httpRequest.getHeader("Authorization"), uuid);
			chain.doFilter(request, response);
			
		} catch (NaoAutorizadoException e1){
			setResponse(httpResponse, "Não autorizado");
			
		} catch (SessaoInvalidaException e) {
			setResponse(httpResponse, "Sessão inválida");
		}
		
		
	}

	private void setResponse(HttpServletResponse httpResponse, String msg) throws IOException, JsonProcessingException {
		httpResponse.setContentType( MediaType.APPLICATION_JSON_UTF8_VALUE );
		httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		httpResponse.getWriter().append( builtErrorMessage(msg) );
	}

	private String builtErrorMessage(String msg) throws JsonProcessingException {
		return objectMapper.writeValueAsString(new ErroResource(msg));
	}

}