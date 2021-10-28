package cat.itacademy.proyectoerp.WorkingHoursControllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
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
import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.repository.IEmployeeRepository;
import cat.itacademy.proyectoerp.repository.IWorkingHoursRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProyectoErpApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")

public class GetWorkingHoursTests {
	
	private String testUsername = "employee@erp.com";

	private String testUserPassword = "ReW9a0&+TP";

	private String testAdminUsername = "admin@erp.com";

	private String testAdminPassword = "ReW9a0&+TP";
	
	private String endPointWorkingHours = "/api/workinghours";
	
	private String endPointWorkingHoursEmployeeId = "/api/workinghours/employeeid/{employeeId}";
	
	private String endPointWorkingHoursDate = "/api/workinghours/date/{date}";
	
	private String endPointWorkingHoursEmployeeIdDate = "/api/workinghours/employeeid/{employeeId}/date/{date}";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	IWorkingHoursRepository workingHoursRepository;

	@Autowired
	IEmployeeRepository employeeRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("get all working hours - success")
	void givenWorkingHours_whenGetWorkingHours_thenStatus200() throws Exception {
		
		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);
						
		this.mockMvc.perform(
				get(endPointWorkingHours).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("get all working hours log as employee - Unauthorized, acces denied")
	void givenWorkingHoursLoginAsEmployee_whenGetWorkingHours_thenStatus401() throws Exception {
		
		String accessToken = obtainAccessToken(testUsername, testUserPassword);
					
		ResultActions resultPost=this.mockMvc.perform(
				get(endPointWorkingHours).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON));
		
		printResult(resultPost);

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		Assertions.assertTrue(resultString
				.contains("Access Denied"));

	}

	@Test
	@DisplayName("get working hours by employeeId - success")
	void givenExistentEmployeeId_whenGetWorkingHoursById_thenStatus200() throws Exception {
		
		Employee firstEmployee = employeeRepository.findAll().get(0);
		
		UUID employeeId = firstEmployee.getId();

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);

		this.mockMvc.perform(get(endPointWorkingHoursEmployeeId, employeeId).header("Authorization", "Bearer " + accessToken)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

	}

	@Test
	@DisplayName("get working hours by employeeId - NoContent when non existent id ")
	void givenNonExistentEmployeeId_whenGetWorkingHoursById_thenStatus204() throws Exception {
		
		UUID nonEmployeeId = UUID.fromString("22220002-0000-0000-0000-000000000000");

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);

