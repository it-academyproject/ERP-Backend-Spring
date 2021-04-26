package cat.itacademy.proyectoerp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

	// Count number of records by name
	Long countByName(String name);
	
	//Find product by name
	Product findByName(String name);
}
