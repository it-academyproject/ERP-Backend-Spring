package cat.itacademy.proyectoerp.service;

import java.util.List;

import cat.itacademy.proyectoerp.domain.Product;

public interface IProductService {

	public Product createProduct(Product product); // CREATE - create new product

	public List<Product> getProducts(); // READ - read product list data

	public Product findProductById(int id); // READ - read data of a product according to id

	public Product updateProduct(Product product); // UPDATE - update product data

	public void deleteProduct(int id); // DELETE - delete product according to id

}
