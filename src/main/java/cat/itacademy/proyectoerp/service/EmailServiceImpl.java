package cat.itacademy.proyectoerp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Client;
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
	@Async
	public void sendWelcomeEmail(User user) throws MailException {

		SimpleMailMessage message = new SimpleMailMessage();

		// set email properties/body
		message.setTo(user.getUsername());
		message.setSubject("Welcome to ProyectoERP");
		message.setText("Welcome to ProyectoERP \n\nThanks for signing up!");

		// send email
		javaMailSender.send(message);

	}

	/**
	 * Method to send a new random password to user
	 * 
	 * @param user
	 * @param password new random password
	 * @throws MailException
	 */
	@Override
	public void sendPasswordEmail(User user, String password) throws MailException {

		SimpleMailMessage message = new SimpleMailMessage();

		// set email properties/body
		message.setTo(user.getUsername());
	//	message.setFrom("example@gmail.com");
		message.setSubject("Forgot Password");
		message.setText("Your new password is: " + password
				+ "\n\nNow you can login with your new password and navigate through the app, see you soon!");

		// send email
		javaMailSender.send(message);

	}
	
	/**
	 * Method to send a Order confirmation Email to User
	 * 
	 * @param Client
	 * @param Order
	 */
	@Override
	public void sendOrderConfirmationEmail(Client client) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(client.getUser().getUsername());
		message.setSubject("Confirmation Order ");
		message.setText("Hello " + client.getNameAndSurname() + ", your order has been placed");
		javaMailSender.send(message);
	}
	
	/**
	 * Method to send a farewell email to user when they wish to unsubscribe
	 * 
	 * @param user
	 * @throws MailException
	 */	
	@Override
	public void sendFarewellEmail(User user) throws MailException {

		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(user.getUsername());
		message.setSubject("Subscription updates have been successfully made.");
		message.setText("We already miss how close we used to be.");
		
		// send email
		javaMailSender.send(message);
		
	}


}
