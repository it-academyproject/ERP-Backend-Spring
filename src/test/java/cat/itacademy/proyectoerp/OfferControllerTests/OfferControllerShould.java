package cat.itacademy.proyectoerp.OfferControllerTests;

import static cat.itacademy.proyectoerp.util.TestData.*;
import static cat.itacademy.proyectoerp.util.TestUtil.loginAndGetJwtToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.domain.Category;
import cat.itacademy.proyectoerp.dto.CategoryDTO;
import cat.itacademy.proyectoerp.dto.ProductDTO;
import cat.itacademy.proyectoerp.repository.ICategoryRepository;
import cat.itacademy.proyectoerp.util.TestData;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(TestData.class)
@TestPropertySource("classpath:application-integrationtest.properties")
public class OfferControllerShould {
	
	private static final String PRODUCT_BASE_URI  = "/api/products/",
			CATEGORY_BASE_URI = PRODUCT_BASE_URI + "categories/";
	
	@Autowired
	private TestData testData;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private JacksonJsonParser jsonParser = new JacksonJsonParser(objectMapper);
	
	@Autowired
	private MockMvc mvc;
	
	@BeforeEach
	void beforeEach() {
		testData.resetData();
	}
	
	private String obtainAccessToken(boolean isAdmin) throws Exception {
		if (isAdmin)
			return loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		else if (!isAdmin)
			return loginAndGetJwtToken(mvc, DEFAULT_EMPLOYEE_USERNAME, DEFAULT_PASSWORD);
		else
			return null;
	}
	
	private ResultActions performGetRequest(boolean isAdmin, String endpoint) throws Exception {
		return mvc
			.perform(get(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAccessToken(isAdmin))
				.accept(MediaType.APPLICATION_JSON));
	}
	
