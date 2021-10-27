package cat.itacademy.proyectoerp.OrderControllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.repository.IClientRepository;
import cat.itacademy.proyectoerp.repository.IEmployeeRepository;
import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.repository.IUserRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProyectoErpApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class GetRequestTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	IOrderRepository orderRepository;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IClientRepository clientRepository;
	
	@Autowired
	IEmployeeRepository employeeRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("get all orders - success")
	void givenOrders_whenGetOrders_thenStatus200() throws Exception {
		String accessToken = obtainAdminAccessToken();
		String endPoint = "/api/orders";
		this.mockMvc.perform(get(endPoint)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("get order by id - success")
	void givenOrderById_whenGetOrderById_thenStatus200() throws Exception {
		Order firstOrder = orderRepository.findAll().get(0);
		UUID id = firstOrder.getId();
		String accessToken = obtainAdminAccessToken();
		String endPoint = "/api/orders/{id}";
		
		this.mockMvc.perform(get(endPoint, id)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("get order by id - NoContent when non existent id ")
	void givenNonExistentId_whenGetOrderById_thenStatus204() throws Exception {
		UUID nonExtistentId = UUID.fromString("06d70bed-7424-43ab-954e-385fcd68997a");
		String accessToken = this.obtainAdminAccessToken();
		String endPoint = "/api/orders/{id}";
		
		this.mockMvc.perform(get(endPoint, nonExtistentId)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("get order by id - BadRequest when wrong id format")
	void givenWrongIdFormat_whenGetOrderById_thenStatus400() throws Exception {
		String wrongIdFormat = "yertueriy";
		String accessToken = this.obtainAdminAccessToken();
		String endPoint = "/api/orders/{id}";
		
		this.mockMvc.perform(get(endPoint, wrongIdFormat)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("get order by id - BadRequest when id is space character(s)")
	void givenBlankId_whenGetOrderById_thenStatus500() throws Exception {
		String wrongIdFormat = "   ";
		String accessToken = this.obtainAdminAccessToken();
		String endPoint = "/api/orders/{id}";
		
		this.mockMvc.perform(get(endPoint, wrongIdFormat)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}
	
	
	@Test
	@DisplayName("get all the orders from a client - Success when placed by the same client")
	void givenClient_whenGetOrdersFromSameClient_thenStatus200() throws Exception {
		String clientUsername = "client@erp.com";
		Client client = clientRepository.findByUserId(userRepository.findByUsername(clientUsername).getId());
		UUID idClient = client.getId();

		String accessToken = this.obtainClientAccessToken(clientUsername);
		String endPoint = "/api/orders/client/{idClient}";
		
		this.mockMvc.perform(get(endPoint, idClient)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	
	@Test
	@DisplayName("get all the orders from a client - Unauthorized when placed by another client")
	void givenClient_whenGetOrdersFromOtherClient_thenStatus401() throws Exception {
		String clientUsername = "testClient@erp.com";
		Client client = clientRepository.findByUserId(userRepository.findByUsername(clientUsername).getId());
		UUID idClient = client.getId();
		
		String accessToken = this.obtainClientAccessToken("client@erp.com");
		String endPoint = "/api/orders/client/{idClient}";
		
		
		this.mockMvc.perform(get(endPoint, idClient)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
	
	
	@Test
	@DisplayName("get all the orders from a client - Unauthorized when placed by an employee")
	void givenClient_whenGetOrdersFromEmployee_thenStatus401() throws Exception {
		String clientUsername = "testClient@erp.com";
		Client client = clientRepository.findByUserId(userRepository.findByUsername(clientUsername).getId());
		UUID idClient = client.getId();
		
		String accessToken = this.obtainEmployeeAccessToken();
		String endPoint = "/api/orders/client/{idClient}";
		
		this.mockMvc.perform(get(endPoint, idClient)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnauthorized());
	}
	
	
	
	@Test
	@DisplayName("get all the orders from a client - Success when placed by an admin")
	void givenClient_whenGetOrdersFromAdmin_thenStatus200() throws Exception {
		String clientUsername = "client@erp.com";
		Client client = clientRepository.findByUserId(userRepository.findByUsername(clientUsername).getId());
		UUID idClient = client.getId();
		
		String accessToken = this.obtainAdminAccessToken();
		String endPoint = "/api/orders/client/{idClient}";
		
		this.mockMvc.perform(get(endPoint, idClient)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	

	@Test
	@DisplayName("get all the orders attended by an employee and the unattended orders - Success")
	void givenEmployee_whenGetOrdersAttached_thenStatus200() throws Exception {
		String employeeUsername = "testEmployee01@erp.com";
		Employee employee = employeeRepository.findByUserId(userRepository.findByUsername(employeeUsername).getId());
		UUID idEmployee = employee.getId();
		
		String accessToken = this.obtainAdminAccessToken();
		String endPoint = "/api/orders/employee/{idEmployee}";
		
		this.mockMvc.perform(get(endPoint, idEmployee)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	
	
	private String obtainAdminAccessToken() throws Exception {
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
	
	
	
	private String obtainEmployeeAccessToken() throws Exception {
		String testUsername = "employee@erp.com";
		String testPassword = "ReW9a0&+TP";
		JwtLogin jwtLogin = new JwtLogin(testUsername, testPassword);
		
		ResultActions resultPost = this.mockMvc.perform(post("/api/login")
				.content(new ObjectMapper().writeValueAsString(jwtLogin))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		String resultString = resultPost.andReturn().getResponse().getContentAsString();
		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}
	
	
	
	private String obtainClientAccessToken(String email) throws Exception {
		String testUsername = email;
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
