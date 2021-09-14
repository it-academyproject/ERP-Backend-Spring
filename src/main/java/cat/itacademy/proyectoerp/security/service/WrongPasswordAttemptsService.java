package cat.itacademy.proyectoerp.security.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.repository.IUserRepository;

@Service
public class WrongPasswordAttemptsService {

	public static final int MAX_FAILED_ATTEMPTS = 3;
//	private static final int LOCK_TIME_SECONDS_DURATION = 86400; // 24 hours - requirement
	private static final int LOCK_TIME_SECONDS_DURATION = 25; // 20 seconds - for testing

	@Autowired
	IUserRepository userRepository;

	public void increaseFailedAttempts(User user) {
		String username = user.getUsername();
		Integer newFailAttempts = user.getFailedLoginAttempts() + 1;
		userRepository.updateFailedLoginAttempts(newFailAttempts, username);
	}

	public void resetFailedLoginAttempts(String username) {
		userRepository.updateFailedLoginAttempts(0, username);
	}

	public void lock(User user) {
		user.setAccountLocked(true);
		user.setLockTime(LocalDateTime.now());
		userRepository.save(user);
	}

	public boolean unlockWhenTimeExpired(User user) {
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime dateTimeToUnlock; // when the account will be unlocked
		
		dateTimeToUnlock = user.getLockTime().plusSeconds(LOCK_TIME_SECONDS_DURATION); 
		//difference between the current time and the unlocking time
		int secondsToUnlock = (int) ChronoUnit.SECONDS.between(currentTime, dateTimeToUnlock);
		
		if (secondsToUnlock <= 0) {
			user.setAccountLocked(false);
			user.setLockTime(null);
			user.setFailedLoginAttempts(0);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	public String getTimeToUnlock(User user) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTimeToUnlock = user.getLockTime().plusSeconds(LOCK_TIME_SECONDS_DURATION);
		String dateTimeToUnlockText = dateTimeToUnlock.format(formatter);
		return dateTimeToUnlockText;
	}

}