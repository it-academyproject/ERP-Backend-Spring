package cat.itacademy.proyectoerp;

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

import cat.itacademy.proyectoerp.security.entity.JwtLogin;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class NotificationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Notifications for anonymous users are unauthorized")
	void notificationsForAnonymousUsers() throws Exception {
		mockMvc.perform(get("/api/notifications"))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	@DisplayName("Authenticated users can get their notifications")
	void notificationsForAuthenticatedUsers() throws Exception {
		String accessToken = obtainAccessToken("client@erp.com", "ReW9a0&+TP");
		
		mockMvc.perform(get("/api/notifications")
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("object").isArray());
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
}
