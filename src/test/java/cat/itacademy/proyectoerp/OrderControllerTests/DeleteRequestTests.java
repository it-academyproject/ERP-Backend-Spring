package cat.itacademy.proyectoerp.OrderControllerTests;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.ProyectoErpApplication;
import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProyectoErpApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class DeleteRequestTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	IOrderRepository orderRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	@DisplayName("delete order - success")
	void whenDeleteOrderOK_thenStatus200() throws Exception {
		String accessToken = obtainAccessToken();
		String endPoint = "/api/orders";
		Map<String, UUID> idMap = this.createId("existentId");
		String body = new ObjectMapper().writeValueAsString(idMap);
		
		this.mockMvc.perform(delete(endPoint).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("delete order - ok but error message when null id")
	void givenNullId_whenDeleteOrder_thenStatus200AndErrorMessage() throws Exception {
		String accessToken = obtainAccessToken();
		String endPoint = "/api/orders";
		Map<String, UUID> idMap = this.createId("nullId");
		String body = new ObjectMapper().writeValueAsString(idMap);
		
		this.mockMvc.perform(delete(endPoint).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is( "Error: The given id must not be null!;"
						+ " nested exception is java.lang.IllegalArgumentException: The given id must not be null!")));
	}

	@Test
	@DisplayName("delete order - ok but error message when non existent id")
	void givenNonExistentId_whenDeleteOrder_thenStatus200AndErrorMesage() throws Exception {
		String accessToken = obtainAccessToken();
		String endPoint = "/api/orders";
		Map<String, UUID> idMap = this.createId("nonExistentId");
		String body = new ObjectMapper().writeValueAsString(idMap);
		
		this.mockMvc.perform(delete(endPoint).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", 
						is( "Error: No class cat.itacademy.proyectoerp.domain.Order"
								+ " entity with id 11110000-0000-0000-0000-000000000000 exists!")));
	}
	
	/**
	 * 
	 * @param idType, string to define the id used in each test (existent, null or non-existent)
	 * @return idMap, map "id":id
	 */
	private Map<String, UUID> createId(String idType) {
		Map<String, UUID> idMap = new HashMap<String, UUID>();
		UUID id;
		
		switch (idType) {
		case "existentId":
			id = UUID.fromString("9ebcaeaf-4b2f-48de-8a4e-dafa630965a6");
			idMap.put("id", id);
			break;
		case "nullId":
			idMap.put("id", null);
			break;
		case "nonExistentId":
			id = UUID.fromString("11110000-0000-0000-0000-000000000000");
			idMap.put("id", id);
			break;
		default:
			break;
		}
		
		return idMap;
	}
	
	private String obtainAccessToken() throws Exception {
		String testUsername = "admin@erp.com";
		String testPassword = "ReW9a0&+TP";
		JwtLogin jwtLogin = new JwtLogin(testUsername, testPassword);

		ResultActions resultPost = this.mockMvc.perform(post("/api/login")
				.content(new ObjectMapper().writeValueAsString(jwtLogin))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();
		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}

}
