package cat.itacademy.proyectoerp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.domain.Address;
import cat.itacademy.proyectoerp.domain.Shop;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
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
	
	private String obtainEmployeeAccessToken() throws Exception {
		String endpoint = "/api/login";
		
		String username = "employee@erp.com", password = "ReW9a0&+TP";
		
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
	
	private String obtainClientAccessToken() throws Exception {
String endpoint = "/api/login";
		
		String username = "client@erp.com", password = "ReW9a0&+TP";
		
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
	
	private ResultActions performGetRequest(String endpoint) throws Exception {
		return mvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions performPostRequest(String endpoint, String content) throws Exception {
		return mvc
			.perform(post(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
	private ResultActions performPutRequest(String endpoint, String content) throws Exception {
		return mvc
			.perform(put(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
	private ResultActions performDeleteRequest(String endpoint, String content) throws Exception {
		return mvc
			.perform(delete(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAdminAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
	//EMPLOYEE REQUESTS
	
	private ResultActions performEmployeeGetRequest(String endpoint) throws Exception {
		return mvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainEmployeeAccessToken())
				.accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions performEmployeePostRequest(String endpoint, String content) throws Exception {
		return mvc
			.perform(post(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainEmployeeAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
	private ResultActions performEmployeePutRequest(String endpoint, String content) throws Exception {
		return mvc
			.perform(put(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainEmployeeAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
	private ResultActions performEmployeeDeleteRequest(String endpoint, String content) throws Exception {
		return mvc
			.perform(delete(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainEmployeeAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
	//CLIENT REQUESTS
	
	private ResultActions performClientGetRequest(String endpoint) throws Exception {
		return mvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainClientAccessToken())
				.accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions performClientPostRequest(String endpoint, String content) throws Exception {
		return mvc
			.perform(post(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainClientAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
	private ResultActions performClientPutRequest(String endpoint, String content) throws Exception {
		return mvc
			.perform(put(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainClientAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
	private ResultActions performClientDeleteRequest(String endpoint, String content) throws Exception {
		return mvc
			.perform(delete(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainClientAccessToken())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));
	}
	
	// GET /api/shops
	
	@Test
	@DisplayName("200 Ok GET /api/shops")
	public void givenGetShops_thenStatus200() throws Exception {
		String endpoint = "/api/shops";
		
		this.performGetRequest(endpoint)
			.andExpect(status().isOk());
	}
	
	// GET /api/shops/{id}
	
	@Test
	@DisplayName("200 Ok GET /api/shops/{id}")
	public void givenGetShopById_thenStatus200() throws Exception {
		String id = "11110000-0000-0000-0000-000000000000";
		String endpoint = "/api/shops/" + id;
		
		this.performGetRequest(endpoint)
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("200 Ok GET /api/shops/{id} ArgumentNotFoundException")
	public void givenGetShopById_whenArgumentNotFoundException_thenStatus200() throws Exception {
		String id = "33330000-0000-0000-0000-000000000000";
		String endpoint = "/api/shops/" + id;
		
		this.performGetRequest(endpoint)
			.andExpect(status().isOk())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof ArgumentNotFoundException))
			.andExpect(result -> assertEquals("Shop not found. The id: " + id + " doesn't exist", result.getResolvedException().getMessage()));
	}
	
	@Test
	@DisplayName("400 Bad Request GET /api/shops/{id} MethodArgumentTypeMismatchException")
	public void givenGetShopById_whenMethodArgumentTypeMismatchException_thenStatus400() throws Exception {
		String id = "1";
		String endpoint = "/api/shops/" + id;
		
		this.performGetRequest(endpoint)
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));
	}
	
	@Test
	@DisplayName("500 Internal Server Error GET /api/shops/{id} MissingPathVariableException")
	public void givenGetShopById_whenMissingPathVariableException_thenStatus500() throws Exception {
		String id = " ";
		String endpoint = "/api/shops/" + id;
		
		this.performGetRequest(endpoint)
			.andExpect(status().isInternalServerError())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingPathVariableException));
	}
	
	// POST /api/shop
	
	@Test
	@DisplayName("200 Ok POST /api/shops")
	public void givenPostShop_thenStatus200() throws Exception {
		String endpoint = "/api/shops";
		
		Address address = new Address("City3", "Country3", "3 C", "C/ Ccc", "33333");
		Shop shop = new Shop("Brand3", "Company3", "33333333C", 888888888, address, "www.shop3.com");
		
		String content = new ObjectMapper().writeValueAsString(shop);
		
		this.performPostRequest(endpoint, content)
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("400 Bad Request POST /api/shops HttpMessageNotReadableException")
	public void givenPostShop_whenHttpMessageNotReadableException_thenStatus400() throws Exception {
		String endpoint = "/api/shops";
		
		String content = new ObjectMapper().writeValueAsString(null);
		
		this.performPostRequest(endpoint, content)
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException));
	}
	
	@Test
	@DisplayName("400 Bad Request POST /api/shops MethodArgumentNotValidException")
	public void givenPostShop_whenMethodArgumentNotValidException_thenStatus400() throws Exception {
		String endpoint = "/api/shops";
		
		Address address = new Address("City3", "Country3", "3 C", "C/ Ccc", "33333");
		Shop shop = new Shop(" ", "Company3", "33333333C", 888888888, address, "www.shop3.com");
		
		String content = new ObjectMapper().writeValueAsString(shop);
		
		this.performPostRequest(endpoint, content)
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)); // Brand name is mandatory
	}
	
	// PUT /api/shop
	
	@Test
	@DisplayName("200 Ok PUT /api/shop")
	public void givenPutShop_thenStatus200() throws Exception {
		String endpoint = "/api/shop";
		
		UUID id = UUID.fromString("22220000-0000-0000-0000-000000000000");
		Address address = new Address(id, "Actualización", "Actualización", "2 B", "C/ Actualización", "22222");
		Shop shop = new Shop(id, "Actualización", "Actualización", "22222222B", 777777777, address, "www.shop2.com");
		
		String content = new ObjectMapper().writeValueAsString(shop);
		
		this.performPutRequest(endpoint, content)
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("400 Bad Request PUT /api/shop HttpMessageNotReadableException")
	public void givenPutShop_whenHttpMessageNotReadableException_thenStatus400() throws Exception {
		String endpoint = "/api/shop";
		
		String content = new ObjectMapper().writeValueAsString(null);
		
		this.performPutRequest(endpoint, content)
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException));
	}
	
	// DELETE /api/shop
	
	@Test
	@DisplayName("200 Ok DELETE /api/shop")
	public void givenDeleteShop_thenStatus200() throws Exception {
		String endpoint = "/api/shop";
		
		UUID id = UUID.fromString("22220000-0000-0000-0000-000000000000");
		Address address = new Address(id, "City2", "Country2", "2 B", "C/ Bbb", "22222");
		Shop shop = new Shop(id, "Brand2", "Company2", "22222222B", 777777777, address, "www.shop2.com");
		
		String content = new ObjectMapper().writeValueAsString(shop);
		
		this.performDeleteRequest(endpoint, content)
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("400 Bad Request DELETE /api/shop HttpMessageNotReadableException")
	public void givenDeleteShop_whenHttpMessageNotReadableException_thenStatus400() throws Exception {
		String endpoint = "/api/shop";
		
		String content = new ObjectMapper().writeValueAsString(null);
		
		this.performDeleteRequest(endpoint, content)
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException));
	}
	
	//EMPLOYEE AUTH
	//GET /api/shops
	
	@Test
	@DisplayName("200 Ok EMPLOYEE GET /api/shops")
	public void givenEmployeeGetShops_thenStatus200() throws Exception {
		String endpoint = "/api/shops";
		
		this.performEmployeeGetRequest(endpoint)
			.andExpect(status().isOk());
	}
	
	// GET /api/shops/{id}
	
	@Test
	@DisplayName("200 Ok EMPLOYEE GET /api/shops/{id}")
	public void givenEmployeeGetShopById_thenStatus200() throws Exception {
		String id = "11110000-0000-0000-0000-000000000000";
		String endpoint = "/api/shops/" + id;
			
		this.performEmployeeGetRequest(endpoint)
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("200 Ok EMPLOYEE GET /api/shops/{id} ArgumentNotFoundException")
	public void givenEmployeeGetShopById_whenArgumentNotFoundException_thenStatus200() throws Exception {
		String id = "33330000-0000-0000-0000-000000000000";
		String endpoint = "/api/shops/" + id;
		
		this.performEmployeeGetRequest(endpoint)
			.andExpect(status().isOk())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof ArgumentNotFoundException))
			.andExpect(result -> assertEquals("Shop not found. The id: " + id + " doesn't exist", result.getResolvedException().getMessage()));;
	}
	
	@Test
	@DisplayName("400 Bad Request EMPLOYEE GET /api/shops/{id} MethodArgumentTypeMismatchException")
	public void givenEmployeeGetShopById_whenMethodArgumentTypeMismatchException_thenStatus400() throws Exception {
		String id = "1";
		String endpoint = "/api/shops/" + id;
		
		this.performEmployeeGetRequest(endpoint)
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));
	}
	
	@Test
	@DisplayName("500 Internal Server Error EMPLOYEE GET /api/shops/{id} MissingPathVariableException")
	public void givenEmployeeGetShopById_whenMissingPathVariableException_thenStatus500() throws Exception {
		String id = " ";
		String endpoint = "/api/shops/" + id;
		
		this.performEmployeeGetRequest(endpoint)
			.andExpect(status().isInternalServerError())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingPathVariableException));
	}
	
	// POST /api/shop
	
	@Test
	@DisplayName("401 Unauthorized EMPLOYEE POST /api/shops")
	public void givenEmployeePostShop_thenStatus403() throws Exception {
		String endpoint = "/api/shops";
			
		Address address = new Address("City3", "Country3", "3 C", "C/ Ccc", "33333");
		Shop shop = new Shop("Brand3", "Company3", "33333333C", 888888888, address, "www.shop3.com");
			
		String content = new ObjectMapper().writeValueAsString(shop);
			
		this.performEmployeePostRequest(endpoint, content)
			.andExpect(status().isUnauthorized());
	}
	
	// PUT /api/shop
	
	@Test
	@DisplayName("401 Unauthorized EMPLOYEE PUT /api/shop")
	public void givenEmployeePutShop_thenStatus403() throws Exception {
		String endpoint = "/api/shop";
			
		UUID id = UUID.fromString("22220000-0000-0000-0000-000000000000");
		Address address = new Address(id, "Actualización", "Actualización", "2 B", "C/ Actualización", "22222");
		Shop shop = new Shop(id, "Actualización", "Actualización", "22222222B", 777777777, address, "www.shop2.com");
			
		String content = new ObjectMapper().writeValueAsString(shop);
			
		this.performEmployeePutRequest(endpoint, content)
			.andExpect(status().isUnauthorized());
		}
	
	// DELETE /api/shop
	
	@Test
	@DisplayName("401 Unauthorized EMPLOYEE DELETE /api/shop")
	public void givenEmployeeDeleteShop_thenStatus403() throws Exception {
		String endpoint = "/api/shop";
		
		UUID id = UUID.fromString("22220000-0000-0000-0000-000000000000");
		Address address = new Address(id, "City2", "Country2", "2 B", "C/ Bbb", "22222");
		Shop shop = new Shop(id, "Brand2", "Company2", "22222222B", 777777777, address, "www.shop2.com");
		
		String content = new ObjectMapper().writeValueAsString(shop);
		
		this.performEmployeeDeleteRequest(endpoint, content)
			.andExpect(status().isUnauthorized());
	}
	
	//CLIENT AUTH
	//GET /api/shops
	
	@Test
	@DisplayName("200 Ok CLIENT GET /api/shops")
	public void givenClientGetShops_thenStatus200() throws Exception {
		String endpoint = "/api/shops";
		
		this.performClientGetRequest(endpoint)
			.andExpect(status().isOk());
	}
	
	// GET /api/shops/{id}
	
	@Test
	@DisplayName("200 Ok CLIENT GET /api/shops/{id}")
	public void givenClientGetShopById_thenStatus200() throws Exception {
		String id = "11110000-0000-0000-0000-000000000000";
		String endpoint = "/api/shops/" + id;
			
		this.performClientGetRequest(endpoint)
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("200 Ok CLIENT GET /api/shops/{id} ArgumentNotFoundException")
	public void givenClientGetShopById_whenArgumentNotFoundException_thenStatus200() throws Exception {
		String id = "33330000-0000-0000-0000-000000000000";
		String endpoint = "/api/shops/" + id;
		
		this.performClientGetRequest(endpoint)
			.andExpect(status().isOk())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof ArgumentNotFoundException))
			.andExpect(result -> assertEquals("Shop not found. The id: " + id + " doesn't exist", result.getResolvedException().getMessage()));;;
	}
	
	@Test
	@DisplayName("400 Bad Request CLIENT GET /api/shops/{id} MethodArgumentTypeMismatchException")
	public void givenClientGetShopById_whenMethodArgumentTypeMismatchException_thenStatus400() throws Exception {
		String id = "1";
		String endpoint = "/api/shops/" + id;
		
		this.performClientGetRequest(endpoint)
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));
	}
	
	@Test
	@DisplayName("500 Internal Server Error CLIENT GET /api/shops/{id} MissingPathVariableException")
	public void givenClientGetShopById_whenMissingPathVariableException_thenStatus500() throws Exception {
		String id = " ";
		String endpoint = "/api/shops/" + id;
		
		this.performClientGetRequest(endpoint)
			.andExpect(status().isInternalServerError())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingPathVariableException));
	}
	
	// POST /api/shop
	
	@Test
	@DisplayName("401 Unauthorized CLIENT POST /api/shops")
	public void givenClientPostShop_thenStatus403() throws Exception {
		String endpoint = "/api/shops";
			
		Address address = new Address("City3", "Country3", "3 C", "C/ Ccc", "33333");
		Shop shop = new Shop("Brand3", "Company3", "33333333C", 888888888, address, "www.shop3.com");
			
		String content = new ObjectMapper().writeValueAsString(shop);
			
		this.performClientPostRequest(endpoint, content)
			.andExpect(status().isUnauthorized());
	}
	
	// PUT /api/shop
	
	@Test
	@DisplayName("401 Unauthorized CLIENT PUT /api/shop")
	public void givenClientPutShop_thenStatus403() throws Exception {
		String endpoint = "/api/shop";
			
		UUID id = UUID.fromString("22220000-0000-0000-0000-000000000000");
		Address address = new Address(id, "Actualización", "Actualización", "2 B", "C/ Actualización", "22222");
		Shop shop = new Shop(id, "Actualización", "Actualización", "22222222B", 777777777, address, "www.shop2.com");
			
		String content = new ObjectMapper().writeValueAsString(shop);
			
		this.performClientPutRequest(endpoint, content)
			.andExpect(status().isUnauthorized());
		}
	
	// DELETE /api/shop
	
	@Test
	@DisplayName("401 Unauthorized CLIENT DELETE /api/shop")
	public void givenClientDeleteShop_thenStatus403() throws Exception {
		String endpoint = "/api/shop";
		
		UUID id = UUID.fromString("22220000-0000-0000-0000-000000000000");
		Address address = new Address(id, "City2", "Country2", "2 B", "C/ Bbb", "22222");
		Shop shop = new Shop(id, "Brand2", "Company2", "22222222B", 777777777, address, "www.shop2.com");
		
		String content = new ObjectMapper().writeValueAsString(shop);
		
		this.performClientDeleteRequest(endpoint, content)
			.andExpect(status().isUnauthorized());
	}
}
