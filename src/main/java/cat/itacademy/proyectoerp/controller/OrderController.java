package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.dto.CreateOrderDTO;
import cat.itacademy.proyectoerp.dto.OrderDTO;
import cat.itacademy.proyectoerp.helpers.Responsehelper;
import cat.itacademy.proyectoerp.service.OrderServiceImpl;

@RestController
@RequestMapping("/api")
public class OrderController {
	
	//ACORDARSE DE ACTUALIZAR EL RUNNER

	@Autowired
	OrderServiceImpl orderService;
	@Autowired
	Responsehelper responsehelper;
		
	
	/**
	 * Create a new order
	 * 
	 * @param CreateOrderDTO
	 * @return OrderDTO
	 */
	@PostMapping("/orders")
	public Map<String, Object> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			OrderDTO newOrder = orderService.createOrder(createOrderDTO);
			map.putAll(responsehelper.responseWasOkWithEntity("true", "Order created", "order", newOrder));
			
		} catch (Exception e) {
			map.putAll(responsehelper.responsewaswrong("false", e.getMessage()));
			
		}
		return map;
	}
	   
	
	@GetMapping("/orders/{id}")
	public Map<String, Object> findOrderById(@PathVariable(name = "id") UUID id) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			Order order = orderService.findOrderById(id);
			map.putAll(responsehelper.responseWasOkWithEntity("true", "Order found", "order", order));
			
		} catch (Exception e) {
			map.putAll(responsehelper.responsewaswrong("false", e.getMessage()));
		}
		return map;
	}


	@GetMapping("/orders")
	public HashMap<String, Object> findOrders() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			List<OrderDTO> ordersList = orderService.findAllOrders();
			map.putAll(responsehelper.responseWasOkWithEntity("true", "Orders found", "orders", ordersList));
			
		} catch (Exception e) {
			map.putAll(responsehelper.responsewaswrong("false", e.getMessage()));
		}
		return map;
	}
	
	
	


	
	@DeleteMapping("/orders")
	public Map<String, Object> deleteOrder(@RequestBody Order order) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			orderService.findOrderById(order.getId());
			orderService.deleteOrder(order.getId());
			
			map.putAll(responsehelper.responseSimpleWasOk("true", "Order with id: " + order.getId() + " has been deleted"));
			

		} catch (Exception e) {

			map.putAll(responsehelper.responsewaswrong("false", e.getMessage()));

		}
		return map;
	}

}