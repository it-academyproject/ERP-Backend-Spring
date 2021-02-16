package cat.itacademy.proyectoerp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cat.itacademy.proyectoerp.security.jwt.JwtAuthenticationEntryPoint;
import cat.itacademy.proyectoerp.security.jwt.JwtFilters;
import cat.itacademy.proyectoerp.security.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Para poder usar la anotación @PreAuthorized en los controllers
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailServiceImpl userDetailService;

	@Autowired
	JwtAuthenticationEntryPoint jwtEntryPoint;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public JwtFilters jwtFilters() {
		return new JwtFilters();
	}

	/*
	 * @Bean public PasswordEncoder passwordEncoder(){ return new
	 * BCryptPasswordEncoder(); }
	 */

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/api/login", "/api/users", "/api/users/recoverpassword")
				.permitAll().anyRequest().authenticated().and()
				// Exception control. 401 no authorized
				.exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// Filter to validate the tokens with every request
		http.addFilterBefore(jwtFilters(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailService);
		return provider;
	}
}
