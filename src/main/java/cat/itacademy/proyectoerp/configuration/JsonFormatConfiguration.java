package cat.itacademy.proyectoerp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;



@Configuration
public class JsonFormatConfiguration {
	 @Bean
	    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
	        return new Jackson2ObjectMapperBuilder().propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	    }

}
