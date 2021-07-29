package cat.itacademy.proyectoerp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.repository.IShopRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-integrationtest.properties")
@Transactional
public class ShopControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private IShopRepository repository;
	
	@Test
	@DisplayName("Data correctly loaded into db")
	public void testData() {
		UUID id = UUID.fromString("11110000-0000-0000-0000-000000000000");
		
		assertThat(repository.existsById(id));
	}
	
	private String obtainAdminAccessToken() throws Exception {
		String endpoint = "/api/login";
		
		String username = "admin@erp.com", password = "ReW9a0&+TP";
		
		String content = new ObjectMapper().writeValueAsString(new JwtLogin(username, password));
		
		String response = mvc
			.perform(post(endpoint)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		
		return new JacksonJsonParser().parseMap(response).get("token").toString();
	}
	
	// GET /api/shops
	
	@Test
	@DisplayName("200 Ok GET /api/shops")
	public void givenGetShops_thenStatus200() throws Exception {
		String endpoint = "/api/shops";
		
		mvc
			.perform(get(endpoint)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	// GET /api/shops/{id}
	
	@Test
	@DisplayName("200 Ok GET /api/shops/{id}")
	public void givenGetShopById_thenStatus200() throws Exception {
		String id = "11110000-0000-0000-0000-000000000000";
		String endpoint = "/api/shops/" + id;
		
		mvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("200 Ok GET /api/shops/{id} ArgumentNotFoundException")
	public void givenGetShopById_whenArgumentNotFoundException_thenStatus200() throws Exception {
		String id = "33330000-0000-0000-0000-000000000000";
		String endpoint = "/api/shops/" + id;
		
		mvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("400 Bad Request GET /api/shops/{id} MethodArgumentTypeMismatchException")
	public void givenGetShopById_whenMethodArgumentTypeMismatchException_thenStatus400() throws Exception {
		String id = "1";
		String endpoint = "/api/shops/" + id;
		
		mvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("500 Internal Server Error GET /api/shops/{id} MissingPathVariableException")
	public void givenGetShopById_whenMissingPathVariableException_thenStatus500() throws Exception {
		String id = " ";
		String endpoint = "/api/shops/" + id;
		
		mvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError());
	}
	
}
