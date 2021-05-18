package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.service.OrderServiceImpl;

@RestController
@RequestMapping("/api")
public class OrderController {
	
	//ACORDARSE DE ACTUALIZAR EL RUNNER

	@Autowired
	OrderServiceImpl orderService;
	
	String success = "success";
	String message = "message";
	String isFalse = "false";
	String error = "Error: ";	
	
	/**
	 * Create a new order
	 * 
	 * @param order order
	 * @return new order 
	 */
	@PostMapping("/orders")
	public Map<String, Object> createOrder(@RequestBody Order order) {
	//public Map<String, Object> createOrder(@RequestBody orderPOJO neworder) {

		HashMap<String, Object> map = new HashMap<>();
//
		try {
			
			order.setStatus(OrderStatus.UNASSIGNED);
			orderService.createOrder(order);

			map.put(success, "true");
			map.put(message, "Order created");
			map.put("order", order);

		} catch (Exception e) {

			map.put(success, isFalse);
			map.put(message, error + e.getMessage());
		}

		return map;
	}
	   
	
	@GetMapping("/orders/{id}")
	public Map<String, Object> findOrderById(@PathVariable(name = "id") UUID id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			Order order = orderService.findOrderById(id);
			map.put(success, "true");
			map.put(message, "order found");
			map.put("order", order);
		} catch (Exception e) {
			map.put(success, isFalse);
			map.put(message, error + e.getMessage());
		}
		return map;
	}


	@GetMapping("/orders")
	public HashMap<String, Object> findOrders() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Order> ordersList = orderService.findAllOrders();
		try {
			map.put("success", "true");
			map.put("message", "order found");
			map.put("order", ordersList);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}
		return map;
	}


	
	@DeleteMapping("/orders")
	public Map<String, Object> deleteOrder(@RequestBody Order order) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			orderService.findOrderById(order.getId());
			orderService.deleteOrder(order.getId());

			map.put(success, "true");
			map.put(message, "Order with id: " + order.getId() + " has been deleted");

		} catch (Exception e) {

			map.put(success, isFalse);
			map.put(message, error + e.getMessage());

		}
		return map;
	}

}