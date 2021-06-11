package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.dto.TopEmployeeDTO;
import cat.itacademy.proyectoerp.domain.DatesTopEmployeePOJO;
import cat.itacademy.proyectoerp.dto.OrderDTO;
import cat.itacademy.proyectoerp.dto.CreateOrderDTO;
import cat.itacademy.proyectoerp.dto.EmployeeSalesDTO;

public interface IOrderService {
	
	public OrderDTO createOrder(CreateOrderDTO createOrderDTO) throws Exception;
	 
	public Order findOrderById(UUID id);

	public List<OrderDTO> findAllOrders();
	
	public void updateOrder(Order order);

	public void deleteOrder(UUID id);

	public List<Order> findOrdersByStatus(OrderStatus status);

	public List<Order> findOrdersByClient(String id);
	
	public List<Order> findOrdersByEmployeeId(String employeeId);
	
	public List<TopEmployeeDTO> findAllTopTen(DatesTopEmployeePOJO topemployee);
	

	EmployeeSalesDTO getBestEmployeeByTotalSales();

	EmployeeSalesDTO getWorstEmployeeByTotalSales();
	
}
