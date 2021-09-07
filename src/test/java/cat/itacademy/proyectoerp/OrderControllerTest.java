package cat.itacademy.proyectoerp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.domain.Address;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.PaymentMethod;
import cat.itacademy.proyectoerp.dto.CreateOrderDTO;
import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProyectoErpApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class OrderControllerTest {

	ModelMapper modelMapper = new ModelMapper();

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
	@DisplayName("Validate endpoint get all orders")
	void givenOrders_whenGetOrders_thenStatus200() throws Exception {
		String accessToken = obtainAccessToken();

		String endPoint = "/api/orders";
		this.mockMvc.perform(
				get(endPoint).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Validate endpoint get order by id")
	void givenOrderById_whenGetOrderById_thenStatus200() throws Exception {
		Order firstOrder = orderRepository.findAll().get(0);
		UUID id = firstOrder.getId();
		String accessToken = obtainAccessToken();

		String endPoint = "/api/orders/{id}";
		this.mockMvc.perform(
				get(endPoint, id).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Validate get order by id - non existent id")
	void givenOrderById_whenOrderByIdNotFound_thenStatus204() throws Exception {
		UUID nonExtistentId = UUID.fromString("06d70bed-7424-43ab-954e-385fcd68997a");
		String accessToken = obtainAccessToken();

		String endPoint = "/api/orders/{id}";
		this.mockMvc.perform(get(endPoint, nonExtistentId).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("Validate bad endpoint get order by id - wrong id format")
	void givenOrderById_whenOrderIdNotFound_thenStatus400() throws Exception {
		String wrongIdFormat = "yertueriy";
		String accessToken = obtainAccessToken();

		String endPoint = "/api/orders/{id}";
		this.mockMvc.perform(get(endPoint, wrongIdFormat).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Validate endpoint create order")
	void givenCreatedOrder_thenStatus200() throws Exception {
		String accessToken = obtainAccessToken();
		String endPoint = "/api/orders";
		CreateOrderDTO newOrder = createValidOrder();
		String body = new ObjectMapper().writeValueAsString(newOrder);

		this.mockMvc
				.perform(post(endPoint).header("Authorization", "Bearer " + accessToken)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Bad Request when CREATE order with empty billing address")
	void givenEmptyAddressThenStatusOkAndErrorMessage() throws Exception {
		String accessToken = obtainAccessToken();
		String endPoint = "/api/orders";
		CreateOrderDTO invalidOrder = createInvalidOrder("billingAddress");
		String body = new ObjectMapper().writeValueAsString(invalidOrder);

		this.mockMvc.perform(post(endPoint)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", 
							is( "Error: Could not commit JPA transaction;"
								+ " nested exception is javax.persistence.RollbackException: Error while committing the transaction")));

	}
	
	private CreateOrderDTO createInvalidOrder(String field) {
		// valid order so far
		PaymentMethod paymentMethod = PaymentMethod.CASH;
		Map<Integer, Integer> productsQuantity = new HashMap<>();
		productsQuantity.put(5, 5);
		Address billingAddress = new Address("Carrer Nou", "45", "Barna", "Spain", "08023");

		// specify the wrong field of the order
		switch (field) {
		case "productsQuantity":
			paymentMethod = PaymentMethod.CASH;
			productsQuantity = null;
			break;
		case "billingAddress":
			paymentMethod = PaymentMethod.CASH;
			billingAddress.setStreet("");
			break;
			
		default:
			break;
		}

		CreateOrderDTO invalidOrder = new CreateOrderDTO();
		invalidOrder.setPaymentMethod(paymentMethod);
		invalidOrder.setProductsQuantity(productsQuantity);
		invalidOrder.setBillingAddress(billingAddress);
		System.out.println(invalidOrder.getPaymentMethod());
		return invalidOrder;
	}


	@Test
	@DisplayName("Validate endpoint delete order")
	void givenDeletedOrder_thenStatus200() throws Exception {
		String accessToken = obtainAccessToken();
		String endPoint = "/api/orders";
		CreateOrderDTO newOrder = createValidOrder();
		String body = new ObjectMapper().writeValueAsString(newOrder);

		this.mockMvc
				.perform(delete(endPoint).header("Authorization", "Bearer " + accessToken)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isOk());
	}

	private CreateOrderDTO createValidOrder() {
		PaymentMethod paymentMethod = PaymentMethod.CASH;
		Map<Integer, Integer> productsQuantity = new HashMap<>();
		productsQuantity.put(5, 5);
		Address billingAddress = new Address("Rocafort", "45", "Barna", "Spain", "08023");

		CreateOrderDTO newOrder = new CreateOrderDTO();
		newOrder.setPaymentMethod(paymentMethod);
		newOrder.setProductsQuantity(productsQuantity);
		newOrder.setBillingAddress(billingAddress);

		return newOrder;
	}

	private String obtainAccessToken() throws Exception {
		String testUsername = "admin@erp.com";
		String testPassword = "ReW9a0&+TP";
		JwtLogin jwtLogin = new JwtLogin(testUsername, testPassword);

		ResultActions resultPost = this.mockMvc
				.perform(post("/api/login").content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();
		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}
}
