package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderStatus;

public interface IOrderService {


	public Order createOrder(Order order);  //UUID
	 
	public Order findOrderById(UUID id);

	public List<Order> findAllOrders();
	
	public void updateOrder(Order order);

	public void deleteOrder(UUID id);

	public List<Order> findOrdersByStatus(OrderStatus status);

	public List<Order> findOrdersByClient(String id);
	
	public List<Order> findOrdersByEmployeeId(String employeeId);

}
