package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.User;

public interface IEmailService {

	// method to send welcome email to new user
	public void sendWelcomeEmail(User user);

	// method to send a new random password to user
	public void sendPasswordEmail(User user, String password);

	//Method to send a Order confirmation Email to User
	void sendOrderConfirmationEmail(Client client);
	
	// method to send farewell email to user when unsubscribing
	public void sendFarewellEmail(User user);
	
	// method to send an email to a client when its order changes to IN_DELIVERY status
	public void sendOrderInDeliveryEmail(Client client);
	
	// method to send an email to a client when its order changes to CANCELLED status
	public void sendOrderCancelledEmail(Client client);
	
}
