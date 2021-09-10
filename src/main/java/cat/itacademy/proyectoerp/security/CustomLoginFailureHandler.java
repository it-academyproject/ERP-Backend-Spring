package cat.itacademy.proyectoerp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.security.service.LoginAttemptsService;
import cat.itacademy.proyectoerp.service.IUserService;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private LoginAttemptsService loginAttemptService;

	@Autowired
	private IUserService userService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException exception) throws IOException, ServletException {
		
		String username = request.getParameter("username");
		User user = userService.findByUsername(username);

//		if (user != null) {
//			if (user.getActive() && user.getAccountNonLocked()) {
//				if (user.getFailedAttempts() < loginAttemptService.MAX_FAILED_ATTEMPTS - 1) {
//					loginAttemptService.increaseFailedAttempts(user);
//				} else {
//					loginAttemptService.lock(user);
//					exception = new LockedException("Your account has been locked due to 3 failed attempts."
//							+ " It will be unlocked after 24 hours.");
//				}
//			} else if (!user.getAccountNonLocked()) {
//				if (loginAttemptService.unlockWhenTimeExpired(user)) {
//					exception = new LockedException("Your account has been unlocked. Please try to login again.");
//				}
//			}
//
//		}
//
////		super.setDefaultFailureUrl("/login");
//		super.onAuthenticationFailure(request, response, exception);
	}
}
