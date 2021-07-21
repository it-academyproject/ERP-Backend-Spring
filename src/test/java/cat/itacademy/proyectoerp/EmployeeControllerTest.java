package cat.itacademy.proyectoerp;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.dto.EmployeeDTO;
import cat.itacademy.proyectoerp.dto.UserDTO;
import cat.itacademy.proyectoerp.repository.IEmployeeRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hamcrest.Matchers;
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

import javax.annotation.Resource;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
			classes = ProyectoErpApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class EmployeeControllerTest {
	protected JsonNode node;
	ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	IEmployeeRepository employeeRepository;
	
	@Resource
	private WebApplicationContext webApplicationContext;
	
	@BeforeTestExecution
	public void setUp() {
		mockMvc=MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}
	
	@Test
	@DisplayName("Validate endpoint get all employees")
	void givenEmployees_whenGetEmployees_thenStatus200() throws Exception{
		String testUsername= "admin@erp.com";
		String testPassword= "ReW9a0&+TP";
		
		List<Employee> employees = employeeRepository.findAll();
		
		List<EmployeeDTO> employeesDTO= employees.stream()
				.map(employee -> modelMapper.map(employee, EmployeeDTO.class))
				.collect(Collectors.toList());
		int lastElement = employeesDTO.size()-1;
		
		String accessToken = obtainAccessToken(testUsername,testPassword);
		ResultActions result =
			this.mockMvc.perform(get("/api/employees")
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("Employees",hasSize(employeesDTO.size())))
					.andExpect(jsonPath("Employees[2].dni",  is(employeesDTO.get(2).getDni())))
					.andExpect(jsonPath("Employees["+lastElement+"].dni",  is(employeesDTO.get(lastElement).getDni())));

		String resultStringTest1 = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest1);
	}
	
	@Test
	@DisplayName("Validate bad endpoint request all employees")
	void givenEmployees_whenGetEmployees_thenStatus404() throws Exception{
		String testUsername= "admin@erp.com";
		String testPassword= "ReW9a0&+TP";
		
		List<Employee> employees = employeeRepository.findAll();
		
		List<EmployeeDTO> employeesDTO= employees.stream()
				.map(employee -> modelMapper.map(employee, EmployeeDTO.class))
				.collect(Collectors.toList());
		
		
		String accessToken = obtainAccessToken(testUsername,testPassword);
		ResultActions result =
			this.mockMvc.perform(get("/api/employee")
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound());
		
		String resultStringTest2 = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest2);
	}
	
	@Test
	@DisplayName("Validate Not authorized user")
	void givenEmployees_whenGetEmployees_thenStatus401() throws Exception{
		String testUsername= "employee@erp.com";
		String testPassword= "ReW9a0&+TP";
		
		List<Employee> employees = employeeRepository.findAll();
		
		List<EmployeeDTO> employeesDTO= employees.stream()
				.map(employee -> modelMapper.map(employee, EmployeeDTO.class))
				.collect(Collectors.toList());
		
		
		String accessToken = obtainAccessToken(testUsername,testPassword);
		ResultActions result =
			this.mockMvc.perform(get("/api/employee")
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().is4xxClientError());
		
		String resultStringTest3 = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest3);
	}
	
	@Test
	@DisplayName("Validate exist employee ")
	void givenEmployeeById_whenGetEmployeeById_thenStatus200() throws Exception{
		String testUsername= "admin@erp.com";
		String testPassword= "ReW9a0&+TP";
		
		UUID id= UUID.fromString("06d70bed-7424-43ab-954e-385fcd68997e");
		
		//Optional<Employee> employee = employeeRepository.findById(id);
		
		
		String accessToken = obtainAccessToken(testUsername,testPassword);
		ResultActions result =
			this.mockMvc.perform(get("/api/employees/{id}", id)
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		String resultStringTest4 = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest4);	
	}
	
	@Test
	@DisplayName("Validate if not exist Employee")
	void givenEmployeeById_whenEmployeeByIdNotFound_thenStatus200() throws Exception{
		String testUsername= "admin@erp.com";
		String testPassword= "ReW9a0&+TP";
		
		UUID id= UUID.fromString("06d70bed-7424-43ab-954e-385fcd68997a");
		
		//Employee employee = employeeRepository.findById(id);
		
		
		String accessToken = obtainAccessToken(testUsername,testPassword);
		ResultActions result =
			this.mockMvc.perform(get("/api/employees/{id}", id)
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().json("{'success' : 'false'}"));
		
		String resultStringTest5 = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest5);
					
	}
	
	@Test
	@DisplayName("Validate endpoint delete employee requestbody")
	void deleteEmployee_whendeleteEmployee_thenStatus200() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode request=null;
		EmployeeDTO employeeDto = new EmployeeDTO();
		request = objectMapper.createObjectNode();
		
		
		
		String testUsername= "admin@erp.com";
		String testPassword= "ReW9a0&+TP";

		UUID id= UUID.fromString("06d70bed-7424-43ab-954e-385fcd68997e");
		
		Optional<Employee> employee = employeeRepository.findById(id);
		UserDTO userdto= modelMapper.map(employee.get().getUser(), UserDTO.class);
		
		employeeDto.setId(employee.get().getId());
		employeeDto.setDni(employee.get().getDni());
		employeeDto.setSalary(employee.get().getSalary());
		employeeDto.setPhone(employee.get().getPhone());
		employeeDto.setInDate(employee.get().getInDate());
		employeeDto.setOutDate(null);
		employeeDto.setUser(userdto);
		 
		
		
		 request.set("request",objectMapper.convertValue(employeeDto, ObjectNode.class));
		//String theJsonText = objectMapper.writeValueAsString(employee);
		
		//JsonNode node = objectMapper.readTree(theJsonText);
		
				
		
		
		String accessToken = obtainAccessToken(testUsername,testPassword);
		ResultActions result =
			this.mockMvc.perform(delete("/api/employees/")
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON)
					.content(request.binaryValue()))
					.andExpect(status().isOk())
					.andExpect(content().json("{'success' : 'true'}"));
		
		String resultStringTest6 = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest6);
	}
	
	@Test
	@DisplayName("Validate endpoint delete employee not exist")
	void deleteEmployee_whenNotExistEmployee_thenStatus200() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		String testUsername= "admin@erp.com";
		String testPassword= "ReW9a0&+TP";
		

		UUID id= UUID.fromString("06d70bed-7424-43ab-954e-385fcd68997a");
		
		Optional<Employee> employee = employeeRepository.findById(id);
		
		String theJsonText = objectMapper.writeValueAsString(employee);
		
		JsonNode node = objectMapper.readTree(theJsonText);
		System.out.println(node);	
				
		
		
		String accessToken = obtainAccessToken(testUsername,testPassword);
		ResultActions result =
			this.mockMvc.perform(delete("/api/employees/")
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_FORM_URLENCODED)
					.content(node.binaryValue()))
					.andExpect(status().isOk())
					.andExpect(content().json("{'success' : 'false'}"));
		
		String resultStringTest7 = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest7);
		
	}
	
	@Test
	@DisplayName("Validate endpoint update employee")
	void updateEmployee_whenUpdateEmployee_thenStatus200() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		String testUsername= "admin@erp.com";
		String testPassword= "ReW9a0&+TP";
		

		UUID id= UUID.fromString("06d70bed-7424-43ab-954e-385fcd68997e");
		
		Optional<Employee> employee = employeeRepository.findById(id);
		employee.get().setDni("Y6549681Z");
		String theJsonText = objectMapper.writeValueAsString(employee);
		
		JsonNode node = objectMapper.readTree(theJsonText);
		System.out.println(node);	
				
		
		
		String accessToken = obtainAccessToken(testUsername,testPassword);
		ResultActions result =
			this.mockMvc.perform(put("/api/employees/")
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_FORM_URLENCODED)
					.content(node.binaryValue()))
					.andExpect(status().isOk())
					.andExpect(content().json("{'success' : 'true'}"));
		
		String resultStringTest7 = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest7);
	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
		JwtLogin jwtLogin = new JwtLogin(username, password);

		ResultActions resultPost =
				this.mockMvc.perform(post("/api/login")
						.content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();
		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}
	
	private Employee createNewEmployee() {
		User useremployee= new  User( "employee@erptest", "ReW9a0&+TP", UserType.EMPLOYEE);
		Employee employee = new Employee("John", "Doe", (double) 22000, "a8762135z", 555333222, LocalDate.now(), null, useremployee);
		
		//if (employeeRepository.exists(employee.getDni()))		
		employeeRepository.save( employee);
		return employee;
	}
	
	private void showResult(String result) {
		System.out.println("===================================");
		System.out.println("output test:");
		System.out.println(result);
		System.out.println("===================================");
	}
	
	
}

