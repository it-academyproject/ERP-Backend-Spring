package cat.itacademy.proyectoerp.util;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;

import cat.itacademy.proyectoerp.domain.Category;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.dto.CategoryDTO;
import cat.itacademy.proyectoerp.dto.ProductDTO;
import cat.itacademy.proyectoerp.repository.ICategoryRepository;
import cat.itacademy.proyectoerp.repository.IProductRepository;
import cat.itacademy.proyectoerp.repository.IUserRepository;

@TestComponent
public class TestData {

	public static final String DEFAULT_ADMIN_USERNAME = "admin@erp.com";
	
	public static final String DEFAULT_EMPLOYEE_USERNAME = "employee@erp.com";
	
	public static final String DEFAULT_PASSWORD = "ReW9a0&+TP";

	public static final String VALID_PARENT_CATEGORY_NAME = "Componentes";
	
	public static final String VALID_PARENT_CATEGORY_DESCRIPTON = "Componentes de ordenador";

	public static final String VALID_CATEGORY_NAME = "Targetas graficas";
	
	public static final String VALID_CATEGORY_DESCRIPTON = "Targetas graficas para ordenador";
	
	public static final String VALID_PRODUCT_NAME = "MSI GT 1030";
	
	public static final int VALID_STOCK = 200;
	
	public static final String VALID_IMAGE = "url";
	
	public static final String VALID_FAMILY = "MSI";
	
	public static final double VALID_PRICE = 80;
	
	public static final double VALID_VAT = 21.0;
	
	public static final double VALID_WHOLESALE_PRICE = 70;
	
	public static final int VALID_WHOLESALE_QUANTITY = 50;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private ICategoryRepository categoryRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	public void resetData() {
		userRepository.deleteAll();
		productRepository.deleteAll();
		categoryRepository.deleteAll();
	}
	
	public void createAdminUser() {
		userRepository.save(new User(DEFAULT_ADMIN_USERNAME, passwordEncoder.encode(DEFAULT_PASSWORD), UserType.ADMIN));
	}
	
	public void createEmployeeUser() {
		userRepository.save(new User(DEFAULT_EMPLOYEE_USERNAME, passwordEncoder.encode(DEFAULT_PASSWORD), UserType.EMPLOYEE));
	}
	
	public Category createCategory(String name, String description, Category parentCategory) {
		Category category = new Category(name, description, parentCategory);
		return categoryRepository.save(category);
	}
	
	public CategoryDTO buildCategoryDto(String name, String description, UUID parentCategoryId) {
		CategoryDTO categoryDto = new CategoryDTO();
		categoryDto.setName(name);
		categoryDto.setDescription(description);
		categoryDto.setParentCategoryId(parentCategoryId);
		return categoryDto;
	}
	
	public Set<CategoryDTO> buildSetCategoryDto(Category... categories) {
		return Set.of(categories).stream().collect(Collectors.mapping(category -> modelMapper.map(category, CategoryDTO.class) , Collectors.toSet()));
	}
	
	public Product createProduct(String name, int stock, String image, String family, double price, double vat, double wholesalePrice, int wholesaleQuantity, long created, long modified) {
		Product product = new Product(name, stock, image, family, price, vat, wholesalePrice, wholesaleQuantity, created, modified);
		return productRepository.save(product);
	}
	
	public ProductDTO buildProductDto(String name, int stock, String image, String family, double price, double vat, double wholesalePrice, int wholesaleQuantity, Set<CategoryDTO> categoriesDto) {
		ProductDTO productDto = new ProductDTO();
		productDto.setName(name);
		productDto.setStock(stock);
		productDto.setImage(image);
		productDto.setFamily(family);
		productDto.setPrice(price);
		productDto.setVat(vat);
		productDto.setWholesalePrice(wholesalePrice);
		productDto.setWholesaleQuantity(wholesaleQuantity);
		productDto.setCategories(categoriesDto);
		return productDto;
	}
	
}
