package com.atendimentoams.api.seguranca.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomContainer implements EmbeddedServletContainerCustomizer {

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {

		container.setPort(8081);

	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	    return (container -> {
	        container.setContextPath("");
	        if(System.getenv("PORT")!=null) {
	            container.setPort(Integer.valueOf(System.getenv("PORT"))); 

	        }
	    });
	}
}