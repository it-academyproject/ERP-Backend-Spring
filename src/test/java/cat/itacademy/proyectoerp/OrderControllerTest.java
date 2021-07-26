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

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.dto.OrderDTO;
import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
classes = ProyectoErpApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class OrderControllerTest {
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	IOrderRepository orderRepository;

	@Resource
	private WebApplicationContext webApplicationContext;


	@BeforeTestExecution
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	@DisplayName("Validate if exists order by id")
	void givenOrderById_whenGetOrderById_thenStatus200() throws Exception{
		UUID id= UUID.fromString("c0c955de-91f4-4787-9d72-996748f99810");
		
		String accessToken = obtainAccessToken();
		ResultActions result =
			this.mockMvc.perform(get("/api/orders/{id}", id)
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		String resultStringTest = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest);	
	}
	
	@Test
	@DisplayName("Validate non existent id order")
	void givenEmployeeById_whenEmployeeByIdNotFound_thenStatus200() throws Exception{
		UUID id= UUID.fromString("06d70bed-7424-43ab-954e-385fcd68997a");
		
		String accessToken = obtainAccessToken();
		ResultActions result =
			this.mockMvc.perform(get("/api/employees/{id}", id)
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().json("{'success' : 'false'}"));
		
		String resultStringTest5 = result.andReturn().getResponse().getContentAsString();
		
		showResult(resultStringTest5);
					
	}
	
	
	
	
	private String obtainAccessToken() throws Exception {
		JwtLogin jwtLogin = new JwtLogin("admin@erp.com", "ReW9a0&+TP");

		ResultActions resultPost =
				this.mockMvc.perform(post("/api/login")
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
