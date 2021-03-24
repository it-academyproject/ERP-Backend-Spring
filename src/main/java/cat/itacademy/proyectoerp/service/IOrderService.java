package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.validation.annotation.Validated;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderDetail;
import cat.itacademy.proyectoerp.domain.OrderStatus;

@Validated
public interface IOrderService {

	public Order createOrder(Order order);

	public Order findOrderById(UUID id);

	public List<Order> findAllOrders();
	
	public Order updateOrder(Order order);

	public void deleteOrder(UUID id);
	
	public boolean listIsValid(List<OrderDetail> orderDetails);

	public List<Order> findOrdersByStatus(OrderStatus status);

	public List<Order> findOrdersByClient(String id);

}
