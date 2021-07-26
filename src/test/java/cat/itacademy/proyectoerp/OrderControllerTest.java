package cat.itacademy.proyectoerp;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderDetail;
import cat.itacademy.proyectoerp.dto.EmployeeDTO;
import cat.itacademy.proyectoerp.dto.OrderDTO;
import cat.itacademy.proyectoerp.repository.IOrderDetailRepository;
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
	IOrderDetailRepository orderDetailRepository;

	@Resource
	private WebApplicationContext webApplicationContext;

	@BeforeTestExecution
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("Validate endpoint get all orders")
	void givenOrders_whenGetOrders_thenStatus200() throws Exception {
		String accessToken = obtainAccessToken();
		
		this.mockMvc
		.perform(get("/api/orders")
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Validate 204 http response when no orders exist")
	void noOrders_whenGetOrders_thenStatus204() throws Exception {
		// delete all orders - necessary first delete all order details
		orderDetailRepository.deleteAll();
		orderRepository.deleteAll();
		String accessToken = obtainAccessToken();
		
		this.mockMvc
		.perform(get("/api/orders")
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("Validate endpoint get order by id")
	void givenOrderById_whenGetOrderById_thenStatus200() throws Exception {
		Order firstOrder = orderRepository.findAll().get(0);
		UUID id = firstOrder.getId();
		String accessToken = obtainAccessToken();
		
		this.mockMvc
		.perform(get("/api/orders/{id}", id)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Validate bad endpoint get order by id - non existent id")
	void givenEmployeeById_whenEmployeeByIdNotFound_thenStatus204() throws Exception {
		UUID nonExtistentId = UUID.fromString("06d70bed-7424-43ab-954e-385fcd68997a");
		String accessToken = obtainAccessToken();
		
		this.mockMvc
		.perform(get("/api/orders/{id}", nonExtistentId)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}

	private String obtainAccessToken() throws Exception {
		JwtLogin jwtLogin = new JwtLogin("admin@erp.com", "ReW9a0&+TP");

		ResultActions resultPost = this.mockMvc
				.perform(post("/api/login")
						.content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();
		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}
}
