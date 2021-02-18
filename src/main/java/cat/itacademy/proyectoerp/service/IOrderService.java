package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Order;

public interface IOrderService {
	
	
	public Order createOrder(Order order);

	public Order findOrderById(UUID id);

	public List<Order> findAllOrders();
	
	public Order updateOrder(Order order);

	public void deleteOrder(UUID id);
	
	public boolean listIsValid(List<String> productsId);

}
