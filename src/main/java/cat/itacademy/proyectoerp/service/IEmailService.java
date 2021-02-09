package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.User;

public interface IEmailService {

	// method to send welcome email to new user
	public void sendWelcomeEmail(User user);

	// method to send the reset token to then be able to reset the password
	public void passwordResetEmail(User user, String resetToken);
}
