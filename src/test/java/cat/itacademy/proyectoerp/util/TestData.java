package cat.itacademy.proyectoerp.util;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;

import cat.itacademy.proyectoerp.domain.Category;
import cat.itacademy.proyectoerp.domain.Offer;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.dto.CategoryDTO;
import cat.itacademy.proyectoerp.dto.OfferDTO;
import cat.itacademy.proyectoerp.dto.ProductDTO;
import cat.itacademy.proyectoerp.dto.ShopDTO;
import cat.itacademy.proyectoerp.repository.ICategoryRepository;
import cat.itacademy.proyectoerp.repository.IOfferRepository;
import cat.itacademy.proyectoerp.repository.IProductRepository;
import cat.itacademy.proyectoerp.repository.IShopRepository;
import cat.itacademy.proyectoerp.repository.IUserRepository;

@TestComponent
public class TestData {
	
	public static final String
			DEFAULT_ADMIN_USERNAME = "admin@erp.com",
			DEFAULT_EMPLOYEE_USERNAME = "employee@erp.com",
			DEFAULT_PASSWORD = "ReW9a0&+TP";
	
	public static final String VALID_CATEGORY_NAME = "Componentes",
			VALID_CATEGORY_DESCRIPTION = "Componentes de ordenador";
	
	public static final String VALID_PRODUCT_NAME = "MSI GT 1030";
	public static final int VALID_PRODUCT_STOCK = 200;
	public static final String VALID_PRODUCT_IMAGE = "url";
	public static final double VALID_PRODUCT_PRICE = 80,
			VALID_PRODUCT_VAT = 21.0,
			VALID_PRODUCT_WHOLESALE_PRICE = 70;
	public static final int VALID_PRODUCT_WHOLESALE_QUANTITY = 50;
	
	//datos test Ofertas


	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private ICategoryRepository categoryRepository;
	
	@Autowired
	private IShopRepository shopRepository;
	
	@Autowired
	private IOfferRepository offerRepository;
	
	public void resetData() {
		shopRepository.deleteAll();
		productRepository.deleteAll();
		categoryRepository.deleteAll();
		//offerRepository.deleteAll();
	}
	
	public Category createCategory(String name, String description, Offer offer) {
		Category category = new Category(name, description, offer);
		
		return categoryRepository.save(category);
	}
	
	public CategoryDTO buildCategoryDto(String name, String description, OfferDTO offerDto) {
		return new CategoryDTO(name, description, offerDto);
	}
	
	public CategoryDTO buildCategoryDto(UUID id, String name, String description, OfferDTO offerDto) {
		return new CategoryDTO(id, name, description, offerDto);
	}

	public ProductDTO buildProductDto(String name, int stock, String image, double price, double vat, double wholesalePrice, int wholesaleQuantity,
			OfferDTO offerDto, CategoryDTO categoryDto, ShopDTO shopDto) {
		return new ProductDTO(name, stock, image, price, vat, wholesalePrice, wholesaleQuantity, offerDto, categoryDto, shopDto);
	}
	
	//Offer
	public Offer createOffer(String name, double discount, LocalDateTime startsOn,
			LocalDateTime endsOn, int paidQuantity, int freeQuantity) {

		//Offer offer = new Offer(null, name, discount, startsOn,endsOn,paidQuantity,freeQuantity);

		Offer offer = new Offer(name, discount, startsOn,endsOn,paidQuantity,freeQuantity);

		return offerRepository.save(offer);
	
	}
	
	public OfferDTO buildOfferDto(String name, double discount, LocalDateTime startsOn,
			LocalDateTime endsOn, int paidQuantity, int freeQuantity) {
		return new OfferDTO(name, discount, startsOn,endsOn,paidQuantity,freeQuantity);
	}
	
	public OfferDTO buildOfferDto(UUID id,String name, double discount, LocalDateTime startsOn,
			LocalDateTime endsOn, int paidQuantity, int freeQuantity) {
		return new OfferDTO(id,name, discount, startsOn,endsOn,paidQuantity,freeQuantity);
	}
	
}
