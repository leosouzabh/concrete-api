package br.concrete.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ConcreteApiApplication extends SpringBootServletInitializer  {

	private static final Logger log = LoggerFactory.getLogger(ConcreteApiApplication.class);

	
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ConcreteApiApplication.class);
		
        log.info("Contexto: {}", ctx.getApplicationName());
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ConcreteApiApplication.class);
	}
	
}
