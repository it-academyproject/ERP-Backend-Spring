package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
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

	/**
	 * Create a new order
	 * 
	 * @param order order
	 * @return new order 
	 */
	@PostMapping("/orders")
	public HashMap<String, Object> createOrder(@RequestBody Order order) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			orderService.createOrder(order);

			map.put("success", "true");
			map.put("message", "Order created");
			map.put("order", order);

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
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
	public HashMap<String, Object> findOrderById(@PathVariable(name = "id") UUID id) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			Order order = orderService.findOrderById(id);

			map.put("success", "true");
			map.put("message", "order found");
			map.put("order", order);

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
	public HashMap<String, Object> updateOrder(@RequestBody Order order) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			Order updatedOrder = orderService.updateOrder(order);

			map.put("success", "true");
			map.put("message", "order updated");
			map.put("product", updatedOrder);

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());

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
	public HashMap<String, Object> deleteOrder(@RequestBody Order order) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			orderService.findOrderById(order.getId());

			orderService.deleteOrder(order.getId());

			map.put("success", "true");
			map.put("message", "Order with id: " + order.getId() + " has been deleted");

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());

		}

		return map;
	}

}


