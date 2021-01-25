package cat.itacademy.proyectoerp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.dao.IProductDAO;
import cat.itacademy.proyectoerp.domain.Product;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	IProductDAO productRepo;

	// Method to find a product by name
	public Product findByName(String name) {
		return productRepo.findByName(name);
	}

	@Override
	public Product createProduct(Product product) throws IllegalArgumentException {

		if (productRepo.countByName(product.getName()) != 0) {
			throw new IllegalArgumentException("Product name already exists");

		} else if (product.getName().isEmpty()) {
			throw new IllegalArgumentException("The product name cannot be empty");

		} else if (product.getWholesale_price() > product.getPrice()) {
			throw new IllegalArgumentException("The wholesale price cannot be higher than price");

		} else {
			return productRepo.save(product);
		}
	}

	@Override
	public List<Product> getProducts() {
		return productRepo.findAll();
	}

	@Override
	public Product findProductById(int id) {
		return productRepo.findById(id).get();
	}

	@Override
	public Product updateProduct(Product product) throws IllegalArgumentException {

		if (product.getName().isEmpty()) {
			throw new IllegalArgumentException("The product name cannot be empty");

		} else if (product.getWholesale_price() > product.getPrice()) {
			throw new IllegalArgumentException("The wholesale price cannot be higher than price");

		} else {
			return productRepo.save(product);
		}

	}

	@Override
	public void deleteProduct(int id) {
		productRepo.deleteById(id);
	}

}
