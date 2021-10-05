package cat.itacademy.proyectoerp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class NotificationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Notifications for anonymous users are unauthorized")
	void notificationsForAnonymousUsersReturns401() throws Exception {
		mockMvc.perform(get("/api/notifications"))
		.andExpect(status().isUnauthorized());
	}
	
}