package cat.itacademy.proyectoerp.OfferControllerTests;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;
import cat.itacademy.proyectoerp.service.IOfferService;

//mvn -Dtest=OfferFilterTest test


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-integrationtest.properties")
@Transactional
class OfferFilterTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private IOfferService repository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("Data correctly loaded into db")
	public void testData() {
		UUID id = UUID.fromString("33330000-0000-0000-0000-000000000000");

		assertThat(repository.findById(id));
	}

	// filter Offers by name
	@Test
	@DisplayName("filter Offers by name ")
	void givenOfferFilteredByName() throws Exception {

		String offerName = "text";
		String accessToken = this.obtainAccessToken();
		String endPoint = "/api/offers";

		this.mvc.perform(get(endPoint, offerName).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// filter offers by discount
	@Test
	@DisplayName("filter Offers by discount min and max")
	void givenOfferFilteredByDiscountMinAndMax() throws Exception {

		Double min = 0.15;
		Double max = 0.3;
		String accessToken = this.obtainAccessToken();
		String endPoint = "/api/offers/discount";

		this.mvc.perform(
				get(endPoint, min).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		this.mvc.perform(
				get(endPoint, max).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		this.mvc.perform(get(endPoint, min, max).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("filter Offers by discount min")
	void givenOfferFilteredByDiscountMin() throws Exception {

		Double min = 0.15;
		
		String accessToken = this.obtainAccessToken();
		String endPoint = "/api/offers/discount";

		this.mvc.perform(
				get(endPoint, min).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		

		this.mvc.perform(get(endPoint, min).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("filter Offers by discount max")
	void givenOfferFilteredByDiscountMax() throws Exception {

		Double max = 0.05;
		
		String accessToken = this.obtainAccessToken();
		String endPoint = "/api/offers/discount";

		this.mvc.perform(
				get(endPoint, max).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		

		this.mvc.perform(get(endPoint, max).header("Authorization", "Bearer " + accessToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	// filter offers by Date
		@Test
		@DisplayName("filter Offers by Date ")
		void givenOfferFilteredByDate() throws Exception {

			String from = "31-08-2021";
			String to = "2021-09-01";
			String accessToken = this.obtainAccessToken();
			String endPoint = "/api/offers";

			this.mvc.perform(get(endPoint, from).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());

			this.mvc.perform(
					get(endPoint, to).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());

			this.mvc.perform(get(endPoint, from, to).header("Authorization", "Bearer " + accessToken)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		}
	
	

	private String obtainAccessToken() throws Exception {
		String testUsername = "admin@erp.com";
		String testPassword = "ReW9a0&+TP";
		JwtLogin jwtLogin = new JwtLogin(testUsername, testPassword);

		ResultActions resultPost = this.mvc
				.perform(post("/api/login").content(new ObjectMapper().writeValueAsString(jwtLogin))
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		String resultString = resultPost.andReturn().getResponse().getContentAsString();
		return new JacksonJsonParser().parseMap(resultString).get("token").toString();
	}

}
