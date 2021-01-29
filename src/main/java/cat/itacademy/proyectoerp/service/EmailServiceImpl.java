package cat.itacademy.proyectoerp.service;
import org.springframework.context.MessageSource;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.User;

@Service
public class EmailServiceImpl {

	@Autowired
	JavaMailSender javaMailSender;

    private MessageSource messages;
	/**
	 * Method to send welcome email to user
	 * 
	 * @param user username
	 * @throws MailException
	 */
	public void sendWelcomeEmail(User user) throws MailException {

		SimpleMailMessage message = new SimpleMailMessage();

		// set email properties/body
		message.setTo(user.getUsername());
		message.setFrom("example@gmail.com");
		message.setSubject("Welcome to ProyectoERP");
		message.setText(
				"Welcome to ProyectoERP \n\nThanks for signing up! \n\n To visit our website click here: https://example.com/");

		javaMailSender.send(message);

	}
	
	
	public  SimpleMailMessage constructResetTokenEmail(
		 String contextPath, Locale locale, String token, String  user) {
		 String url = contextPath + "/user/changePassword?token=" + token;
		 String message = messages.getMessage("message.resetPassword", null, locale);
		 
		 return constructEmail("Reset Password", message + " \r\n" + url, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body,  String user) {
		 SimpleMailMessage email = new SimpleMailMessage();
		
		 email.setSubject(subject);
		 email.setText(body);
		 email.setTo(user);
		 email.setFrom("support.email");
		
		 return email;
	}

	public void sendResetPass(SimpleMailMessage message) {
	javaMailSender.send(message);
	}
}