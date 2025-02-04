package pe.edu.cibertec.rueditas_frontend.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
     return builder.build(); // RestTemplateBuilder crea un nuevo RestTemplate con configuraciones por defecto
    }
}
