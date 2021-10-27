package cat.itacademy.proyectoerp.service;

import java.util.List;

import cat.itacademy.proyectoerp.domain.Notification;
import cat.itacademy.proyectoerp.domain.User;

public interface INotificationService {

	List<Notification> getNotificationsByUser(User user);
	void notifyUser(Notification notification, User user);
	void notifyUsers(Notification notification, List<User> users);
	void notifyAllEmployees(Notification notification);
	void notifyAllAdmins(Notification notification);
}
