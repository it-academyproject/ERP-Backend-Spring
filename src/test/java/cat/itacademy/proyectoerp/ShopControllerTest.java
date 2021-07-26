package cat.itacademy.proyectoerp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.domain.Address;
import cat.itacademy.proyectoerp.domain.Shop;
import cat.itacademy.proyectoerp.repository.IShopRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Transactional
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
		UUID id = UUID.fromString("11110000-0000-0000-0000-000000000000");
		
		assertThat(repository.existsById(id));
	}
	
	private String obtainAdminAccessToken() throws Exception {
		String endpoint = "/api/login";
		
		String content = new ObjectMapper().writeValueAsString(new JwtLogin("admin@erp.com", "ReW9a0&+TP"));
		
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
	public void givenShops_whenGetShops_thenStatus200() throws Exception {
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
	public void givenShops_whenGetShopById_thenStatus200() throws Exception {
		String id = "11110000-0000-0000-0000-000000000000";
		String endpoint = "/api/shops/" + id;
		
		mvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("204 No Content GET /api/shops/{id}")
	public void givenShops_whenGetShopById_thenStatus204() throws Exception {
		String id = "33330000-0000-0000-0000-000000000000";
		String endpoint = "/api/shops/" + id;
		
		mvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("400 Bad Request GET /api/shops/{id}")
	public void givenShops_whenGetShopById_thenStatus400() throws Exception {
		String id = "1";
		String endpoint = "/api/shops/" + id;
		
		mvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	// POST /api/shop
	
	@Test
	@DisplayName("200 Ok POST /api/shop")
	public void givenShops_whenPostShop_thenStatus200() throws Exception {
		String endpoint = "/api/shop";
		
		UUID id = UUID.fromString("33330000-0000-0000-0000-000000000000");
		Address address = new Address(id, "City3", "Country3", "3 C", "C/ Ccc", "33333");
		Shop shop = new Shop(id, "Brand3", "Company3", "33333333C", 888888888, address, "www.shop3.com");
		
		String content = new ObjectMapper().writeValueAsString(shop);
		
		mvc
			.perform(post(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isOk());
	}
	
	// PUT /api/shop
	
	@Test
	@DisplayName("200 Ok PUT /api/shop")
	public void givenShops_whenPutShop_thenStatus200() throws Exception {
		String endpoint = "/api/shop";
		
		UUID id = UUID.fromString("22220000-0000-0000-0000-000000000000");
		Address address = new Address(id, "Actualización", "Actualización", "2 B", "C/ Actualización", "22222");
		Shop shop = new Shop(id, "Actualización", "Actualización", "22222222B", 777777777, address, "www.shop2.com");
		
		String content = new ObjectMapper().writeValueAsString(shop);
		
		mvc
			.perform(put(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isOk());
	}
	
	// DELETE /api/shop
	
	@Test
	@DisplayName("200 Ok DELETE /api/shop")
	public void givenShops_whenDeleteShop_thenStatus200() throws Exception {
		String endpoint = "/api/shop";
		
		UUID id = UUID.fromString("22220000-0000-0000-0000-000000000000");
		Address address = new Address(id, "City2", "Country2", "2 B", "C/ Bbb", "22222");
		Shop shop = new Shop(id, "Brand2", "Company2", "22222222B", 777777777, address, "www.shop2.com");
		
		String content = new ObjectMapper().writeValueAsString(shop);
		
		mvc
			.perform(delete(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content))
			.andExpect(status().isOk());
	}

}
