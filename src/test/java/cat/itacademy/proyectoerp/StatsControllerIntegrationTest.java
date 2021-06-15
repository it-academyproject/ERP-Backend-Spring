package cat.itacademy.proyectoerp;

import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.repository.UserRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class testing the Stats Endpoints. We load some data directly to the test database to work with it. 
 * And drop the test database after it.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
locations = "classpath:application-integrationtest.properties")
@Transactional
@Sql(scripts = "classpath:stats-integration-test-insert-data.sql")
public class StatsControllerIntegrationTest {
	/**
	 * @MockBean avoid the execution of real Runner.class as we don't need it's entities. 
	 * Benefits: we win some testing time and avoid loading problems.
	 */
	@MockBean
	Runner runner;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;
	
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
	
	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/employees/sells")
	public void SecurityEmployeeCompletedOrdersByEmployee() throws Exception {
		
		String accessToken = obtainEmployeeAccessToken();
		
		// request
		mockMvc.perform(get("/api/stats/employees/sells")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) 
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isUnauthorized());
	}
	
	
	@Test
	@DisplayName("Security Client [GET] /api/stats/employees/sells")
	public void SecurityClientCompletedOrdersByEmployee() throws Exception {
		
		String accessToken = obtainClientAccessToken();
		
		// request
		mockMvc.perform(get("/api/stats/employees/sells")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) 
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/employees/sells")
	public void SecurityNoAuthCompletedOrdersByEmployee() throws Exception {

		// request
		mockMvc.perform(get("/api/stats/employees/sells")
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isUnauthorized());
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
	
	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/employees/toptensales")
	public void SecurityEmployeeTop10EmployeesBySales() throws Exception {
		
		String accessToken = obtainEmployeeAccessToken();
		String json = "{\"begin_date\":\"2001-01-01T00:00:00\","
				+ "\"end_date\":\"2001-12-29T23:59:59\"}";	
		
		// request
		mockMvc.perform(get("/api/stats/employees/toptensales")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isUnauthorized());
	}
	
	
	@Test
	@DisplayName("Security Client [GET] /api/stats/employees/toptensales")
	public void SecurityClientTop10EmployeesBySales() throws Exception {
		
		String accessToken = obtainClientAccessToken();
		String json = "{\"begin_date\":\"2001-01-01T00:00:00\","
				+ "\"end_date\":\"2001-12-29T23:59:59\"}";	
		
		// request
		mockMvc.perform(get("/api/stats/employees/toptensales")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/employees/toptensales")
	public void SecurityNoAuthTop10EmployeesBySales() throws Exception {

		String json = "{\"begin_date\":\"2001-01-01T00:00:00\","
				+ "\"end_date\":\"2001-12-29T23:59:59\"}";	
		
		// request
		mockMvc.perform(get("/api/stats/employees/toptensales")
				.contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		
		// results
				.andExpect(status().isUnauthorized());
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
	
	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/employees/bestsales")
	public void SecurityEmployeeWorseEmployeeBySales() throws Exception {
		
		String accessToken = obtainEmployeeAccessToken();
		
		// request
		mockMvc.perform(get("/api/stats/employees/bestsales")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isUnauthorized());
	}
		
	@Test
	@DisplayName("Security Client [GET] /api/stats/employees/bestsales")
	public void SecurityClientWorseEmployeeBySales() throws Exception {
		
		String accessToken = obtainClientAccessToken();

		// request
		mockMvc.perform(get("/api/stats/employees/bestsales")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/employees/bestsales")
	public void SecurityNoAuthWorseEmployeeBySales() throws Exception {
		
		// request
		mockMvc.perform(get("/api/stats/employees/bestsales")
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isUnauthorized());
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
	
	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/employees/worstsales")
	public void SecurityEmployeeBestEmployeeBySales() throws Exception {
		
		String accessToken = obtainEmployeeAccessToken();
		
		// request
		mockMvc.perform(get("/api/stats/employees/worstsales")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isUnauthorized());
	}
		
	@Test
	@DisplayName("Security Client [GET] /api/stats/employees/worstsales")
	public void SecurityClientBestEmployeeBySales() throws Exception {
		
		String accessToken = obtainClientAccessToken();

		// request
		mockMvc.perform(get("/api/stats/employees/worstsales")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/employees/worstsales")
	public void SecurityNoAuthBestEmployeeBySales() throws Exception {
		
		// request
		mockMvc.perform(get("/api/stats/employees/worstsales")
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// results
				.andExpect(status().isUnauthorized());
	}
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/profits/{year}/
	
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/profits/{year}/{month}
	
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/salaries/year
	
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/salaries/month
	
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
	
	private String obtainEmployeeAccessToken() throws Exception {
		JwtLogin jwtLogin = new JwtLogin("testEmployee@erp.com", "ReW9a0&+TP");

		ResultActions resultPost =
				this.mockMvc.perform(post("/api/login")
						.content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();
		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}
	
	private String obtainClientAccessToken() throws Exception {
		JwtLogin jwtLogin = new JwtLogin("testClient@erp.com", "ReW9a0&+TP");

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
