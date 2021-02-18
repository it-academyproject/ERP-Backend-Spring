package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.service.OrderServiceImpl;

@RestController
@RequestMapping("/api")
public class OrderController {

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

		HashMap<String, Object> map = new HashMap<>();

		try {
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

	/**
	 * Get order by id
	 * 
	 * @param id order id
	 * @return order by id
	 */
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


	/**
	 * Get all orders
	 *
	 * @return list of orders
	 */
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

	/**
	 * Update order
	 * 
	 * @param order
	 * @return order updated
	 */
	@PutMapping("/order")
	public Map<String, Object> updateOrder(@RequestBody Order order) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			Order updatedOrder = orderService.updateOrder(order);

			map.put(success, "true");
			map.put(message, "order updated");
			map.put("product", updatedOrder);
		} catch (Exception e) {

			map.put(success, isFalse);
			map.put(message, error + e.getMessage());

		}
		return map;
	}

	/**
	 * Delete order by id
	 * 
	 * @param order id
	 * @return message
	 */
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


