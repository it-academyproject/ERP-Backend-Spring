package cat.itacademy.proyectoerp.api;

import static cat.itacademy.proyectoerp.util.TestData.*;
import static cat.itacademy.proyectoerp.util.TestUtil.loginAndGetJwtToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cat.itacademy.proyectoerp.domain.Category;
import cat.itacademy.proyectoerp.dto.CategoryDTO;
import cat.itacademy.proyectoerp.dto.ProductDTO;
import cat.itacademy.proyectoerp.util.TestData;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(TestData.class)
public class ProductControllerShould {

	private static final String PRODUCT_BASE_URI  = "/api/products/";
	
	private static final String CATEGORY_BASE_URI = "/api/products/categories/";
	
		
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
		testData.createAdminUser();
    }
	
	@Test
	void returnErrorWhenCategoryNameIsBlank() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		CategoryDTO categoryDto = testData.buildCategoryDto("  ", VALID_PARENT_CATEGORY_DESCRIPTON, null);
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(CATEGORY_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
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
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_PARENT_CATEGORY_NAME, null, null);
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(CATEGORY_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
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
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_PARENT_CATEGORY_NAME, "Desc", null);
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(CATEGORY_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
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
	void persistAParentCategory() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(CATEGORY_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		CategoryDTO category = objectMapper.convertValue(map.get("category"), CategoryDTO.class);
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("true"),
				() -> assertThat(map.get("message")).isEqualTo("category created"),
				() -> assertThat(category.getId()).isNotNull(),
				() -> assertThat(category.getName()).isEqualTo(categoryDto.getName()),
				() -> assertThat(category.getDescription()).isEqualTo(categoryDto.getDescription()),
				() -> assertThat(category.getParentCategoryId()).isNull(),
				() -> assertThat(category.getParentCategoryName()).isNull()
		);
	}

	@Test
	void returnErrorWhenPersistingACategoryWithoutAuthorization() throws Exception {
		// GIVEN
		testData.createEmployeeUser();
		String token = loginAndGetJwtToken(mvc, DEFAULT_EMPLOYEE_USERNAME, DEFAULT_PASSWORD);
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(CATEGORY_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
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
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		testData.createCategory(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_PARENT_CATEGORY_NAME, "Other correct description", null);
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(CATEGORY_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: A category named " + VALID_PARENT_CATEGORY_NAME + " already exists")
		);
	}
	
	@Test
	void returnErrorWhenCategoryDescriptionAlreadyExists() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		testData.createCategory(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		CategoryDTO categoryDto = testData.buildCategoryDto("Other correct name", VALID_PARENT_CATEGORY_DESCRIPTON, null);
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(CATEGORY_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: Another category has the same description")
		);
	}
	
	@Test
	void returnErrorWhenParentCategoryDoesNotExists() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		UUID randomId = UUID.randomUUID();
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTON, randomId);
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(CATEGORY_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: The id " + randomId + " doesn't correspond to any category")
		);
	}
	
	@Test
	void persistASubCategory() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		Category parentCategory = testData.createCategory(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTON, parentCategory.getId());
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(CATEGORY_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		CategoryDTO category = objectMapper.convertValue(map.get("category"), CategoryDTO.class);
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("true"),
				() -> assertThat(map.get("message")).isEqualTo("category created"),
				() -> assertThat(category.getId()).isNotNull(),
				() -> assertThat(category.getName()).isEqualTo(categoryDto.getName()),
				() -> assertThat(category.getDescription()).isEqualTo(categoryDto.getDescription()),
				() -> assertThat(category.getParentCategoryId()).isEqualTo(parentCategory.getId()),
				() -> assertThat(category.getParentCategoryName()).isEqualTo(parentCategory.getName())
		);
	}
	
	@Test
	void returnErrorWhenParentCategoryIsASubCagetory() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		Category parentCategory = testData.createCategory(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		Category subCategory = testData.createCategory(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTON, parentCategory);
		CategoryDTO categoryDto = testData.buildCategoryDto("Other valid name", "Other valid description", subCategory.getId());
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(CATEGORY_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: A category can only be subcategory of a parent category, not of another subcategory")
		);
	}
	
	@Test
	void returnErrorWhenFetchingANonExistentCategory() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		UUID randomId = UUID.randomUUID();
		// WHEN
		MockHttpServletResponse response = mvc.perform(get(CATEGORY_BASE_URI + randomId)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: The id " + randomId + " doesn't correspond to any category")
		);	
	}
	
	@Test
	void fetchACategory() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		Category persistedCategory = testData.createCategory(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		// WHEN
		MockHttpServletResponse response = mvc.perform(get(CATEGORY_BASE_URI + persistedCategory.getId())
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		CategoryDTO category = objectMapper.convertValue(map.get("category"), CategoryDTO.class);
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("true"),
				() -> assertThat(map.get("message")).isEqualTo("category found"),
				() -> assertThat(category.getId()).isNotNull(),
				() -> assertThat(category.getName()).isEqualTo(persistedCategory.getName()),
				() -> assertThat(category.getDescription()).isEqualTo(persistedCategory.getDescription()),
				() -> assertThat(category.getParentCategoryId()).isNull(),
				() -> assertThat(category.getParentCategoryName()).isNull()
		);	
	}
	
	@Test
	void fetchListOfCategories() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		Category parentCategory = testData.createCategory(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		Category subCategory = testData.createCategory(VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTON, parentCategory);
		List<CategoryDTO> categories = testData.buildListCategoryDto(new Category[] {parentCategory, subCategory});
		// WHEN
		MockHttpServletResponse response = mvc.perform(get(CATEGORY_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		List<CategoryDTO> cata = objectMapper.convertValue(map.get("categories"), new TypeReference<List<CategoryDTO>>(){});
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("true"),
				() -> assertThat(map.get("message")).isEqualTo("categories found"),
				() -> assertThat(cata).isEqualTo(categories)
		);	
	}
	
	@Test
	void returnErrorWhenUpdatingInexistentCategory() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		UUID randomId = UUID.randomUUID();
		CategoryDTO categoryDto = testData.buildCategoryDto("Other valid name", "Other valid description", null);
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(put(CATEGORY_BASE_URI + randomId)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: The id " + randomId + " doesn't correspond to any category")
		);	
	}
	
	@Test
	void updateACategory() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		Category persistedCategory = testData.createCategory(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		CategoryDTO categoryDto = testData.buildCategoryDto("Other valid name", "Other valid description", null);
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(put(CATEGORY_BASE_URI + persistedCategory.getId())
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		CategoryDTO category = objectMapper.convertValue(map.get("category"), CategoryDTO.class);
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("true"),
				() -> assertThat(map.get("message")).isEqualTo("category updated"),
				() -> assertThat(category.getId()).isEqualTo(persistedCategory.getId()),
				() -> assertThat(category.getName()).isEqualTo("Other valid name"),
				() -> assertThat(category.getDescription()).isEqualTo( "Other valid description"),
				() -> assertThat(category.getParentCategoryId()).isNull(),
				() -> assertThat(category.getParentCategoryName()).isNull()
		);	
	}
	
	@Test
	void returnErrorWhenUpdatingACategoryWithoutAuthorization() throws Exception {
		// GIVEN
		testData.createEmployeeUser();
		String token = loginAndGetJwtToken(mvc, DEFAULT_EMPLOYEE_USERNAME, DEFAULT_PASSWORD);
		Category persistedCategory = testData.createCategory(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		CategoryDTO categoryDto = testData.buildCategoryDto("Other valid name", "Other valid description", null);
		String jsonBody = objectMapper.writeValueAsString(categoryDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(put(CATEGORY_BASE_URI + persistedCategory.getId())
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
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
	void returnErrorWhenDeletingInexistentCategory() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		UUID randomId = UUID.randomUUID();
		// WHEN
		MockHttpServletResponse response = mvc.perform(delete(CATEGORY_BASE_URI + randomId)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: The id " + randomId + " doesn't correspond to any category")
		);	
	}
	
	@Test
	void deleteACategory() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		Category persistedCategory = testData.createCategory(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		// WHEN
		MockHttpServletResponse response = mvc.perform(delete(CATEGORY_BASE_URI + persistedCategory.getId())
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.accept(APPLICATION_JSON))
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
	void returnErrorWhenDeletingACategoryWithoutAuthorization() throws Exception {
		// GIVEN
		testData.createEmployeeUser();
		String token = loginAndGetJwtToken(mvc, DEFAULT_EMPLOYEE_USERNAME, DEFAULT_PASSWORD);
		Category persistedCategory = testData.createCategory(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		// WHEN
		MockHttpServletResponse response = mvc.perform(delete(CATEGORY_BASE_URI + persistedCategory.getId())
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.accept(APPLICATION_JSON))
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
	void returnErrorWhenProductNameIsBlank() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		ProductDTO productDto = testData.buildProductDto("  ", VALID_STOCK, VALID_IMAGE, VALID_FAMILY, VALID_PRICE, VALID_VAT, VALID_WHOLESALE_PRICE, VALID_WHOLESALE_QUANTITY, null);
		String jsonBody = objectMapper.writeValueAsString(productDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(PRODUCT_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
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
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		ProductDTO productDto = testData.buildProductDto(VALID_PRODUCT_NAME, VALID_STOCK, VALID_IMAGE, VALID_FAMILY, 10, VALID_VAT, 100, VALID_WHOLESALE_QUANTITY, null);
		String jsonBody = objectMapper.writeValueAsString(productDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(PRODUCT_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
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
	void returnErrorWhenProductCategoriesIsIncorrect() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		CategoryDTO categoryDto = testData.buildCategoryDto(VALID_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		UUID randomId = UUID.randomUUID();
		categoryDto.setId(randomId);
		Set<CategoryDTO> categoriesDto = Set.of(categoryDto);
		ProductDTO productDto = testData.buildProductDto(VALID_PRODUCT_NAME, VALID_STOCK, VALID_IMAGE, VALID_FAMILY, VALID_PRICE, VALID_VAT, VALID_WHOLESALE_PRICE, VALID_WHOLESALE_QUANTITY, categoriesDto);
		String jsonBody = objectMapper.writeValueAsString(productDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(PRODUCT_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("false"),
				() -> assertThat(map.get("message")).isEqualTo("error: The id " + randomId + " doesn't correspond to any category")
		);
	}
	
	@Test
	void persistProductWithCategories() throws Exception {
		// GIVEN
		String token = loginAndGetJwtToken(mvc, DEFAULT_ADMIN_USERNAME, DEFAULT_PASSWORD);
		Category persistedCategory = testData.createCategory(VALID_PARENT_CATEGORY_NAME, VALID_PARENT_CATEGORY_DESCRIPTON, null);
		Category anotherPersistedCategory = testData.createCategory("Another valid name", "Another valid description", null);
		Set<CategoryDTO> categoriesDto = testData.buildSetCategoryDto(new Category[] {persistedCategory, anotherPersistedCategory});
		ProductDTO productDto = testData.buildProductDto(VALID_PRODUCT_NAME, VALID_STOCK, VALID_IMAGE, VALID_FAMILY, VALID_PRICE, VALID_VAT, VALID_WHOLESALE_PRICE, VALID_WHOLESALE_QUANTITY, categoriesDto);
		String jsonBody = objectMapper.writeValueAsString(productDto);
		// WHEN
		MockHttpServletResponse response = mvc.perform(post(PRODUCT_BASE_URI)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.content(jsonBody)
				.contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		// THEN
		Map<String, Object> map = jsonParser.parseMap(response.getContentAsString());
		ProductDTO product = objectMapper.convertValue(map.get("product"), ProductDTO.class);
		assertAll(
				() -> assertThat(map.get("success")).isEqualTo("true"),
				() -> assertThat(map.get("message")).isEqualTo("product created"),
				() -> assertThat(product.getId()).isNotNull(),
				() -> assertThat(product.getName()).isEqualTo(VALID_PRODUCT_NAME),
				() -> assertThat(product.getStock()).isEqualTo(VALID_STOCK),
				() -> assertThat(product.getFamily()).isEqualTo(VALID_FAMILY),
				() -> assertThat(product.getWholesalePrice()).isEqualTo(VALID_WHOLESALE_PRICE),
				() -> assertTrue(product.getCategories().equals(categoriesDto))
		);
	}
	
}
