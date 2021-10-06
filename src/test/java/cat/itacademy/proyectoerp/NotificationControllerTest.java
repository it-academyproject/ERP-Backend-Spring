package cat.itacademy.proyectoerp;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

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

import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;
import cat.itacademy.proyectoerp.service.IOrderService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class NotificationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private IOrderService orderService;
	
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
		String accessToken = obtainAccessToken("client@erp.com", "ReW9a0&+TP");
		
		updateOrderStatus("11110000-0000-0000-0000-000000000000"); // generates a notification for the client
		
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
	 * Generates an ORDER_STATUS_CHANGED notification.
	 */
	private void updateOrderStatus(String id) {
		UUID orderId = UUID.fromString(id);
		orderService.updateOrderStatus(orderId, OrderStatus.IN_DELIVERY);
	}
}