		ResultActions resultPost = this.mockMvc.perform(get(endPointWorkingHoursEmployeeId, nonEmployeeId)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON));

		printResult(resultPost);

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		Assertions.assertTrue(resultString
				.contains("No Working Hours found for this Employee Id: 22220002-0000-0000-0000-000000000000"));

	}

	@Test

	@DisplayName("get working hours by employeeId - BadRequest when wrong id format")
	void givenWrongEmployeeIdFormat_whenGetWorkingHoursById_thenStatus400() throws Exception {
		String wrongIdFormat = "pepe&@";

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);

		ResultActions resultPost = this.mockMvc.perform(get(endPointWorkingHoursEmployeeId, wrongIdFormat)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON));

		printResult(resultPost);

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		Assertions.assertTrue(resultString.contains("Method Argument Type Mismatch"));

	}

	@Test

	@DisplayName("get working hours by employeeId - BadRequest when id is space character(s)")
	void givenBlankEmployeeId_whenGetOrderById_thenStatus500() throws Exception {
		
		String wrongIdFormat = "   ";

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);

		ResultActions resultPost = this.mockMvc.perform(get(endPointWorkingHoursEmployeeId, wrongIdFormat)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON));

		printResult(resultPost);

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		Assertions.assertTrue(resultString.contains("INTERNAL_SERVER_ERROR"));

	}

	@Test
	@DisplayName("get working hours by date - succes")
	void givenDate_whenGetWorkingHoursByDate_thenStatus200() throws Exception {

		LocalDate date = LocalDate.parse("2021-01-01");

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);
		
		this.mockMvc.perform(
				get(endPointWorkingHoursDate, date).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	@DisplayName("get working hours by date - BadRequest when wrong date format")
	void givenWrongDateFormat_whenGetWorkingHoursByDate_thenStatus400() throws Exception {

		String date = "2017-03-08 12:30";
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(date, format);

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);

		ResultActions resultPost = this.mockMvc.perform(get(endPointWorkingHoursDate, dateTime)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON));

		printResult(resultPost);

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		Assertions.assertTrue(resultString.contains("Method Argument Type Mismatch"));

	}

	@Test
	@DisplayName("get working hours by date - BadRequest when date is a string")
	void givenDateAsString_whenGetWorkingHoursByDate_thenStatus400() throws Exception {

		String date = "asdff$@";

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);

		ResultActions resultPost = this.mockMvc.perform(get(endPointWorkingHoursDate, date)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON));

		printResult(resultPost);

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		Assertions.assertTrue(resultString.contains("Method Argument Type Mismatch"));

	}

	@Test
	@DisplayName("get working hours by date - BadRequest when date is a blank")
	void givenBlankDate_whenGetWorkingHoursByDate_thenStatus400() throws Exception {

		String date = "   ";

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);

		ResultActions resultPost = this.mockMvc.perform(get(endPointWorkingHoursDate, date)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON));

		printResult(resultPost);

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		Assertions.assertTrue(resultString.contains("Missing Path Variable"));

	}

	@Test
	@DisplayName("get working hours by employeeId and date - success")
	void givenEmployeeIdAndDate_whenGetWorkingHoursByDate_thenStatus200() throws Exception {

		Employee firstEmployee = employeeRepository.findAll().get(0);
		
		UUID employeeId = firstEmployee.getId();

		LocalDate date = LocalDate.parse("2021-01-01");

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);

		String endPoint = "/api/workinghours/employeeid/{employeeId}/date/{date}";

		this.mockMvc.perform(get(endPoint, employeeId, date)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		;

	}

	@Test
	@DisplayName("get working hours by employeeId and date - BadRequest when wrong date format")
	void givenEmployeeIDAndWrongDateFormat_whenGetWorkingHoursByDate_thenStatus400() throws Exception {

Employee firstEmployee = employeeRepository.findAll().get(0);
		
		UUID employeeId = firstEmployee.getId();

		String date = "2017-03-08 12:30";

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		LocalDateTime dateTime = LocalDateTime.parse(date, format);

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);

		String endPoint = "/api/workinghours/employeeid/{employeeId}/date/{date}";

		ResultActions resultPost = this.mockMvc.perform(get(endPoint, employeeId, dateTime)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON));

		printResult(resultPost);

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		Assertions.assertTrue(resultString.contains("Method Argument Type Mismatch"));
	}

	@Test
	@DisplayName("get working hours by employeeId and date - NoContent when non existent employeeId")
	void givenWrongEmployeeIdFromatAndDate_whenGetWorkingHoursByDate_thenStatus204() throws Exception {

		String nonEmployeeId = "asf~€";

		LocalDate date = LocalDate.parse("2021-01-01");

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);

		ResultActions resultPost = this.mockMvc.perform(get(endPointWorkingHoursEmployeeIdDate, nonEmployeeId, date)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON));

		printResult(resultPost);

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		Assertions.assertTrue(resultString.contains("Method Argument Type Mismatch"));

	}

	@Test
	@DisplayName("get working hours by employeeId and date - NotFound when date is a blank")
	void givenExistentEmployeeIDAndBlankDate_whenGetWorkingHoursByDate_thenStatus404() throws Exception {

Employee firstEmployee = employeeRepository.findAll().get(0);
		
		UUID employeeId = firstEmployee.getId();

		String date = "   ";

		String accessToken = obtainAccessToken(testAdminUsername, testAdminPassword);

		ResultActions resultPost = this.mockMvc.perform(get(endPointWorkingHoursEmployeeIdDate, employeeId, date)
				.header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON));

		printResult(resultPost);

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		Assertions.assertTrue(resultString.contains("Missing Path Variable"));

	}

	private String obtainAccessToken(String testUsername, String testPassword) throws Exception {

		JwtLogin jwtLogin = new JwtLogin(testUsername, testPassword);

		ResultActions resultPost = this.mockMvc.perform(post("/api/login")
						.content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}

	private void printResult(ResultActions resultPost) throws UnsupportedEncodingException {
		
		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		System.out.println("===================================");
		System.out.println("output test:");
		System.out.println(resultString);
		System.out.println("===================================");
	}

}
