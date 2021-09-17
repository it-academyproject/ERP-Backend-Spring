package cat.itacademy.proyectoerp.security.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.repository.IUserRepository;

@Service
public class LoginService {
	
	@Autowired
	IUserRepository userRepository;

	@Autowired
	WrongPasswordAttemptsService wrongPasswordAttemptsService;

	public void updateLastSession(String username) {
		User user = userRepository.findByUsername(username);
		// successful login with user non-locked and no password failed attempts
		if (!user.isAccountLocked() && user.getFailedLoginAttempts() == 0) {
			user.setLastSession(LocalDateTime.now());
		} else {
			// successful login after one or two password fails (max=3), resets count
			if (user.getFailedLoginAttempts() > 0 && !user.isAccountLocked()) {
				user.setLastSession(LocalDateTime.now());
				wrongPasswordAttemptsService.resetFailedLoginAttempts(username);
			// successful login when user unlocked right after lock time expired and 0 failed attempts
			} else if (wrongPasswordAttemptsService.unlockWhenTimeExpired(user)) {
				user.setLastSession(LocalDateTime.now());
			// user account locked
			} else if (user.isAccountLocked()) {
				String timeToUnlock = wrongPasswordAttemptsService.getTimeToUnlock(user);
				throw new LockedException("Your account has been locked due to 3 failed attempts."
						+ " It will be unlocked by " + timeToUnlock);
			}
		}
	}

	public String handlePasswordFail(String username) {
		User user = userRepository.findByUsername(username);
		if (user.getActive() && !user.isAccountLocked()) {
			// user non-locked and failed attempts less than max, increase count and set last session to null
			if (user.getFailedLoginAttempts() < WrongPasswordAttemptsService.MAX_FAILED_ATTEMPTS - 1) {
				user.setLastSession(null);
				wrongPasswordAttemptsService.increaseFailedAttempts(user);
				return "Invalid user credentials";
			}
			// lock user if non-locked when max reached
			if (user.getLockTime() == null) {
				user.setLastSession(null);
				wrongPasswordAttemptsService.lock(user);
			}
			/* failed attempt and account has just been locked - outside the if(user.getLocktime);
			 * this return statement located outside the if() right above prevents a successful login after account locked 
			 */
			return "Your account has been locked due to 3 failed attempts." + " It will be unlocked by "
			+ wrongPasswordAttemptsService.getTimeToUnlock(user);
		// failed password attempt right after lock period expired, increase count
		} else if (wrongPasswordAttemptsService.unlockWhenTimeExpired(user)) {
			wrongPasswordAttemptsService.increaseFailedAttempts(user);
			return "Invalid user credentials";
		}
		// failed attempt while lock period in force
		return "Your account has been locked due to 3 failed attempts." + " It will be unlocked by "
				+ wrongPasswordAttemptsService.getTimeToUnlock(user);
	}
}
