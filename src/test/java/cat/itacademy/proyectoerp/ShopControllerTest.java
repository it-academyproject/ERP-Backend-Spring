package cat.itacademy.proyectoerp;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.repository.IShopRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Transactional
@Sql(scripts = "classpath:data.sql")
public class ShopControllerTest {
	
	@MockBean
	Runner runner;
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private IShopRepository repository;
	
	@Test
	@DisplayName("Data correctly loaded into db")
	public void dataIsLoaded() {
		assertThat(repository.existsById(UUID.fromString("11110000-0000-0000-0000-000000000000")));
	}
	
	private String obtainAdminAccessToken() throws Exception {
		return new JacksonJsonParser().parseMap(mvc
				.perform(post("/api/login")
					.accept(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(new JwtLogin("admin@erp.com", "ReW9a0&+TP")))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString())
			.get("token").toString();
	}
	
	// GET /api/shops
	
	@Test
	@DisplayName("200 Ok GET /api/shops")
	public void givenShops_whenGetShops_thenStatus200() throws Exception {
		mvc
			.perform(get("/api/shops")
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	// GET /api/shops/{id}
	
	@Test
	@DisplayName("200 Ok GET /api/shops/{id}")
	public void givenShops_whenGetShopById_thenStatus200() throws Exception {
		String id = "11110000-0000-0000-0000-000000000000";
		
		mvc
			.perform(get("/api/shops/" + id)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("404 Not Found GET /api/shops/{id}")
	public void givenShops_whenGetShopById_thenStatus404() throws Exception {
		String id = "33330000-0000-0000-0000-000000000000";
		
		mvc
			.perform(get("/api/shops/" + id)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("400 Bad Request GET /api/shops/{id}")
	public void givenShops_whenGetShopById_thenStatus400() throws Exception {
		mvc
			.perform(get("/api/shops/" + 1)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

}
