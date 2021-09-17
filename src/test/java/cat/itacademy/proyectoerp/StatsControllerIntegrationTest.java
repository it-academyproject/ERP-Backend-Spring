package cat.itacademy.proyectoerp;

import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.repository.IUserRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class testing the Integration of Stats Endpoints. We load some data directly to the test database to work with it.
 * @Transactional used to load scripts @Sql only one time.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
locations = "classpath:application-integrationtest.properties")
@Transactional
public class StatsControllerIntegrationTest {
	/**
	 * @MockBean avoid the execution of real Runner.class as we don't need it's entities. 
	 * Benefits: we win some testing time and avoid loading problems or bad testing results.
	 */
		
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IOrderRepository orderRepository;
	
	// Test initial Data from sql is correctly loaded.	
	@Test
	@DisplayName("Entities loaded correctly in DB")
	public void checkSQLData() {
		assertThat(userRepository.existsById((long) 91)).isTrue();
		assertThat(orderRepository.existsById(UUID.fromString("11110000-0000-0000-0000-000000000000"))).isTrue();
	}
	
	// Test /api/stats/employees/sells EndPoint
	@Test
	@DisplayName("Correct [GET] /api/stats/employees/sells")
	public void RequestCompletedOrdersByEmployee() throws Exception {

		String accessToken = obtainAdminAccessToken();

		// request
		mockMvc.perform(get("/api/stats/employees/sells")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) 
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employee[0].name", is("testEmployee01@erp.com")))
				.andExpect(jsonPath("$.employee[0].orders", is("2")))
				.andExpect(jsonPath("$.employee[0].dni", is("C3333333C")))
				.andExpect(jsonPath("$.employee[1].name", is("testEmployee02@erp.com")))
				.andExpect(jsonPath("$.employee[1].orders", is("1")))
				.andExpect(jsonPath("$.employee[1].dni", is("C3333334C")));
	}

	// Test /api/stats/employees/toptensales EndPoint	
	@Test
	@DisplayName("Correct [GET] /api/stats/employees/toptensales")
	public void RequestTop10EmployeesBySales() throws Exception {

		String accessToken = obtainAdminAccessToken();
		String json = "{\"begin_date\":\"2021-01-01T00:00:00\","
				+ "\"end_date\":\"2021-12-29T23:59:59\"}";		
			
		// request
		mockMvc.perform(get("/api/stats/employees/toptensales")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employees[0].id", is("11110000-0000-0000-0000-000000000000")))
				.andExpect(jsonPath("$.employees[0].total", is(5000.0)));
	}
	
	@Test
	@DisplayName("Empty Order Period [GET] /api/stats/employees/toptensales")
	public void EmptyPeriodTop10EmployeesBySales() throws Exception {

		String accessToken = obtainAdminAccessToken();
		String json = "{\"begin_date\":\"2001-01-01T00:00:00\","
				+ "\"end_date\":\"2001-12-29T23:59:59\"}";		
			
		// request
		mockMvc.perform(get("/api/stats/employees/toptensales")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("no employees or orders found between the dates")));
	}
	
