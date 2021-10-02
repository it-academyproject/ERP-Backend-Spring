package cat.itacademy.proyectoerp.util;

import cat.itacademy.proyectoerp.domain.Notification;
import cat.itacademy.proyectoerp.domain.NotificationType;
import cat.itacademy.proyectoerp.domain.Order;

/**
 * This class builds Notification objects.
 * 
 * @author daniel
 *
 */
public class NotificationBuilder {

	private NotificationBuilder() {}
	
	/**
	 * Returns a Notification object with a custom message based on the type and the
	 * object received by parameter
	 */
	public static Notification build(NotificationType type, Object obj) {
		Notification notification = null;

		switch (type) {
			case NEW_ORDER: {
				Order order = (Order) obj;
				if (order.getId() != null) {
					notification = new Notification(type, "New order created. ID: " + order.getId());
				}
				break;
			}
			case ORDER_STATUS_CHANGED: {
				Order order = (Order) obj;
				if (order.getId() != null) {
					notification = new Notification(type,
							"Order ID " + order.getId() + " has changed to status " + order.getStatus());
				}
				break;
			}
			default: {
				System.out.println("NotificationBuilder switch case error.");
			}
		}

		return notification;
	}

}
