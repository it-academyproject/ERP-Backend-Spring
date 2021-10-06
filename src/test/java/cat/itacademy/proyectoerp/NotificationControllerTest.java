package cat.itacademy.proyectoerp;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.domain.Notification;
import cat.itacademy.proyectoerp.domain.NotificationType;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;
import cat.itacademy.proyectoerp.service.INotificationService;
import cat.itacademy.proyectoerp.service.IUserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class NotificationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private INotificationService notificationService;
	
	@Autowired
	private IUserService userService;
	
	// GET /api/notifications endpoint (as anonymous user)
	@Test
	@DisplayName("Notifications for anonymous users are unauthorized")
	void notificationsForAnonymousUsers() throws Exception {
		mockMvc.perform(get("/api/notifications"))
		.andExpect(status().isUnauthorized());
	}
	
	// GET /api/notifications endpoint (as authenticated user)
	@Test
	@DisplayName("Authenticated users can get their own notifications")
	void notificationsForAuthenticatedUsers() throws Exception {
		User user = userService.findByUsername("client@erp.com");
		
		generateNotification(user);
		
		String accessToken = obtainAccessToken("client@erp.com", "ReW9a0&+TP");
		
		mockMvc.perform(get("/api/notifications")
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("object").isArray())
		.andExpect(jsonPath("object[0].user.username", is("client@erp.com")));
	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
		JwtLogin jwtLogin = new JwtLogin(username, password);

		ResultActions resultPost =
				this.mockMvc.perform(post("/api/login")
						.content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();
		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}
	
	/**
	 * Generates a notification for a given user
	 */
	private void generateNotification(User user) {
		Notification notification = new Notification(NotificationType.ORDER_STATUS_CHANGED, "This is a test");
		notificationService.notifyUser(notification, user);
	}
}
