package cat.itacademy.proyectoerp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

	Long countByName(String name);
	
	Product findByName(String name);

	boolean existsByName(String name);
	
	List<Product> findAllByCategoryName(String name);
	
	List<Product> findAllByOrderByPriceDesc();
	
	List<Product> findAllByOrderByPriceAsc();
}