	private ResultActions performPostRequest(boolean isAdmin, String endpoint, String json) throws Exception {
		return mvc
			.perform(post(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAccessToken(isAdmin))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json));
	}
	
	private ResultActions performPutRequest(boolean isAdmin, String endpoint, String json) throws Exception {
		return mvc
			.perform(put(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAccessToken(isAdmin))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json));
	}
	
	private ResultActions performDeleteRequest(boolean isAdmin, String endpoint, String json) throws Exception {
		return mvc
			.perform(delete(endpoint)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.obtainAccessToken(isAdmin))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json));
	}
	
	// CATEGORIES
	// POST /api/products/categories
	
	@Test
	void returnErrorWhenCategoryNameIsBlank() throws Exception {
		// GIVEN
		CategoryDTO categoryDto = testData.buildCategoryDto("   ", VALID_CATEGORY_DESCRIPTION, null);
		
		String json = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = this.performPostRequest(true, CATEGORY_BASE_URI, json)
			.andExpect(status().isOk())
			.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
			() -> assertThat(map.get("success")).isEqualTo("false"),
			() -> assertThat(map.get("message")).isEqualTo("error: Name cannot be null or whitespace")
		);
	}
	
	@Test
	void returnErrorWhenCategoryDescriptionIsBlank() throws Exception {
		// GIVEN
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_CATEGORY_NAME, "   ", null);
		
		String json = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = this.performPostRequest(true, CATEGORY_BASE_URI, json)
			.andExpect(status().isOk())
			.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
			() -> assertThat(map.get("success")).isEqualTo("false"),
			() -> assertThat(map.get("message")).isEqualTo("error: Description must be between 10 and 200 characters")
		);
	}
	
	@Test
	void returnErrorWhenCategoryDescriptionHasIncorrectLength() throws Exception {
		// GIVEN
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_CATEGORY_NAME, "Too short", null);
		
		String json = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = this.performPostRequest(true, CATEGORY_BASE_URI, json)
			.andExpect(status().isOk())
			.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: Description must be between 10 and 200 characters")
		);
	}
	
	@Test
	void returnErrorWhenPersistingACategoryWithoutAuthorization() throws Exception {
		// GIVEN
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION, null);
		
		String json = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = this.performPostRequest(false, CATEGORY_BASE_URI, json)
			.andExpect(status().isUnauthorized())
			.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		assertAll(
				() -> assertThat(map.get("status")).isEqualTo("UNAUTHORIZED"),
				() -> assertThat(map.get("message")).isEqualTo("Access Denied")
		);
	}
	
	@Test
	void returnErrorWhenCategoryNameAlreadyExists() throws Exception {
		// GIVEN
		testData.createCategory(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION, null);
		
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_CATEGORY_NAME, "Different description", null);
		
		String json = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = this.performPostRequest(true, CATEGORY_BASE_URI, json)
			.andExpect(status().isOk())
			.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
			() -> assertThat(map.get("success")).isEqualTo("false"),
			() -> assertThat(map.get("message")).isEqualTo("error: A category named " + VALID_CATEGORY_NAME + " already exists")
		);
	}
	
	@Test
	void returnErrorWhenCategoryDescriptionAlreadyExists() throws Exception {
		// GIVEN
		testData.createCategory(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION, null);
		
		CategoryDTO categoryDto = testData.buildCategoryDto("Different name", VALID_CATEGORY_DESCRIPTION, null);
		
		String json = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = this.performPostRequest(true, CATEGORY_BASE_URI, json)
			.andExpect(status().isOk())
			.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
			() -> assertThat(map.get("success")).isEqualTo("false"),
			() -> assertThat(map.get("message")).isEqualTo("error: Another category has the same description")
		);
	}
	
	// GET /api/products/categories
	
	@Test
	void returnErrorWhenFetchingANonExistentCategory() throws Exception {
		// GIVEN
		UUID categoryId = UUID.randomUUID();
		// WHEN
		MockHttpServletResponse response = this.performGetRequest(true, CATEGORY_BASE_URI + categoryId)
			.andExpect(status().isOk())
			.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());

		assertAll(
			() -> assertThat(map.get("success")).isEqualTo("false"),
			() -> assertThat(map.get("message")).isEqualTo("error: The id " + categoryId + " doesn't correspond to any category")
		);	
	}

	@Autowired
	private ICategoryRepository categoryRepository;
	
	@Test
	void fetchACategory() throws Exception {
		// GIVEN
		Category category = new Category(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION, null);
		
		categoryRepository.save(category);
		// WHEN
		MockHttpServletResponse response = this.performGetRequest(true, CATEGORY_BASE_URI + category.getId())
			.andExpect(status().isOk())
			.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		CategoryDTO categoryDto = objectMapper.convertValue(map.get("category"), CategoryDTO.class);
		
		assertAll(
			() -> assertThat(map.get("success")).isEqualTo("true"),
			() -> assertThat(map.get("message")).isEqualTo("category found"),
			() -> assertThat(categoryDto.getId()).isNotNull(),
			() -> assertThat(categoryDto.getName()).isEqualTo(VALID_CATEGORY_NAME),
			() -> assertThat(categoryDto.getDescription()).isEqualTo(VALID_CATEGORY_DESCRIPTION)
		);	
	}
	
	@Test
	void fetchListOfCategories() throws Exception {
		// GIVEN
		List<Category> categories = new ArrayList<>();
		
		categories.add(testData.createCategory(VALID_CATEGORY_NAME + " One", VALID_CATEGORY_DESCRIPTION + " One", null));
		categories.add(testData.createCategory(VALID_CATEGORY_NAME + " Two", VALID_CATEGORY_DESCRIPTION + " Two", null));
		// WHEN
		MockHttpServletResponse response = this.performGetRequest(true, CATEGORY_BASE_URI)
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("true"),
				() -> assertThat(map.get("message")).isEqualTo("categories found")
		);	
	}
	
	// PUT /api/products/categories
	
	@Test
	void updateACategory() throws Exception {
		// GIVEN
		Category category = testData.createCategory(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION, null);
		
		CategoryDTO categoryDto = testData.buildCategoryDto(category.getId(), "Modified name", "Modified description", null);
		
		String json = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = this.performPutRequest(true, CATEGORY_BASE_URI + categoryDto.getId(), json)
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		CategoryDTO responseCategoryDto = objectMapper.convertValue(map.get("category"), CategoryDTO.class);
		
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("true"),
				() -> assertThat(map.get("message")).isEqualTo("category updated"),
				() -> assertThat(responseCategoryDto.getId()).isEqualTo(categoryDto.getId()),
				() -> assertThat(responseCategoryDto.getName()).isEqualTo(categoryDto.getName()),
				() -> assertThat(responseCategoryDto.getDescription()).isEqualTo(categoryDto.getDescription())
		);	
	}
	
	@Test
	void returnErrorWhenUpdatingInexistentCategory() throws Exception {
		// GIVEN
		UUID categoryId = UUID.randomUUID();
		
		CategoryDTO categoryDto = testData.buildCategoryDto(categoryId, "Non-existent name", "Non-existent description", null);
		
		String json = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = this.performPutRequest(true, CATEGORY_BASE_URI + categoryDto.getId(), json)
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: The id " + categoryId + " doesn't correspond to any category")
		);	
	}
	
	@Test
	void returnErrorWhenUpdatingACategoryWithoutAuthorization() throws Exception {
		// GIVEN
		Category category = testData.createCategory(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION, null);
		
		CategoryDTO categoryDto = testData.buildCategoryDto(category.getId(), "Modified name", "Modified description", null);
		
		String json = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = this.performPutRequest(false, CATEGORY_BASE_URI + categoryDto.getId(), json)
				.andExpect(status().isUnauthorized())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
				() -> assertThat(map.get("status")).isEqualTo("UNAUTHORIZED"),
				() -> assertThat(map.get("message")).isEqualTo("Access Denied")
		);
	}
	
	// DELETE /api/products/categories
	
	@Test
	void deleteACategory() throws Exception {
		// GIVEN
		Category category = testData.createCategory(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION, null);
		
		String json = objectMapper.writeValueAsString(category);
		// WHEN
		MockHttpServletResponse response = this.performDeleteRequest(true, CATEGORY_BASE_URI, json)
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("true"),
				() -> assertThat(map.get("message")).isEqualTo("category deleted")
		);	
	}
	
	@Test
	void returnErrorWhenDeletingInexistentCategory() throws Exception {
		// GIVEN
		UUID categoryId = UUID.randomUUID();
		
		CategoryDTO categoryDto = testData.buildCategoryDto(categoryId, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION, null);
		
		String json = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = this.performDeleteRequest(true, CATEGORY_BASE_URI, json)
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: The id " + categoryId + " doesn't correspond to any category")
		);	
	}
	
	@Test
	void returnErrorWhenDeletingACategoryWithoutAuthorization() throws Exception {
		// GIVEN
		Category category = testData.createCategory(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION, null);
		
		String json = objectMapper.writeValueAsString(category);
		// WHEN
		MockHttpServletResponse response = this.performDeleteRequest(false, CATEGORY_BASE_URI, json)
			.andExpect(status().isUnauthorized())
			.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
			() -> assertThat(map.get("status")).isEqualTo("UNAUTHORIZED"),
			() -> assertThat(map.get("message")).isEqualTo("Access Denied")
		);
	}
	
	// PRODUCTS
	// POST /api/products
	
	@Test
	void returnErrorWhenProductNameIsBlank() throws Exception {
		// GIVEN
		ProductDTO productDto = testData.buildProductDto(" ", VALID_PRODUCT_STOCK, VALID_PRODUCT_IMAGE, VALID_PRODUCT_PRICE, VALID_PRODUCT_VAT, VALID_PRODUCT_WHOLESALE_PRICE, VALID_PRODUCT_WHOLESALE_QUANTITY, null, null, null);
		
		String json = objectMapper.writeValueAsString(productDto);
		// WHEN
		MockHttpServletResponse response = this.performPostRequest(true, PRODUCT_BASE_URI, json)
			.andExpect(status().isOk())
			.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
			() -> assertThat(map.get("success")).isEqualTo("false"),
			() -> assertThat(map.get("message")).isEqualTo("error: Name cannot be null or whitespace")
		);
	}
	
	@Test
	void returnErrorWhenProductPriceIsLessThanWholesalePrice() throws Exception {
		// GIVEN
		ProductDTO productDto = testData.buildProductDto(VALID_PRODUCT_NAME, VALID_PRODUCT_STOCK, VALID_PRODUCT_IMAGE, 10, VALID_PRODUCT_VAT, 100, VALID_PRODUCT_WHOLESALE_QUANTITY, null, null, null);
		
		String json = objectMapper.writeValueAsString(productDto);
		// WHEN
		MockHttpServletResponse response = this.performPostRequest(true, PRODUCT_BASE_URI, json)
			.andExpect(status().isOk())
			.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
			() -> assertThat(map.get("success")).isEqualTo("false"),
			() -> assertThat(map.get("message")).isEqualTo("error: The wholesale price cannot be higher than the regular price")
		);
	}
	
	@Test
	void persistProductWithCategory() throws Exception {
		// GIVEN
		Category category = testData.createCategory(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION, null);
		
		CategoryDTO categoryDto = objectMapper.convertValue(category, CategoryDTO.class);
		ProductDTO productDto = testData.buildProductDto(VALID_PRODUCT_NAME, VALID_PRODUCT_STOCK, VALID_PRODUCT_IMAGE, VALID_PRODUCT_PRICE, VALID_PRODUCT_VAT, VALID_PRODUCT_WHOLESALE_PRICE, VALID_PRODUCT_WHOLESALE_QUANTITY, null, categoryDto, null);
		
		String json = objectMapper.writeValueAsString(productDto);
		// WHEN
		MockHttpServletResponse response = this.performPostRequest(true, PRODUCT_BASE_URI, json)
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		ProductDTO responseProductDto = objectMapper.convertValue(map.get("product"), ProductDTO.class);
		
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("true"),
				() -> assertThat(map.get("message")).isEqualTo("product created"),
				() -> assertThat(responseProductDto.getId()).isNotNull(),
				() -> assertThat(responseProductDto.getName()).isEqualTo(VALID_PRODUCT_NAME),
				() -> assertThat(responseProductDto.getStock()).isEqualTo(VALID_PRODUCT_STOCK),
				() -> assertThat(responseProductDto.getWholesalePrice()).isEqualTo(VALID_PRODUCT_WHOLESALE_PRICE),
				() -> assertTrue(responseProductDto.getCategoryDto().equals(categoryDto))
		);
	}
	
	@Test
	void returnErrorWhenProductCategoryIsIncorrect() throws Exception {
		// GIVEN
		UUID categoryId = UUID.randomUUID();
		
		CategoryDTO categoryDto = testData.buildCategoryDto(categoryId, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION, null);
		ProductDTO productDto = testData.buildProductDto(VALID_PRODUCT_NAME, VALID_PRODUCT_STOCK, VALID_PRODUCT_IMAGE, VALID_PRODUCT_PRICE, VALID_PRODUCT_VAT, VALID_PRODUCT_WHOLESALE_PRICE, VALID_PRODUCT_WHOLESALE_QUANTITY, null, categoryDto, null);
		
		String json = objectMapper.writeValueAsString(productDto);
		// WHEN
		MockHttpServletResponse response = this.performPostRequest(true, PRODUCT_BASE_URI, json)
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: The id " + categoryId + " doesn't correspond to any category")
		);
	}
	
}
