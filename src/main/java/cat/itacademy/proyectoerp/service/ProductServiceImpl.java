package cat.itacademy.proyectoerp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.proyectoerp.domain.Category;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.dto.CategoryDTO;
import cat.itacademy.proyectoerp.dto.ProductDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IProductRepository;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	ICategoryService categoryService;
	
	ModelMapper modelMapper = new ModelMapper();
	TypeMap<ProductDTO, Product> typeMap = modelMapper.createTypeMap(ProductDTO.class, Product.class);


	public Product createProduct(Product product) throws ArgumentNotValidException {

		// Verify that the product name: is not null, not empty or already exists.
		if (product.getName() == null) {
			throw new ArgumentNotValidException("The product must have a name");

		} else if (product.getName().isEmpty()) {
			throw new ArgumentNotValidException("The product name cannot be empty");

		} else if (productRepository.countByName(product.getName()) != 0) {
			throw new ArgumentNotValidException("Product name already exists");

		//verify that the wholesale price is not higher than the price
		} else if (product.getWholesalePrice() > product.getPrice()) {
			throw new ArgumentNotValidException("The wholesale price cannot be higher than price");

		} else if (product.getCreated() > product.getModified()) {
			throw new ArgumentNotValidException("Modified date prior to Create date");
		
		} else {
			return productRepository.save(product);
		}
	}
	
	@Override
	public ProductDTO createProduct(ProductDTO productDto) {
		checkProductName(productDto.getName());
		checkProductPriceAndWholesalePrice(productDto.getPrice(), productDto.getWholesalePrice());
		setProductCreatedAndModified(productDto);
		Product product = mapProductDtoToProductWithoutCategories(productDto);
		product.setCategories(mapCategoriesDtoToCategories(productDto.getCategories()));
		productRepository.save(product);
		return modelMapper.map(product, ProductDTO.class);
	}

	private void checkProductName(String name) {
		if(StringUtils.isBlank(name)) throw new ArgumentNotValidException("Name cannot be null or whitespace");
		if(productRepository.existsByName(name)) throw new ArgumentNotValidException("A category named " + name + " already exists");		
	}

	private void checkProductPriceAndWholesalePrice(double price, double wholesalePrice) {
		if(price < wholesalePrice) throw new ArgumentNotValidException("The wholesale price cannot be higher than the regular price");		
	}

	private void setProductCreatedAndModified(ProductDTO productDto) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		long ts = timestamp.getTime();
		productDto.setCreated(ts);
		productDto.setModified(ts);
	}

	private Product mapProductDtoToProductWithoutCategories(ProductDTO productDto) {
		typeMap.addMappings(mapper -> mapper.skip(Product::setCategories));
		return typeMap.map(productDto);
	}

	private Set<Category> mapCategoriesDtoToCategories(Set<CategoryDTO> categoriesDto) {
		if(categoriesDto == null) return null;
		return categoriesDto.stream().collect(Collectors.mapping(categoryDto -> mapCategoryDtoToEntity(categoryDto), Collectors.toSet()));
	}

	private Category mapCategoryDtoToEntity(CategoryDTO categoryDto) {
		return categoryService.findCategoryById(categoryDto.getId());
	}
	
	@Override
	public List<ProductDTO> getProducts() {
		if (productRepository.findAll().isEmpty()) throw new ArgumentNotFoundException("No products found");
		return productRepository.findAll().stream().collect(Collectors.mapping(product -> modelMapper.map(product, ProductDTO.class), Collectors.toList()));
	}

	@Override
	public List<ProductDTO> getProductsByCategoryName(String name) {
		categoryService.existsCategoryByName(name);
		return productRepository.findAllByCategoriesName(name).stream().collect(Collectors.mapping(product -> modelMapper.map(product, ProductDTO.class), Collectors.toList()));
	}

	@Override
	public Product findProductById(int id) throws ArgumentNotFoundException {
		return productRepository.findById(id).orElseThrow(() -> new ArgumentNotFoundException("Product not found. The id " + id + " doesn't exist"));
	}
	
	public ProductDTO getProductById(int id) {
		return modelMapper.map(findProductById(id), ProductDTO.class);
	}

	@Override
	public ProductDTO updateProduct(Product product) throws ArgumentNotValidException, ArgumentNotFoundException {

		// verify if there is a product with that id.
		Product productSelected = productRepository.findById(product.getId()).get();

		if (productSelected == null) {
			throw new ArgumentNotFoundException("Product not found. The id " + product.getId() + " doesn't exist");
		}

		// NAME:
		// Verify that the product name: is null, not empty or already exists.
		if (product.getName() == null) {
			productSelected.setName(productSelected.getName());

		} else if (product.getName().isEmpty()) {
			throw new ArgumentNotValidException("The product name cannot be empty");

		} else if (!productSelected.getName().equalsIgnoreCase(product.getName())) {

			Product productFound = productRepository.findByName(product.getName());

			if (productFound != null) {
				throw new ArgumentNotValidException("Product name already exists");
			} else {
				productSelected.setName(product.getName());
			}
		}

		// STOCK:
		if (product.getStock() != 0) {
			productSelected.setStock(product.getStock());
		}

		// IMAGE:
		if (product.getImage() != null) {
			productSelected.setImage(product.getImage());
		}

		// FAMILY
		if (product.getFamily() != null) {
			productSelected.setFamily(product.getFamily());
		}

		// PRICE
		if (product.getPrice() != 0) {
			productSelected.setPrice(product.getPrice());
		}

		// VAT
		if (product.getVat() != 0) {
			productSelected.setVat(product.getVat());
		}

		// WHOLESALE PRICE
		// check if the wholesale price changed or if it is higher than the price
		if (product.getWholesalePrice() != 0) {
			productSelected.setWholesalePrice(product.getWholesalePrice());
		}

		if (productSelected.getWholesalePrice() > productSelected.getPrice()) {
			throw new ArgumentNotValidException("The wholesale price cannot be higher than price");
		}

		// WHOLESALE QUANTITY
		if (product.getWholesaleQuantity() != 0) {
			productSelected.setWholesaleQuantity(product.getWholesaleQuantity());
		}
		
		if (product.getModified() >= product.getCreated()) {
			productSelected.setModified(product.getModified());
		}

		productRepository.save(productSelected);
		return modelMapper.map(productSelected, ProductDTO.class);

	}

	@Override
	@Transactional
	public void deleteProduct(int id) {
		productRepository.deleteById(id);
	}

}