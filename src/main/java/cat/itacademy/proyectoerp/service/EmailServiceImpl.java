package cat.itacademy.proyectoerp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.User;

@Service
public class EmailServiceImpl implements IEmailService {

	@Autowired
	JavaMailSender javaMailSender;

	/**
	 * Method to send welcome email to user
	 * 
	 * @param user username
	 * @throws MailException
	 */
	@Override
	public void sendWelcomeEmail(User user) throws MailException {

		SimpleMailMessage message = new SimpleMailMessage();

		// set email properties/body
		message.setTo(user.getUsername());
		message.setFrom("example@gmail.com");
		message.setSubject("Welcome to ProyectoERP");
		message.setText(
				"Welcome to ProyectoERP \n\nThanks for signing up! \n\nTo visit our website click here: https://example.com/");

		// send email
		javaMailSender.send(message);

	}

	/**
	 * Method to send reset token
	 * 
	 * @param user
	 * @param resetToken
	 * @throws MailException
	 */
	@Override
	public void passwordResetEmail(User user, String resetToken) throws MailException {

		SimpleMailMessage message = new SimpleMailMessage();

		// set email properties/body
		message.setTo(user.getUsername());
		message.setFrom("example@gmail.com");
		message.setSubject("Password Reset Request");
		message.setText("To reset your password, click the link below:\n"
				+ "http://localhost:8080/api/users/confirmReset?token=" + resetToken);

		// send email
		javaMailSender.send(message);

	}

}
