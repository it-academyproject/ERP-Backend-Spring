package cat.itacademy.proyectoerp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Cors configuration class: set which origins are allowed, which HTTP methods
 * and the time that the response will be cached(maxAge).
 */

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/api/**").allowedOrigins("*").allowedHeaders("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE").maxAge(3600);
	}

}
