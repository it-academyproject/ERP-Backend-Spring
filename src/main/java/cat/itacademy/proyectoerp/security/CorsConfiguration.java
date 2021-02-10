package cat.itacademy.proyectoerp.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class CorsConfiguration implements WebMvcConfigurer { //, Filter

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/api/**").allowedOrigins("*").allowedHeaders("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD").maxAge(3600);
	}

	//.exposedHeaders("Authorization")
	
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//			throws IOException, ServletException {
//		final HttpServletResponse response = (HttpServletResponse) res;
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//		response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
//		response.setHeader("Access-Control-Max-Age", "3600");
//		if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
//			response.setStatus(HttpServletResponse.SC_OK);
//		} else {
//			chain.doFilter(req, res);
//		}
//	}
	
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*")
//				.allowedMethods("*").maxAge(3600);
//	}
}
