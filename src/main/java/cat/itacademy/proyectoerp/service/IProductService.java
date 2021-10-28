package cat.itacademy.proyectoerp.service;

import java.util.List;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.dto.ProductDTO;
import cat.itacademy.proyectoerp.dto.ProductStatsDTO;

public interface IProductService {

	public ProductDTO createProduct(ProductDTO productDto);

	public List<ProductDTO> getProducts();
	
	public List<ProductDTO> getByCategoryName(String name);

	public Product findProductById(int id);

	public ProductDTO updateProduct(Product product);

	public void deleteProduct(int id);

	public ProductStatsDTO getMaxPrice();

	public ProductStatsDTO getMinPrice();
}
