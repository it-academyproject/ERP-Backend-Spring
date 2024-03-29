package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.Notification;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.repository.IUserRepository;


@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    JavaMailSender javaMailSender;
    
    @Autowired
    IUserRepository userRepository;

    /**
     * Method to send welcome email to user
     *
     * @param user username
     */
    @Override
    @Async
    public void sendWelcomeEmail(User user) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            // set email properties/body
            message.setTo(user.getUsername());
            message.setSubject("Welcome to ProyectoERP");
            message.setText("Welcome to ProyectoERP \n\nThanks for signing up!");
            
            // send email
            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to send a new random password to user
     *
     * @param user
     * @param password new random password
     */
    @Override
    public void sendPasswordEmail(User user, String password) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            // set email properties/body
            message.setTo(user.getUsername());
            
            message.setSubject("Recover email account  ");
            message.setText("Your new password is: " + password
                    + "\n\nNow you can login with your new password and navigate through the app, see you soon!");

            // send email
            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to send a Order confirmation Email to User
     *
     * @param client
     */

    @Override
    public void sendOrderConfirmationEmail(Client client) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(client.getUser().getUsername());
            message.setSubject("Confirmation Order ");
            message.setText("Hello " + client.getNameAndSurname() + ", your order has been placed");
            javaMailSender.send(message);

        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to send a farewell email to user when they wish to unsubscribe
     *
     * @param user
     */
    @Override
    public void sendFarewellEmail(User user) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(user.getUsername());
            message.setSubject("Subscription updates have been successfully made.");
            message.setText("We already miss how close we used to be.");

            // send email
            javaMailSender.send(message);

        } catch (MailException e) {
            e.printStackTrace();
        }

    }

    /**
     * Send an email to a client when its order changes to IN_DELIVERY status
     */
	@Override
	public void sendOrderInDeliveryEmail(Client client) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			
			message.setTo(client.getUser().getUsername());
			message.setSubject("Order updates");
			message.setText("Your order is in the delivery process.");
			
			javaMailSender.send(message);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send an email to a client when its order changes to CANCELLED status
	 */
	@Override
	public void sendOrderCancelledEmail(Client client) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			
			message.setTo(client.getUser().getUsername());
			message.setSubject("Order updates");
			message.setText("Your order has been cacelled.");
			
			javaMailSender.send(message);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  Send an email to all admins on an employee checkin
	 */
	@Override
	public void sendEmployeeCheckin(Notification notification) {
		userRepository.findByUserType(UserType.ADMIN).forEach(admin -> {
			try {
				SimpleMailMessage  message = new SimpleMailMessage();
				
				message.setTo(admin.getUsername());
				message.setSubject("New Check-in Notification");
				message.setText(notification.getMessage());
				
				javaMailSender.send(message);
			} catch (MailException e) {
				e.printStackTrace();
			}
		});
	}

}