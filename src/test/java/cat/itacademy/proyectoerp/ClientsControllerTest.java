package cat.itacademy.proyectoerp;

import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.repository.IClientRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Transactional // To be able to run all the tests together
public class ClientsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private IClientRepository clientRepository;
	
	// GET /api/clients/{id} (get a client by id)
	@Test
	@DisplayName("Get a client by id")
	void getClientById() throws Exception {
		String accessToken = obtainAccessToken("admin@erp.com", "ReW9a0&+TP");
		
		performGetRequest("/api/clients/11110000-0000-0000-0000-000000000000", "", accessToken)
		.andExpect(status().isOk())
		.andExpect(jsonPath("client.id", is("11110000-0000-0000-0000-000000000000")));
	}
	
	// GET /api/clients/users/{userId} (authorized user)
	@Test
	@DisplayName("Authorized users can get client by user id")
	void getClientByUserIdAuthorized() throws Exception {
		String accessToken = obtainAccessToken("client@erp.com", "ReW9a0&+TP");
		
		performGetRequest("/api/clients/users/3", "", accessToken)
		.andExpect(status().isOk())
		.andExpect(jsonPath("object.user.id", is(3)));
	}
	
	// GET /api/clients/users/{userId} (unauthorized user)
	@Test
	@DisplayName("Unauthorized users cannot get client by user id")
	void getClientByUserIdUnauthorized() throws Exception {
		String accessToken = obtainAccessToken("employee@erp.com", "ReW9a0&+TP");
		
		performGetRequest("/api/clients/users/3", "", accessToken)
		.andExpect(status().isBadRequest());
	}
	
	// PUT /api/clients (update a client)
	@Test
	@DisplayName("Clients can be updated")
	void updateClient() throws Exception {
		UUID clientId = UUID.fromString("11110000-0000-0000-0000-000000000000");
		Client client = clientRepository.findById(clientId).get();
		
		client.setNameAndSurname("John Doe");
		
		String content = new ObjectMapper().writeValueAsString(client);
		String accessToken = obtainAccessToken("admin@erp.com", "ReW9a0&+TP");
		
		performPutRequest("/api/clients", content, accessToken)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("name_and_surname", is("John Doe")));
	}
	
	// DELETE /api/clients (delete a client)
	@Test
	@DisplayName("Clients can be deleted")
	void deleteClient() throws Exception {
		UUID clientId = UUID.fromString("11110000-0000-0000-0000-000000000000");
		Client client = clientRepository.findById(clientId).get();
		String content = new ObjectMapper().writeValueAsString(client);
		String accessToken = obtainAccessToken("admin@erp.com", "ReW9a0&+TP");
		
		performDeleteRequest("/api/clients", content, accessToken)
		.andExpect(status().isOk())
		.andExpect(jsonPath("message", containsStringIgnoringCase("deleted")));
	}
		
	private String obtainAccessToken(String username, String password) throws Exception {
		JwtLogin jwtLogin = new JwtLogin(username, password);

		ResultActions resultPost = this.mockMvc.perform(post("/api/login")
						.content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();
		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}
	
	private ResultActions performPutRequest(String endpoint, String content, String token) throws Exception {
		return mockMvc
			.perform(put(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
	private ResultActions performDeleteRequest(String endpoint, String content, String token) throws Exception {
		return mockMvc
			.perform(delete(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
	private ResultActions performGetRequest(String endpoint, String content, String token) throws Exception {
		return mockMvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
}
