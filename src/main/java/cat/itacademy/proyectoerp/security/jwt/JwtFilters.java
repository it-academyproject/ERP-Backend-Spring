package cat.itacademy.proyectoerp.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;


import cat.itacademy.proyectoerp.security.CustomLoginFailureHandler;
import cat.itacademy.proyectoerp.security.CustomLoginSuccessHandler;
import cat.itacademy.proyectoerp.security.service.UserDetailServiceImpl;
import cat.itacademy.proyectoerp.service.UserServiceImpl;

public class JwtFilters extends OncePerRequestFilter{
	
	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	UserDetailServiceImpl userDetailsService;

	@Autowired
	UserServiceImpl userService;
	
		
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
	try {
		
		// if token is valid configure Spring Security
		String token = theToken(req);
	    if(token != null && jwtUtil.validateToken(token)){
	    	//With username (extract from token) we load User (userdetails) from userdetailsservice.
	    	UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.getNameOfUser(token));

	        UsernamePasswordAuthenticationToken auth =
	           new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	            
	        // Wwe specify that the current user is authenticated. We pass the
			// Spring Security Configurations successfully.
	        SecurityContextHolder.getContext().setAuthentication(auth);     	       
	    }
		
	} catch (Exception e){
		System.out.println("fail en el método doFilter " + e.getMessage());
	}
	  filterChain.doFilter(req, res);
	}

	/**
	 * Method, return the token without the header 'Bearer'
	 * @param request
	 * @return
	 */
	private String theToken(HttpServletRequest request){
	        
		String header = request.getHeader("Authorization");
	    if(header != null && header.startsWith("Bearer"))
	    	return header.replace("Bearer ", "");
	    return null;
	}
}
