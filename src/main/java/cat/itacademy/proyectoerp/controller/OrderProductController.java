package cat.itacademy.proyectoerp.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderProduct;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.service.OrderServiceImpl;
import cat.itacademy.proyectoerp.service.ProductServiceImpl;

@RestController
@RequestMapping("api/orders_products")
public class OrderProductController {
	
	
	@Autowired
	OrderServiceImpl orderService;
	
	@Autowired
	ProductServiceImpl productService;
	
	
	@PostMapping
	public ResponseEntity<?> listProductsFromOrder(@RequestBody Order order){
		Order orderDb = orderService.findOrderById(order.getId());
		if(orderDb != null) {
			Collection<Product> productList = orderDb.getProducts();
				return new ResponseEntity<>(productList, HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/save_products_order")
	public ResponseEntity<?> saveProductOrder(@RequestBody OrderProduct orderProduct){
		Order orderDb = orderService.findOrderById(orderProduct.getOrder().getId());
		if(orderDb != null) {
			Product productDb = productService.findProductById(orderProduct.getProduct().getId());
			orderDb.addProduct(productDb);
			orderService.addOrder(orderDb);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

}
