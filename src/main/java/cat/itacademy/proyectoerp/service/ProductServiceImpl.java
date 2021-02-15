package cat.itacademy.proyectoerp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IProductRepository;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	IProductRepository productRepo;

	@Override
	public Product createProduct(Product product) throws ArgumentNotValidException {

		// Verify that the product name: is not null, not empty or already exists.
		if (product.getName() == null) {
			throw new ArgumentNotValidException("The product must have a name");

		} else if (product.getName().isEmpty()) {
			throw new ArgumentNotValidException("The product name cannot be empty");

		} else if (productRepo.countByName(product.getName()) != 0) {
			throw new ArgumentNotValidException("Product name already exists");

		//verify that the wholesale price is not higher than the price
		} else if (product.getWholesale_price() > product.getPrice()) {
			throw new ArgumentNotValidException("The wholesale price cannot be higher than price");

		} else {
			return productRepo.save(product);
		}
	}

	@Override
	public List<Product> getProducts() throws ArgumentNotFoundException {

		// if there are no products, throw exception
		if (productRepo.findAll().isEmpty()) {
			throw new ArgumentNotFoundException("No products found");
		} else {
			return productRepo.findAll();
		}

	}

	@Override
	public Product findProductById(int id) throws ArgumentNotFoundException {

		//if there is no product with that id throw exception
		return productRepo.findById(id)
				.orElseThrow(() -> new ArgumentNotFoundException("Product not found. The id " + id + " doesn't exist"));
	}

	@Override
	public Product updateProduct(Product product) throws ArgumentNotValidException, ArgumentNotFoundException {

		// verify if there is a product with that id.
		Product productSelected = productRepo.findById(product.getId()).get();

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

			Product productFound = productRepo.findByName(product.getName());

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
		if (product.getWholesale_price() != 0) {
			productSelected.setWholesale_price(product.getWholesale_price());
		}

		if (productSelected.getWholesale_price() > productSelected.getPrice()) {
			throw new ArgumentNotValidException("The wholesale price cannot be higher than price");
		}

		// WHOLESALE QUANTITY
		if (product.getWholesale_quantity() != 0) {
			productSelected.setWholesale_quantity(product.getWholesale_quantity());
		}

		return productRepo.save(productSelected);

	}

	@Override
	public void deleteProduct(int id) {
		productRepo.deleteById(id);
	}

}
