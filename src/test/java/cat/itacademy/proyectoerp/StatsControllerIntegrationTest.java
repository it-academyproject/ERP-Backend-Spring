package cat.itacademy.proyectoerp;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.repository.UserRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class testing the Stats Endpoints. We load some data directly to the test database to work with it. 
 * And drop the test database after it.
 */

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")

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
	
	@BeforeAll
	@Sql(scripts = "/stats-integration-test-insert-data.sql")
	public static void LoadSqlData() {
			System.out.println("DB omplerta");
	}
	
	@AfterAll
	@Sql(scripts = "/stats-integration-test-clean-up-data.sql")
	public static void cleanUpDB() {
			System.out.println("DB neta");
	}

	
	@Test
	@DisplayName("Entities loaded correctly in DB")
	public void checkSQLData() {
		assertThat(userRepository.existsById((long) 900000001)).isTrue();
		assertThat(orderRepository.existsById(UUID.fromString("11110000-0000-0000-0000-000000000000"))).isTrue();
	}
	
	private String obtainAccessToken() throws Exception {
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

	@Test
	@DisplayName("Correct [GET] /api/stats/employees/sells")
	public void RequestCompletedOrdersByEmployee() throws Exception {

		String accessToken = obtainAccessToken();

		// request
		mockMvc.perform(get("/api/stats/employees/sells")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) 
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// check results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employee[0].name", is("testEmployee01@erp.com")))
				.andExpect(jsonPath("$.employee[0].orders", is("2")))
				.andExpect(jsonPath("$.employee[0].dni", is("C3333333C")))
				.andExpect(jsonPath("$.employee[1].name", is("testEmployee02@erp.com")))
				.andExpect(jsonPath("$.employee[1].orders", is("0")))
				.andExpect(jsonPath("$.employee[1].dni", is("C3333334C")));
	}
	
	@Test
	@DisplayName("Security Check [GET] /api/stats/employees/sells")
	public void SecurityCompletedOrdersByEmployee() throws Exception {

		// request
		mockMvc.perform(get("/api/stats/employees/sells")
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// check results
				.andExpect(status().isUnauthorized());
	}
	
	//TODO GET: /api/stats/employees/toptensales
	@Test
	@DisplayName("Check [GET] /api/stats/employees/toptensales")
	public void RequestTop10EmployeesBySales() throws Exception {

		String accessToken = obtainAccessToken();

		// request
		mockMvc.perform(get("/api/stats/employees/sells")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) 
				.accept(MediaType.APPLICATION_JSON_VALUE))
		
		// check results
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employee[0].name", is("testEmployee01@erp.com")))
				.andExpect(jsonPath("$.employee[0].orders", is("2")))
				.andExpect(jsonPath("$.employee[0].dni", is("C3333333C")))
				.andExpect(jsonPath("$.employee[1].name", is("testEmployee02@erp.com")))
				.andExpect(jsonPath("$.employee[1].orders", is("0")))
				.andExpect(jsonPath("$.employee[1].dni", is("C3333334C")));
	}
	//TODO GET: /api/stats/employees/bestsales
	
	//TODO GET: /api/stats/employees/worstsales
	
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/profits/{year}/
	
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/profits/{year}/{month}
	
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/salaries/year
	
	//TODO [Pendiente Pull Request B-71] GET: /api/stats/salaries/month
}
