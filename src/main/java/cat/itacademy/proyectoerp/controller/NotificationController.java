package cat.itacademy.proyectoerp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Notification;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.dto.MessageDTO;
import cat.itacademy.proyectoerp.service.INotificationService;
import cat.itacademy.proyectoerp.service.IUserService;
import cat.itacademy.proyectoerp.service.NotificationServiceImpl;

@RestController
@RequestMapping("/api")
public class NotificationController {

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private NotificationServiceImpl notificationServiceImpl;

	/**
	 * Returns the notifications of the current authenticated user
	 * 
	 * @return
	 */
	@GetMapping("/notifications")
	public ResponseEntity<?> getNotifications() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = userService.findByUsername(userDetails.getUsername());

		MessageDTO messageDto = new MessageDTO("true", "Notifications list",
				notificationService.getNotificationsByUser(user));

		return ResponseEntity.ok(messageDto);
	}
	
	
	//updates a notification status, changing it to rode
	@PutMapping("/notifications/{notificationId}/rode")
	public ResponseEntity<?> readNotification(@PathVariable UUID notificationId){
		MessageDTO messageDTO = null;
		
		try {
			Notification notificationReaded = notificationServiceImpl.readNotification(notificationId);
			messageDTO = new MessageDTO("true", "Notification read", notificationReaded);
			return ResponseEntity.ok(messageDTO);
		}
		catch (Exception e) {
			messageDTO = new MessageDTO("false", e.getMessage());
			return ResponseEntity.badRequest().body(messageDTO);
		}
	}
}
