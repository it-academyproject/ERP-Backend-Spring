package cat.itacademy.proyectoerp.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
	public HashMap<String, Object> create(@RequestBody ProductDTO productDto) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			ProductDTO product = productService.createProduct(productDto);
			
			map.put("success", "true");
			map.put("message", "product created");
			map.put("product", product);
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@GetMapping
	public HashMap<String, Object> getAll() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			List<ProductDTO> productList = productService.getProducts();
			
			map.put("success", "true");
			map.put("message", "products found");
			map.put("products", productList);
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@GetMapping("/{id}")
	public HashMap<String, Object> findById(@PathVariable int id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			ProductDTO product = productService.getProductById(id);
			
			map.put("success", "true");
			map.put("message", "product found");
			map.put("product", product);
			
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@GetMapping("/category/{name}")
	public HashMap<String, Object> getByCategoryName(@PathVariable String name) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			List<ProductDTO> productList = productService.getByCategoryName(name);
			
			map.put("success", "true");
			map.put("message", "products found");
			map.put("products", productList);
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public HashMap<String, Object> update(@RequestBody Product product) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			long ts = timestamp.getTime();
			product.setModified(ts);
			
			ProductDTO productUpdated = productService.updateProduct(product);
			
			map.put("success", "true");
			map.put("message", "product updated");
			map.put("product", productUpdated);
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping
	public HashMap<String, Object> delete(@RequestBody Product product) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			productService.findProductById(product.getId());
			
			productService.deleteProduct(product.getId());
			
			map.put("success", "true");
			map.put("message", "product deleted");
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
			
		}
		
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/categories")
	public HashMap<String, Object> createCategory(@RequestBody CategoryDTO categoryDto) {
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			CategoryDTO category = categoryService.create(categoryDto);
			
			map.put("success", "true");
			map.put("message", "category created");
			map.put("category", category);
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/categories")
	public HashMap<String, Object> getCategory() {
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			List<CategoryDTO> categoryDtos = categoryService.read();
			
			map.put("success", "true");
			map.put("message", "categories found");
			map.put("categories", categoryDtos);
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/categories/{id}")
	public HashMap<String, Object> getCategoryById(@PathVariable UUID id) {
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			CategoryDTO category = categoryService.readById(id);
			
			map.put("success", "true");
			map.put("message", "category found");
			map.put("category", category);
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/categories/{id}")
	public HashMap<String, Object> updateCategoryById(@RequestBody CategoryDTO categoryDto) {
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			CategoryDTO category = categoryService.update(categoryDto);
			
			map.put("success", "true");
			map.put("message", "category updated");
			map.put("category", category);
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/categories")
	public HashMap<String, Object> deleteCategoryById(@RequestBody CategoryDTO categoryDto) {
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			categoryService.delete(categoryDto);
			
			map.put("success", "true");
			map.put("message", "category deleted");
		} catch(Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
}
