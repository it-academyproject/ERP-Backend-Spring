package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.User;

public interface IEmailService {
	
	// method to send welcome email to new user
	public void sendWelcomeEmail(User user);
}
