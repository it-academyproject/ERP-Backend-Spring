package cat.itacademy.proyectoerp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonParser;

import cat.itacademy.proyectoerp.controller.WorkingHoursController;
import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.EmployeeDTO;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;
import cat.itacademy.proyectoerp.repository.IWorkingHoursRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.assertj.core.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, 
classes= ProyectoErpApplication.class)
@AutoConfigureMockMvc
//@WebMvcTest(WorkingHoursController.class)
@TestPropertySource(locations ="classpath:application-integrationtest.properties")
public class WorkingHoursControllerIntegrationTest {
	

	protected JsonNode node;
	ModelMapper modelMapper = new ModelMapper();
	@Autowired
	private MockMvc mvc;
	@Autowired
	IWorkingHoursRepository workingHoursRepository;

	@Autowired
	private ObjectMapper objectMapper;
	private JacksonJsonParser jsonParser = new JacksonJsonParser(objectMapper);
	
	@Resource
	private WebApplicationContext webApplicationContext;

	
	@Test
	@DisplayName("Validate Get all working hours")
	public void getAllWorkingHours_thenStatus200() throws Exception{

		String testUsername= "admin@erp.com";
		String testPassword= "ReW9a0&+TP";
		List<WorkingHours> allWorkingHours = workingHoursRepository.findAll();
		List<WorkingHoursDTO> allWorkingHoursDTO= allWorkingHours.stream()
				.map(workingHours -> modelMapper.map(workingHours, WorkingHoursDTO.class))
				.collect(Collectors.toList());
		int lastElement = allWorkingHoursDTO.size()-1;

		String accessToken = obtainAccessToken(testUsername,testPassword);
		ResultActions result =
				mvc.perform(get("/api/workinghours")
						.header("Authorization", "Bearer " + accessToken)
					      .contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("working_hours", hasSize(allWorkingHoursDTO.size())));
		
		String resultStringTest1 = result.andReturn().getResponse().getContentAsString();

		// THEN
		Map<String, Object> map = jsonParser.parseMap(result.andReturn().getResponse().getContentAsString());
		assertAll(
				() -> assertThat(map.get("working_hours")).isNotNull()
				);	
		
	}
	
	@Test
	@DisplayName("Validate Not authorized user")
	void givenEmployees_whenGetEmployees_thenStatus401() throws Exception{
		String testUsername= "employee@erp.com";
		String testPassword= "ReW9a0&+TP";
		
		List<WorkingHours> employees = workingHoursRepository.findAll();
		
		List<WorkingHoursDTO> workingHoursDTO= employees.stream()
				.map(employee -> modelMapper.map(employee, WorkingHoursDTO.class))
				.collect(Collectors.toList());
		
		
		String accessToken = obtainAccessToken(testUsername,testPassword);
		ResultActions result =
			this.mvc.perform(get("/api/workinghours")
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().is4xxClientError());
		
		String resultStringTest3 = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest3);
	}
	
	@Test
	@DisplayName("Validate bad endpoint request all working hours")
	void givenEmployees_whenGetEmployees_thenStatus404() throws Exception{
		String testUsername= "admin@erp.com";
		String testPassword= "ReW9a0&+TP";
		
		List<WorkingHours> employees = workingHoursRepository.findAll();
		
		List<WorkingHoursDTO> workingHoursDTO= employees.stream()
				.map(employee -> modelMapper.map(employee, WorkingHoursDTO.class))
				.collect(Collectors.toList());
		
		String accessToken = obtainAccessToken(testUsername,testPassword);
		ResultActions result =
			this.mvc.perform(get("/api/employee")
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound());
		
		String resultStringTest2 = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest2);
	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
		JwtLogin jwtLogin = new JwtLogin(username, password);

		ResultActions resultPost =
				this.mvc.perform(post("/api/login")
						.content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();
		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}
	
	
	private void showResult(String result) {
		System.out.println("===================================");
		System.out.println("output test:");
		System.out.println(result);
		System.out.println("===================================");
	}


}
