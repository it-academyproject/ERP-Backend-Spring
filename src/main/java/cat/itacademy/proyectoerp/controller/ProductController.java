package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/products")
	public HashMap<String, Object> createProduct(@RequestBody Product product) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			productService.createProduct(product);

			map.put("success", "true");
			map.put("message", "New product created");
			map.put("product", product);

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}

		return map;
	}

	
	@GetMapping("/products/{id}")
	public HashMap<String, Object> findProductById(@PathVariable(name = "id") int id) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			Product product = productService.findProductById(id);

			map.put("success", "true");
			map.put("message", "product found");
			map.put("product", product);

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}

		return map;
	}

	@GetMapping("/products")
	public HashMap<String, Object> getProducts() {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			List<Product> productList = productService.getProducts();

			map.put("success", "true");
			map.put("message", "product list found");
			map.put("products", productList);

		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}

		return map;
	}

	
	//@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/products")
	public HashMap<String, Object> updateProduct(@RequestBody Product product) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			Product productUpdated = productService.updateProduct(product);

			map.put("success", "true");
			map.put("message", "product updated");
			map.put("product", productUpdated);

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());

		}

		return map;
	}

	
	//@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/products")
	public HashMap<String, Object> deleteProduct(@RequestBody Product product) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			productService.findProductById(product.getId());

			productService.deleteProduct(product.getId());

			map.put("success", "true");
			map.put("message", "Product id: " + product.getId() + " has been successfully deleted");

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());

		}

		return map;
	}

}
