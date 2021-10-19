package cat.itacademy.proyectoerp;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.dto.UserDTO;
import cat.itacademy.proyectoerp.repository.IUserRepository;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;
import cat.itacademy.proyectoerp.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = ProyectoErpApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
public class UserControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	IUserRepository userRepository;

	@Resource
	private WebApplicationContext webApplicationContext;


	@BeforeTestExecution
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	void givenListAllUsers_whenGetUsers_thenStatus200() throws Exception {
		ModelMapper modelMapper = new ModelMapper();

		List<User> allUsers = userRepository.findAll();

		List<UserDTO> allUsersDTO = allUsers.stream()
				.map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
		int lastElement = allUsersDTO.size()-1;

		//assertEquals(allUsers.size(),allUsersDTO.size());
		String accessToken = obtainAccessToken();
		ResultActions result =
			this.mockMvc.perform(get("/api/users")
					.header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$",hasSize(allUsersDTO.size())))
					.andExpect(jsonPath("$[0].username",  is(allUsersDTO.get(0).getUsername())))
					.andExpect(jsonPath("$["+lastElement+"].username",  is(allUsersDTO.get(lastElement).getUsername())));

		String resultString = result.andReturn().getResponse().getContentAsString();
		List<?> reulstList = new JacksonJsonParser().parseList(resultString);
		//assertEquals(reulstList.size(), allUsersDTO.size());

		System.out.println("===================================");
		System.out.println("output test:");
		System.out.println(resultString);
		System.out.println("===================================");
		System.out.println(reulstList.get(0).toString());
		System.out.println(reulstList.get(lastElement).toString());
		System.out.println("===================================");
		System.out.println(allUsersDTO.get(0).toString());
		System.out.println(allUsersDTO.get(lastElement).toString());
		System.out.println("===================================");
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

	@Test
	public void givenNewUser_whenPostUser_thenStatus200() throws Exception {
		Random rand = new Random();
		String random = String.valueOf(rand.nextInt(100000));
		User user = new User(random+"_test@erp.com","ReW9a0&+TP");

		String accessToken = obtainAccessToken();
		ResultActions resultPost =
				this.mockMvc.perform(post("/api/users")
						.header("Authorization", "Bearer " + accessToken)
						.content(new ObjectMapper().writeValueAsString(user))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.username", is(user.getUserName())))
						.andExpect(jsonPath("$.success",  is("True")));

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		System.out.println("===================================");
		System.out.println("output test:");
		System.out.println(resultString);
		System.out.println("===================================");
	}

	@Test
	public void givenUsernameIncorrect_whenPostUser_thenStatus400() throws Exception {
		Random rand = new Random();
		String random = String.valueOf(rand.nextInt(100000));
		User user = new User(random+"_test","ReW9a0&+TP");

		String accessToken = obtainAccessToken();
		ResultActions resultPost =
				this.mockMvc.perform(post("/api/users")
						.header("Authorization", "Bearer " + accessToken)
						.content(new ObjectMapper().writeValueAsString(user))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isBadRequest())
						.andExpect(jsonPath("$.errors.username", Matchers.containsStringIgnoringCase("email incorrect")));

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		System.out.println("===================================");
		System.out.println("output test:");
		System.out.println(resultString);
		System.out.println("===================================");
	}

	@Test
	public void givenPasswordIncorrect_whenPostUser_thenStatus400() throws Exception {
		Random rand = new Random();
		String random = String.valueOf(rand.nextInt(100000));
		User user = new User(random+"_test@erp.com","nopassword");

		String accessToken = obtainAccessToken();
		ResultActions resultPost =
				this.mockMvc.perform(post("/api/users")
						.header("Authorization", "Bearer " + accessToken)
						.content(new ObjectMapper().writeValueAsString(user))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isBadRequest())
						.andExpect(jsonPath("$.errors.password", Matchers.containsStringIgnoringCase("Password invalid")));

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		System.out.println("===================================");
		System.out.println("output test:");
		System.out.println(resultString);
		System.out.println("===================================");
	}

	@Test
	public void givenExistUser_whenPostUser_thenStatus422() throws Exception {
		User user = new User ("admin@erp.com","ReW9a0&+TP", UserType.ADMIN);

		String accessToken = obtainAccessToken();
		ResultActions resultPost =
				this.mockMvc.perform(post("/api/users")
						.header("Authorization", "Bearer " + accessToken)
						.content(new ObjectMapper().writeValueAsString(user))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isUnprocessableEntity())
						.andExpect(jsonPath("$.success",  is("False")));

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		System.out.println("===================================");
		System.out.println("output test:");
		System.out.println(resultString);
		System.out.println("===================================");
	}

	@Test
	public void givenExistUser_whenPostLoginUser_thenStatus200() throws Exception {
		JwtLogin jwtLogin = new JwtLogin("admin@erp.com", "ReW9a0&+TP");

		ResultActions resultPost =
				this.mockMvc.perform(post("/api/login")
						.content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		System.out.println("===================================");
		System.out.println("output test:");
		System.out.println(resultString);
		System.out.println("===================================");
	}

	@Test
	public void givenNoExistUser_whenPostLoginUser_thenStatus422() throws Exception {
		JwtLogin jwtLogin = new JwtLogin("nouser@erp.com", "N0Passw0rd&+");

		ResultActions resultPost =
				this.mockMvc.perform(post("/api/login")
						.content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isUnprocessableEntity())
						.andExpect(jsonPath("$.success",  is("False")));;

		String resultString = resultPost.andReturn().getResponse().getContentAsString();

		System.out.println("===================================");
		System.out.println("output test:");
		System.out.println(resultString);
		System.out.println("===================================");
	}
}
