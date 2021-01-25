package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.service.ProductServiceImpl;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	ProductServiceImpl productService;

	/**
	 * Create a new product
	 * 
	 * @param product product data
	 * @return new product data
	 */
	@PostMapping("/products")
	public HashMap<String, Object> createProduct(@RequestBody Product product) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			productService.createProduct(product);

			map.put("success", "true");
			map.put("message", "New product created");
			map.put("new product", product);

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}

		return map;
	}

	/**
	 * Get product data according to id
	 * 
	 * @param id product id
	 * @return product data according to id
	 */
	@GetMapping("/products/{id}")
	public HashMap<String, Object> findProductById(@PathVariable(name = "id") int id) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			Product product = productService.findProductById(id);

			map.put("success", "true");
			map.put("message", "product found");
			map.put("product", product);

		} catch (Exception idNotFound) {

			map.put("success", "false");
			map.put("message", "Product not found. The id " + id + " doesn't exist");

		}

		return map;
	}

	/**
	 * Get list of products
	 * 
	 * @return product list
	 */
	@GetMapping("/products")
	public HashMap<String, Object> getProducts() {

		HashMap<String, Object> map = new HashMap<String, Object>();

		List<Product> productList = productService.getProducts();

		// If the size of the product list is greater than 0, return list
		if (productList.size() > 0) {

			map.put("success", "true");
			map.put("message", "product list found");
			map.put("all products", productList);

		} else {
			map.put("success", "false");
			map.put("message", "No products found");
		}

		return map;
	}

	/**
	 * Update product data
	 * 
	 * @param id      product id
	 * @param product product new data
	 * @return product updated
	 */
	@PutMapping("/products/{id}")
	public HashMap<String, Object> updateProduct(@PathVariable(name = "id") int id, @RequestBody Product product) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			
			Product productSelected = productService.findProductById(id);

			// If the name has been changed, verify that the name is unique
			if (!productSelected.getName().equalsIgnoreCase(product.getName())) {

				Product productFound = productService.findByName(product.getName());

				if (productFound != null) {

					map.put("success", "false");
					map.put("message", "Product name already exists");

					return map;
				}
			}

			productSelected.setName(product.getName());
			productSelected.setStock(product.getStock());
			productSelected.setImage(product.getImage());
			productSelected.setFamily(product.getFamily());
			productSelected.setPrice(product.getPrice());
			productSelected.setVat(product.getVat());
			productSelected.setWholesale_price(product.getWholesale_price());
			productSelected.setWholesale_quantity(product.getWholesale_quantity());

			Product productUpdated = productService.updateProduct(productSelected);

			map.put("success", "true");
			map.put("message", "product updated");
			map.put("product updated", productUpdated);

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());

		}

		return map;
	}

	/**
	 * Delete product according to id
	 * 
	 * @param id product id
	 * @return message: product removed successfully
	 */
	@DeleteMapping("/products/{id}")
	public HashMap<String, Object> deleteProduct(@PathVariable(name = "id") int id) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			productService.deleteProduct(id);

			map.put("success", "true");
			map.put("message", "Product " + id + " has been successfully deleted");

		} catch (Exception idNotFound) {

			map.put("success", "false");
			map.put("message", "Product not found. The id " + id + " doesn't exist");

		}

		return map;
	}

}
