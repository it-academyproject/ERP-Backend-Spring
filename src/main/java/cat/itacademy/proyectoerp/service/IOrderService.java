package cat.itacademy.proyectoerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.security.auth.message.AuthException;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.dto.TopEmployeeDTO;
import cat.itacademy.proyectoerp.domain.DatesTopEmployeePOJO;
import cat.itacademy.proyectoerp.dto.OrderDTO;
import cat.itacademy.proyectoerp.dto.CreateOrderDTO;
import cat.itacademy.proyectoerp.dto.EmployeeSalesDTO;

public interface IOrderService {
	
	public OrderDTO createOrder(CreateOrderDTO createOrderDTO, String token) throws  AuthException;
	 
	public Order findOrderById(UUID id);

	public List<OrderDTO> findAllOrders();
	
	public void updateOrder(Order order);

	public void deleteOrder(UUID id);

	public List<Order> findOrdersByStatus(OrderStatus status);			

	public List<Order> findOrdersByClient(UUID id);
	
	public List<Order> findOrdersByEmployeeId(UUID employeeId);	
	
	public List<TopEmployeeDTO> findAllTopTen(DatesTopEmployeePOJO topemployee);
	
	public EmployeeSalesDTO getBestEmployeeByTotalSales();

	public EmployeeSalesDTO getWorstEmployeeByTotalSales();
	
	public double getProfitByYear(int year);

	public double getProfitByMonth(int year, int month);

	public HashMap<String, Long> countOrdersByfield(String field);
	
	public Order updateOrderStatus(UUID orderId, OrderStatus orderStatus);
	
}
