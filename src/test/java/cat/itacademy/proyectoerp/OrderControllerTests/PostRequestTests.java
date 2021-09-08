package cat.itacademy.proyectoerp.OrderControllerTests;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

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
import cat.itacademy.proyectoerp.domain.Address;
import cat.itacademy.proyectoerp.domain.PaymentMethod;
import cat.itacademy.proyectoerp.dto.CreateOrderDTO;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProyectoErpApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class PostRequestTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
		
	@Test
	@DisplayName("create order - success")
	void whenCreatedOrderOK_thenStatus200() throws Exception {
		String accessToken = obtainAccessToken();
		String endPoint = "/api/orders";
		CreateOrderDTO newOrder = createOrder("");
		String body = new ObjectMapper().writeValueAsString(newOrder);

		this.mockMvc.perform(post(endPoint).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("create order - ok but error message when null billing address")
	void givenNullBillingAddresss_whenCreateOrder_thenStatus200AndErrorMessage() throws Exception {
		String accessToken = obtainAccessToken();
		String endPoint = "/api/orders";
		CreateOrderDTO newOrder = createOrder("noBillingAddress");
		String body = new ObjectMapper().writeValueAsString(newOrder);
		
		this.mockMvc.perform(post(endPoint).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is( "Error:"
						+ " A billing address is necessary to process a new order from an unregistered client")));
	}

	@Test
	@DisplayName("create order - ok but error message when null field in any address")
	void givenNullFieldInAddress_whenCreateOrder_thenStatus200AndErrorMessage() throws Exception {
		String accessToken = obtainAccessToken();
		String endPoint = "/api/orders";
		CreateOrderDTO newOrder = createOrder("emptyStreet"); // the same for any other field in any address, billing or shipping
		String body = new ObjectMapper().writeValueAsString(newOrder);

		this.mockMvc.perform(post(endPoint).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is( "Error: Could not commit JPA transaction;"
								+ " nested exception is javax.persistence.RollbackException: Error while committing the transaction")));
	}

	@Test
	@DisplayName("create order - ok but error message when null products quantity")
	void givenNullProductQuantity_whenCreateOrder_thenStatus200AndErrorMessage() throws Exception {
		String accessToken = obtainAccessToken();
		String endPoint = "/api/orders";
		CreateOrderDTO newOrder = createOrder("noProductsQuantity");
		String body = new ObjectMapper().writeValueAsString(newOrder);
		
		this.mockMvc.perform(post(endPoint).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", 
						is( "Error: Cannot invoke \"java.util.Map.entrySet()\" because \"productQuantity\" is null")));
	}

	@Test
	@DisplayName("create order - ok but error message when non existent product")
	void givenNonExistentProduct_whenCreateOrder_thenStatus200AndErrorMesage() throws Exception {
		String accessToken = obtainAccessToken();
		String endPoint = "/api/orders";
		CreateOrderDTO newOrder = createOrder("nonExistentProduct");
		String body = new ObjectMapper().writeValueAsString(newOrder);
		
		this.mockMvc.perform(post(endPoint).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", 
						is( "Error: Product not found. The id 456 doesn't exist")));
	}
	
	
	/**
	 * 
	 * @param errorCase, defines what causes the error. "" for no error
	 * @return a CreateOrderDTO object, dependent on the errorCase called  
	 */
	private CreateOrderDTO createOrder(String errorCase) {
		PaymentMethod paymentMethod = PaymentMethod.CASH;
		Map<Integer, Integer> productsQuantity = new HashMap<>();
		productsQuantity.put(2, 5);
		Address billingAddress = new Address("Rocafort", "45", "Barna", "Spain", "08023");

		switch (errorCase) {
		case "noBillingAddress":
			billingAddress = null;
			break;			
		case "emptyStreet":
			billingAddress.setStreet(null);
			break;
		case "noProductsQuantity":
			productsQuantity = null;
			break;
		case "nonExistentProduct":
			productsQuantity.remove(5);
			productsQuantity.put(456, 5);
			break;
		default:
			break;
		}
		
		CreateOrderDTO newOrder = new CreateOrderDTO();
		newOrder.setPaymentMethod(paymentMethod);
		newOrder.setProductsQuantity(productsQuantity);
		newOrder.setBillingAddress(billingAddress);

		return newOrder;
	}
	
	
	private String obtainAccessToken() throws Exception {
		String testUsername = "client@erp.com";
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
