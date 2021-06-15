package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

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
import cat.itacademy.proyectoerp.dto.CategoryDTO;
import cat.itacademy.proyectoerp.dto.ProductDTO;
import cat.itacademy.proyectoerp.service.ICategoryService;
import cat.itacademy.proyectoerp.service.ProductServiceImpl;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	ProductServiceImpl productService;

	@Autowired
	ICategoryService categoryService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public HashMap<String, Object> createProduct(@RequestBody ProductDTO productDto) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ProductDTO product = productService.createProduct(productDto);
			map.put("success", "true");
			map.put("message", "product created");
			map.put("product", product);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}
		return map;
	}

	
	@GetMapping("/{id}")
	public HashMap<String, Object> findProductById(@PathVariable(name = "id") int id) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			ProductDTO product = productService.getProductById(id);

			map.put("success", "true");
			map.put("message", "product found");
			map.put("product", product);

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}

		return map;
	}

	@GetMapping
	public HashMap<String, Object> getProducts() {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			List<ProductDTO> productList = productService.getProducts();

			map.put("success", "true");
			map.put("message", "product list found");
			map.put("products", productList);

		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}

		return map;
	}
	
	@GetMapping("/category/{categoryName}")
	public HashMap<String, Object> getProductsByCategoryName(@PathVariable String categoryName) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			List<ProductDTO> productList = productService.getProductsByCategoryName(categoryName);
			map.put("success", "true");
			map.put("message", "product list found");
			map.put("products", productList);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public HashMap<String, Object> updateProduct(@RequestBody Product product) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			long ts = timestamp.getTime();
			product.setModified(ts);
			ProductDTO productUpdated = productService.updateProduct(product);

			map.put("success", "true");
			map.put("message", "product updated");
			map.put("product", productUpdated);

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());

		}

		return map;
	}

	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping
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
