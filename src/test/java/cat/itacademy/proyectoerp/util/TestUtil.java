package cat.itacademy.proyectoerp.util;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.security.entity.JwtLogin;

public class TestUtil {

	private static final String LOGIN_URI = "/api/login";
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	private static final JacksonJsonParser jsonParser = new JacksonJsonParser(objectMapper);

	private static MockHttpServletResponse loginRequest(MockMvc mvc, String username, String password) throws Exception {
		JwtLogin jwtLogin = new JwtLogin(username, password);
		String jsonBody = objectMapper.writeValueAsString(jwtLogin);
		return mvc.perform(post(LOGIN_URI)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
	}
	
	public static String loginAndGetJwtToken(MockMvc mvc, String username, String password) throws Exception {
		MockHttpServletResponse loginResponse = loginRequest(mvc, username, password);
		return jsonParser.parseMap(loginResponse.getContentAsString()).get("token").toString();
	}

}
