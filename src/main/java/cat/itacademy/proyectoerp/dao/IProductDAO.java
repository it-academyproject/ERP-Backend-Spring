package cat.itacademy.proyectoerp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.proyectoerp.domain.Product;

public interface IProductDAO extends JpaRepository<Product, Integer> {

	// Count number of records by name
	Long countByName(String name);
	
	//Find product by name
	Product findByName(String name);
}
