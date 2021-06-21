package cat.itacademy.proyectoerp.service;

import java.util.List;

import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.dto.ProductDTO;

public interface IProductService {

	public ProductDTO createProduct(ProductDTO productDto);

	public List<ProductDTO> getProducts();
	
	public List<ProductDTO> getProductsByCategoryName(String name);

	public Product findProductById(int id);

	public ProductDTO updateProduct(Product product);

	public void deleteProduct(int id);

}
