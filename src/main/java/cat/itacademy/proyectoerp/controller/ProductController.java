package cat.itacademy.proyectoerp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ProductController {
	
	@PostMapping("/products")
	public String createProduct(@RequestBody String name) {
		return "Product "+ name + " is created!";
	}
	
	@GetMapping("/products/{id}")
	public String findProductById(@PathVariable(name = "id") int id) {
		return "In the future you will receive data from product id: "+ id;
	}
	
	@GetMapping("/products")
	public String getProducts() {
		return "In the future you will receive a list of all products with their data";
	}
	
	@PutMapping("/products/{id}")
	public String updateProduct(@PathVariable(name = "id") int id, @RequestBody String name) {
		return "in the future you will receive the modified data of product " + id;
	}
	
	@DeleteMapping("/products/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		return "Product "+ id + " has been successfully deleted";
	}

	
}
