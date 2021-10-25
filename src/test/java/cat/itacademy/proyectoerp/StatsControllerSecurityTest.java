package cat.itacademy.proyectoerp;

import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.repository.IUserRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;
import javassist.tools.web.BadHttpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class testing the Security of Stats Endpoints. We load some data directly to the test database to work with it.
 * @Transactional used to load scripts @Sql only one time.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
locations = "classpath:application-integrationtest.properties")
@Transactional
public class StatsControllerSecurityTest {
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
	@DisplayName("Security Admin [GET] /api/stats/employees/sells")
	public void RequestCompletedOrdersByEmployee() throws Exception {
		String accessToken = obtainAdminAccessToken();
		String uri = "/api/stats/employees/sells";
		
		assertThat(isAuthorized(uri,accessToken)).isTrue();
	}
	
	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/employees/sells")
	public void SecurityEmployeeCompletedOrdersByEmployee() throws Exception {
		String accessToken = obtainEmployeeAccessToken();
		String uri = "/api/stats/employees/sells";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
		
	@Test
	@DisplayName("Security Client [GET] /api/stats/employees/sells")
	public void SecurityClientCompletedOrdersByEmployee() throws Exception {		
		String accessToken = obtainClientAccessToken();
		String uri = "/api/stats/employees/sells";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/employees/sells")
	public void SecurityNoAuthCompletedOrdersByEmployee() throws Exception {
		String accessToken = "";
		String uri = "/api/stats/employees/sells";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	// Test /api/stats/employees/toptensales EndPoint	
	@Test
	@DisplayName("Security Admin [GET] /api/stats/employees/toptensales/{year}")
	public void RequestTop10EmployeesBySales() throws Exception {
		String accessToken = obtainAdminAccessToken();
		String uri = "/api/stats/employees/toptensales/{year}";
		/*String json = "{\"begin_date\":\"2021-01-01T00:00:00\","
				+ "\"end_date\":\"2021-12-29T23:59:59\"}";	*/
		
		assertThat(isAuthorized(uri,accessToken/*,json*/)).isTrue();
	}

	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/employees/toptensales/{year}")
	public void SecurityEmployeeTop10EmployeesBySales() throws Exception {
		String accessToken = obtainEmployeeAccessToken();
		String uri = "/api/stats/employees/toptensales/{year}";
		/*String json = "{\"begin_date\":\"2021-01-01T00:00:00\","
				+ "\"end_date\":\"2021-12-29T23:59:59\"}";	*/
		
		assertThat(isAuthorized(uri,accessToken/*,json*/)).isFalse();
	}
	
	@Test
	@DisplayName("Security Client [GET] /api/stats/employees/toptensales/{year}")
	public void SecurityClientTop10EmployeesBySales() throws Exception {
		String accessToken = obtainClientAccessToken();
		String uri = "/api/stats/employees/toptensales/{year}";
		/*String json = "{\"begin_date\":\"2021-01-01T00:00:00\","
				+ "\"end_date\":\"2021-12-29T23:59:59\"}";	*/
		
		assertThat(isAuthorized(uri,accessToken/*,json*/)).isFalse();
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/employees/toptensales/{year}")
	public void SecurityNoAuthTop10EmployeesBySales() throws Exception {
		String accessToken = "";
		String uri = "/api/stats/employees/toptensales/{year}";
		/*String json = "{\"begin_date\":\"2021-01-01T00:00:00\","
				+ "\"end_date\":\"2021-12-29T23:59:59\"}";	*/
		
		assertThat(isAuthorized(uri,accessToken/*,json*/)).isFalse();
	}
		
	//GET: /api/stats/employees/bestsales
	@Test
	@DisplayName("Security Admin [GET] /api/stats/employees/bestsales")
	public void RequestWorseEmployeeBySales() throws Exception {
		String accessToken = obtainAdminAccessToken();
		String uri = "/api/stats/employees/bestsales";
		
		assertThat(isAuthorized(uri,accessToken)).isTrue();
	}
	
	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/employees/bestsales")
	public void SecurityEmployeeWorseEmployeeBySales() throws Exception {		
		String accessToken = obtainEmployeeAccessToken();
		String uri = "/api/stats/employees/bestsales";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
		
	@Test
	@DisplayName("Security Client [GET] /api/stats/employees/bestsales")
	public void SecurityClientWorseEmployeeBySales() throws Exception {	
		String accessToken = obtainClientAccessToken();
		String uri = "/api/stats/employees/bestsales";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/employees/bestsales")
	public void SecurityNoAuthWorseEmployeeBySales() throws Exception {
		String accessToken = "";
		String uri = "/api/stats/employees/bestsales";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	//GET: /api/stats/employees/worstsales
	@Test
	@DisplayName("Security Admin [GET] /api/stats/employees/worstsales")
	public void RequestBestEmployeeBySales() throws Exception {
		String accessToken = obtainAdminAccessToken();
		String uri = "/api/stats/employees/worstsales";
		
		assertThat(isAuthorized(uri,accessToken)).isTrue();
	}
	
	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/employees/worstsales")
	public void SecurityEmployeeBestEmployeeBySales() throws Exception {	
		String accessToken = obtainEmployeeAccessToken();
		String uri = "/api/stats/employees/worstsales";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
		
	@Test
	@DisplayName("Security Client [GET] /api/stats/employees/worstsales")
	public void SecurityClientBestEmployeeBySales() throws Exception {
		String accessToken = obtainClientAccessToken();
		String uri = "/api/stats/employees/worstsales";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/employees/worstsales")
	public void SecurityNoAuthBestEmployeeBySales() throws Exception {
		String accessToken = "";
		String uri = "/api/stats/employees/worstsales";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	//GET: /api/stats/profits/{year}/
	@Test
	@DisplayName("Security Admin [GET] /api/stats/profits/2021")
	public void RequestProfitsYear() throws Exception {
		String accessToken = obtainAdminAccessToken();
		String uri = "/api/stats/profits/2021";
		
		assertThat(isAuthorized(uri,accessToken)).isTrue();
	}
	
	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/profits/2021")
	public void SecurityEmployeeProfitsYear() throws Exception {		
		String accessToken = obtainEmployeeAccessToken();
		String uri = "/api/stats/profits/2021";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
		
	@Test
	@DisplayName("Security Client [GET] /api/stats/profits/2021")
	public void SecurityClientProfitsYear() throws Exception {
		String accessToken = obtainClientAccessToken();
		String uri = "/api/stats/profits/2021";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/profits/2021")
	public void SecurityNoAuthProfitsYear() throws Exception {
		String accessToken = "";
		String uri = "/api/stats/profits/2021";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
		
	//GET: /api/stats/profits/{year}/{month}
	@Test
	@DisplayName("Security Admin [GET] /api/stats/profits/2021/2")
	public void EmptyPeriodProfitsMonth() throws Exception {
		String accessToken = obtainAdminAccessToken();
		String uri = "/api/stats/profits/2021/2";
		
		assertThat(isAuthorized(uri,accessToken)).isTrue();
	}
		
	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/profits/2021/2")
	public void SecurityEmployeeProfitsMonth() throws Exception {
		String accessToken = obtainEmployeeAccessToken();
		String uri = "/api/stats/profits/2021/2";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
		
	@Test
	@DisplayName("Security Client [GET] /api/stats/profits/2021/2")
	public void SecurityClientProfitsMonth() throws Exception {
		String accessToken = obtainClientAccessToken();
		String uri = "/api/stats/profits/2021/2";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/profits/2021/2")
	public void SecurityNoAuthProfitsMonth() throws Exception {
		String accessToken = "";
		String uri = "/api/stats/profits/2021/2";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
		
	//GET: /api/stats/salaries/year
	@Test
	@DisplayName("Security Admin [GET] /api/stats/salaries/year")
	public void RequestSalariesYear() throws Exception {
		String accessToken = obtainAdminAccessToken();
		String uri = "/api/stats/salaries/year";
		
		assertThat(isAuthorized(uri,accessToken)).isTrue();
	}
	
	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/salaries/year")
	public void SecurityEmployeeSalariesYear() throws Exception {
		String accessToken = obtainEmployeeAccessToken();
		String uri = "/api/stats/salaries/year";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
		
	@Test
	@DisplayName("Security Client [GET] /api/stats/salaries/year")
	public void SecurityClientSalariesYear() throws Exception {
		String accessToken = obtainClientAccessToken();
		String uri = "/api/stats/salaries/year";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/salaries/year")
	public void SecurityNoAuthSalariesYear() throws Exception {
		String accessToken = "";
		String uri = "/api/stats/salaries/year";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	//GET: /api/stats/salaries/month
	@Test
	@DisplayName("Security Admin [GET] /api/stats/salaries/month")
	public void RequestSalariesMonth() throws Exception {
		String accessToken = obtainAdminAccessToken();
		String uri = "/api/stats/salaries/month";
		
		assertThat(isAuthorized(uri,accessToken)).isTrue();
	}
	
	@Test
	@DisplayName("Security Employee Auth [GET] /api/stats/salaries/month")
	public void SecurityEmployeeSalariesMonth() throws Exception {
		String accessToken = obtainClientAccessToken();
		String uri = "/api/stats/salaries/month";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
		
	@Test
	@DisplayName("Security Client [GET] /api/stats/salaries/month")
	public void SecurityClientSalariesMonth() throws Exception {
		String accessToken = obtainClientAccessToken();
		String uri = "/api/stats/salaries/month";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	@Test
	@DisplayName("Security NoAuth [GET] /api/stats/salaries/month")
	public void SecurityNoAuthSalariesMonth() throws Exception {
		String accessToken = "";
		String uri = "/api/stats/salaries/month";
		
		assertThat(isAuthorized(uri,accessToken)).isFalse();
	}
	
	
	
	private boolean isAuthorized(String uri,String accessToken) throws Exception {
		int status = mockMvc.perform(get(uri)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse().getStatus();
		
		if(status == 401) return false;
		if(status == 200)return true;
		throw new BadHttpRequest();
	}
		
	private boolean isAuthorized(String uri, String accessToken, String json) throws Exception {
		int status = mockMvc.perform(get(uri)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn().getResponse().getStatus();
		
		if(status == 401) return false;
		return true;
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
