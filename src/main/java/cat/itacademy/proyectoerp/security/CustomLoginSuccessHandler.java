package cat.itacademy.proyectoerp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.repository.IUserRepository;
import cat.itacademy.proyectoerp.security.service.LoginAttemptsService;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private LoginAttemptsService loginAttemptsService;

	@Autowired
	private IUserRepository userRepository;

//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//								Authentication authentication) throws IOException, ServletException {
//
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//		String username = userDetails.getUsername();
//		User user = userRepository.findByUsername(username);
//
//		if (user.getFailedAttempts() > 0) {
//			loginAttemptsService.resetFailedAttempts(username);
//		}
//
//		super.onAuthenticationSuccess(request, response, authentication);
//	}

}
