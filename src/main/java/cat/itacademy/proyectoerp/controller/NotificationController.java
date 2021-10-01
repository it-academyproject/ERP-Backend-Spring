package cat.itacademy.proyectoerp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.dto.MessageDTO;
import cat.itacademy.proyectoerp.service.INotificationService;
import cat.itacademy.proyectoerp.service.IUserService;

@RestController
@RequestMapping("/api")
public class NotificationController {

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private IUserService userService;

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
}
