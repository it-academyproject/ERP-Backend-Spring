package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.dto.CreateOrderDTO;
import cat.itacademy.proyectoerp.dto.MessageDTO;
import cat.itacademy.proyectoerp.dto.OrderDTO;
import cat.itacademy.proyectoerp.dto.OrderStatusDTO;
import cat.itacademy.proyectoerp.service.OrderServiceImpl;

@RestController
@RequestMapping("/api")
public class OrderController {

	// ACORDARSE DE ACTUALIZAR EL RUNNER

	@Autowired
	OrderServiceImpl orderService;

	String success = "success";
	String message = "message";
	String isFalse = "false";
	String error = "Error: ";

	/**
	 * Create a new order
	 * 
	 * @param CreateOrderDTO
	 * @return OrderDTO
	 */
	@PostMapping("/orders")
	public Map<String, Object> createOrder(@RequestBody CreateOrderDTO createOrderDTO,
			@RequestHeader("authorization") String token) {
		HashMap<String, Object> map = new HashMap<>();

		try {
			OrderDTO newOrder = orderService.createOrder(createOrderDTO, token);
			map.put(success, "true");
			map.put(message, "Order created");
			map.put("order", newOrder);
		} catch (Exception e) {
			map.put(success, isFalse);
			map.put(message, error + e.getMessage());
		}
		return map;
	}

	@GetMapping("/orders/{id}")
	public ResponseEntity<MessageDTO> findOrderById(@PathVariable(name = "id") UUID id) {
		MessageDTO output;
		try {
			Order order = orderService.findOrderById(id);
			if (order == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			output = new MessageDTO("true", "order successfully retrieved.", order);
			return ResponseEntity.status(HttpStatus.OK).body(output);		
		} catch (Exception e) {
			output = new MessageDTO("False", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(output);
		}
	}

	@GetMapping("/orders")
	public ResponseEntity<MessageDTO> findOrders() {
		MessageDTO output;
		try {
			List<OrderDTO> ordersList = orderService.findAllOrders();
			if (ordersList.size() == 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			output = new MessageDTO("true", "list of orders successfully retrieved.", ordersList);
			return ResponseEntity.status(HttpStatus.OK).body(output);			
		} catch (Exception e) {
			output = new MessageDTO("False", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(output);
		}
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
	
	/**
	 * 
	 * Updates the status of an order by its ID. 
	 * 
	 * @param orderId
	 * @param orderStatusDTO
	 * @return
	 */
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	@PutMapping("/orders/{orderId}/status")
	public ResponseEntity<?> updateOrderStatus(@PathVariable UUID orderId, @RequestBody OrderStatusDTO orderStatusDTO) {
		MessageDTO messageDTO;
		
		try {
			Order updatedOrder = orderService.updateOrderStatus(orderId, orderStatusDTO.getStatus());
			messageDTO = new MessageDTO("true", "Order status updated", updatedOrder);
		} catch(Exception e) {
			messageDTO = new MessageDTO("false", e.getMessage());
			return ResponseEntity.badRequest().body(messageDTO);
		}
		
		return ResponseEntity.ok(messageDTO);
	}

}