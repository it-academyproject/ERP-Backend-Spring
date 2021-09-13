package cat.itacademy.proyectoerp.security.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.repository.IUserRepository;

@Service
public class LoginAttemptsService {

	public static final int MAX_FAILED_ATTEMPTS = 3;
	private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

	@Autowired
	IUserRepository userRepository;

	public void increaseFailedAttempts(User user) {
		String username = user.getUsername();
		Integer newFailAttempts = user.getFailedAttempts() + 1;
		userRepository.updateFailedAttempts(newFailAttempts, username);
	}

	public void resetFailedAttempts(String username) {
		userRepository.updateFailedAttempts(0, username);
	}

	public void lock(User user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());
		userRepository.save(user);
	}

	public boolean unlockWhenTimeExpired(User user) {
		long lockTimeInMillis = user.getLockTime().getTime();
		long currentTimeInMillis = System.currentTimeMillis();

		if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
			user.setAccountNonLocked(true);
			user.setLockTime(null);
			user.setFailedAttempts(0);
			
			userRepository.save(user);
			return true;
		}

		return false;
	}

}