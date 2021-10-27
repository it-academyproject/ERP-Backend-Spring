package cat.itacademy.proyectoerp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Notification;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.repository.IEmployeeRepository;
import cat.itacademy.proyectoerp.repository.INotificationRepository;
import cat.itacademy.proyectoerp.repository.IUserRepository;

@Service
public class NotificationServiceImpl implements INotificationService {

	@Autowired
	private INotificationRepository notificationRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IEmployeeRepository employeeRepository;

	@Override
	public List<Notification> getNotificationsByUser(User user) {
		List<Notification> notifications = notificationRepository.findByUser(user);

		readNotifications(notifications);

		return notifications;
	}

	/**
	 * Adds a notification to a single user
	 */
	@Override
	public void notifyUser(Notification notification, User user) {
		Optional<User> userDb = userRepository.findById(user.getId());

		if (userDb.isPresent()) {
			notification.setUser(userDb.get());
			notificationRepository.save(notification);
		}
	}

	/**
	 * Adds a notification to every user from a list of users
	 */
	@Override
	public void notifyUsers(Notification notification, List<User> users) {
		List<Notification> notificationsBatch = new ArrayList<>();
		
		users.forEach(user -> {
			Optional<User> userDb = userRepository.findById(user.getId());

			if (userDb.isPresent()) {
				Notification notificationClone = new Notification(notification);
				notificationClone.setUser(userDb.get());
				notificationsBatch.add(notificationClone);
			}
		});
		
		notificationRepository.saveAll(notificationsBatch);
	}

	/**
	 * Adds a notification to all of the employees
	 */
	@Override
	public void notifyAllEmployees(Notification notification) {
		List<Notification> notificationsBatch = new ArrayList<>();

		employeeRepository.findAll().forEach(employee -> {
			Notification notificationClone = new Notification(notification);
			notificationClone.setUser(employee.getUser());
			notificationsBatch.add(notificationClone);
		});

		System.out.println(notificationsBatch);
		
		notificationRepository.saveAll(notificationsBatch);
	}
	
	/**
	 * Adds a notification to all users with role ADMIN
	 */
	@Override
	public void notifyAllAdmins(Notification notification) {
		List<Notification> notificationsBatch = new ArrayList<>();
		
		userRepository.findByUserType(UserType.ADMIN).forEach(user -> {
			Notification notificationClone = new Notification(notification);
			notificationClone.setUser(user);
			notificationsBatch.add(notificationClone);
		});
		
		System.out.println(notificationsBatch);
		
		notificationRepository.saveAll(notificationsBatch);
	}

	/**
	 * Changes to 'true' the read value of a list of notifications
	 * 
	 * @param notifications
	 */
	private void readNotifications(List<Notification> notifications) {
		List<Notification> notificationsBatch = new ArrayList<>();
		
		notifications.forEach(notification -> {
			notification.setRead(true);
			notificationsBatch.add(notification);
		});
		
		notificationRepository.saveAll(notificationsBatch);
	}

}