	@Test
	@DisplayName("BadRequest [GET] /api/stats/employees/toptensales")
	public void BadRequestTop10EmployeesBySales() throws Exception {

		String accessToken = obtainAdminAccessToken();	
			
		// request
		mockMvc.perform(get("/api/stats/employees/toptensales")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("BAD_REQUEST")));
	}
	
	//GET: /api/stats/employees/bestsales
	@Test
	@DisplayName("Correct [GET] /api/stats/employees/bestsales")
	public void RequestWorseEmployeeBySales() throws Exception {

		String accessToken = obtainAdminAccessToken();
			
		// request
		mockMvc.perform(get("/api/stats/employees/bestsales")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employee.employee.id", is("11110000-0000-0000-0000-000000000000")))
				.andExpect(jsonPath("$.employee.employee.user.username", is("testEmployee01@erp.com")))
				.andExpect(jsonPath("$.employee.total_sales", is(5000.0)));
	}
	
	//GET: /api/stats/employees/worstsales
	@Test
	@DisplayName("Correct [GET] /api/stats/employees/worstsales")
	public void RequestBestEmployeeBySales() throws Exception {

		String accessToken = obtainAdminAccessToken();
			
		// request
		mockMvc.perform(get("/api/stats/employees/worstsales")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employee.employee.id", is("22220000-0000-0000-0000-000000000000")))
				.andExpect(jsonPath("$.employee.employee.user.username", is("testEmployee02@erp.com")))
				.andExpect(jsonPath("$.employee.total_sales", is(2000.0)));
	}
	
	//GET: /api/stats/profits/{year}/
	@Test
	@DisplayName("Correct [GET] /api/stats/profits/2021")
	public void RequestProfitsYear() throws Exception {

		String accessToken = obtainAdminAccessToken();
			
		// request
		mockMvc.perform(get("/api/stats/profits/2021")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.year", is(2021)))
				.andExpect(jsonPath("$.profit", is(7000.0)));
	}
	
	@Test
	@DisplayName("Empty Order Period [GET] /api/stats/profits/2019")
	public void EmptyPeriodProfitsYear() throws Exception {

		String accessToken = obtainAdminAccessToken();
			
		// request
		mockMvc.perform(get("/api/stats/profits/2019")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("error: There are no completed orders for year 2019")));
	}
	
	//GET: /api/stats/profits/{year}/{month}
	@Test
	@DisplayName("Correct [GET] /api/stats/profits/2021/2")
	public void RequestProfitsMonth() throws Exception {

		String accessToken = obtainAdminAccessToken();
			
		// request
		mockMvc.perform(get("/api/stats/profits/2021/2")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.year", is(2021)))
				.andExpect(jsonPath("$.month", is(2)))
				.andExpect(jsonPath("$.profit", is(7000.0)));
	}
	
	@Test
	@DisplayName("Empty Order Period [GET] /api/stats/profits/2021/6")
	public void EmptyPeriodProfitsMonth() throws Exception {

		String accessToken = obtainAdminAccessToken();
			
		// request
		mockMvc.perform(get("/api/stats/profits/2021/6")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", is("false")))
				.andExpect(jsonPath("$.message", is("error: There are no completed orders for junio 2021")));
	}
	
	@Test
	@DisplayName("Bad Request Period [GET] /api/stats/profits/XXXX/6")
	public void BadRequestPeriodProfitsMonth() throws Exception {

		String accessToken = obtainAdminAccessToken();
			
		// request
		mockMvc.perform(get("/api/stats/profits/XXXX/6")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isBadRequest());
	}

	//GET: /api/stats/salaries/year
	@Test
	@DisplayName("Correct [GET] /api/stats/salaries/year")
	public void RequestSalariesYear() throws Exception {

		String accessToken = obtainAdminAccessToken();
			
		// request
		mockMvc.perform(get("/api/stats/salaries/year")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message",is("Total salaries for a year found")))
				.andExpect(jsonPath("$.salaries", is(50000.0)));
	}

	//GET: /api/stats/salaries/month
	@Test
	@DisplayName("Correct [GET] /api/stats/salaries/month")
	public void RequestSalariesMonth() throws Exception {

		String accessToken = obtainAdminAccessToken();
			
		// request
		mockMvc.perform(get("/api/stats/salaries/month")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message",is("Total salaries for a month found")))
				.andExpect(jsonPath("$.salaries", is(4166.6666666666667)));
	}
	
	@Test
	@DisplayName("Correct [GET] /api/stats/count/{status}")
	public void RequestOrdersByStatus() throws Exception {

		String accessToken = obtainAdminAccessToken();
			
		// request
		mockMvc.perform(get("/api/stats/count/status")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", is("true")))
				.andExpect(jsonPath("$.message", is("order list found")))
				.andExpect(jsonPath("$.orders.completed", is(3)))
				.andExpect(jsonPath("$.orders.assigned", is(0)))
				.andExpect(jsonPath("$.message", is("order list found")));
	}
	
	@Test
	@DisplayName("Correct [GET] /api/stats/count/{payment}")
	public void RequestOrdersByPayment() throws Exception {

		String accessToken = obtainAdminAccessToken();
			
		// request
		mockMvc.perform(get("/api/stats/count/payment")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success", is("true")))
				.andExpect(jsonPath("$.message", is("order list found")))
				.andExpect(jsonPath("$.orders.cash", is(3)))
				.andExpect(jsonPath("$.orders.credit_card", is(0)))
				.andExpect(jsonPath("$.message", is("order list found")));
	}

	private String obtainAdminAccessToken() throws Exception {
		JwtLogin jwtLogin = new JwtLogin("testAdmin@erp.com", "ReW9a0&+TP");

		ResultActions resultPost =
				this.mockMvc.perform(post("/api/login")
						.content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();
		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}
}
