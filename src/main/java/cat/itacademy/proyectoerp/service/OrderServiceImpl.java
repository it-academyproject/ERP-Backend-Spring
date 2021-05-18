package cat.itacademy.proyectoerp.service;



import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.dto.EmployeeSalesDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.proyectoerp.repository.IClientRepository;
import cat.itacademy.proyectoerp.repository.IOrderDetailRepository;
import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.repository.IProductRepository;
import cat.itacademy.proyectoerp.domain.Order;

import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;

@Service
public class OrderServiceImpl implements IOrderService{
	
	@Autowired
	IOrderRepository orderRepository;

	@Autowired
	IClientRepository clientRepository;

	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	IOrderDetailRepository orderDetailRepository;
	
	@Autowired
	IEmployeeService employeeService;

	
	@Override
	//@Transactional
	public Order createOrder(Order order) {  //UUID
		// Order newOrder = orderRepository.save(order);
		    return orderRepository.save(order); // newOrder.getId();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Order findOrderById(UUID id) {
		return orderRepository.findById(id)
				.orElseThrow(() -> new ArgumentNotFoundException("Order not found. The id " + id + " doesn't exist"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Order> findAllOrders() {
		if (orderRepository.findAll().isEmpty()) {
			throw new ArgumentNotFoundException("No orders found");
		} else {
			return orderRepository.findAll();
		}
	}

	@Override
	@Transactional
	public void updateOrder(Order order) {
		//checks if it's an existing order
				if (orderRepository.findById(order.getId()).isEmpty()) {
					throw new ArgumentNotFoundException("The order doesn't exists. The order with id "+ order.getId()+ " doesn't exist.");
				}
				Order orderToUpdate = findOrderById(order.getId());
				//checks if parameters are valid and updates them
				if (clientRepository.findById((order.getClientId())).isEmpty()) {  //UUID.fromString
					throw new ArgumentNotFoundException("The client doesn't exist. The client with the id " + order.getClientId() + "doesn't exist");
				} else if (order.getClientId() == null) {
					throw new ArgumentNotValidException("Invalid Client ID");
				}
				orderToUpdate.setClient_id(order.getClientId());
				
				//TODO: Once Employee is implemented it should check if it exists.1
				orderToUpdate.setEmployee_id(order.getEmployeeId());
				if (order.getStatus() == null) {
					throw new ArgumentNotValidException("Status can't be null");
				}
/*				orderToUpdate.setStatus(order.getStatus());
				if(!order.getOrderProducts().isEmpty()){
					orderToUpdate.setOrderProducts(order.getOrderProducts());
				} else {
					throw new ArgumentNotValidException("Invalid products in the order");
				}
*/				
				orderRepository.save(orderToUpdate);
	}

	@Override
	public void deleteOrder(UUID id) {
		orderRepository.deleteById(id);
	}

	@Override
	public List<Order> findOrdersByStatus(OrderStatus status) {
		if(orderRepository.findOrdersByStatus(status) == null){
			throw new ArgumentNotFoundException("No orders found");
		} else{
			return orderRepository.findOrdersByStatus(status);
		}
	}

	@Override
	public List<Order> findOrdersByClient(String id) {
		if(orderRepository.findOrdersByClientId(id) == null){
			throw new ArgumentNotFoundException("No orders with client " + id + " found");
		} else{
			return orderRepository.findOrdersByClientId(id);
		}
	}

	@Override
	public List<Order> findOrdersByEmployeeId(String employeeId) {
		if(orderRepository.findOrdersByEmployeeId(employeeId) == null){
			throw new ArgumentNotFoundException("The employee with id: " + employeeId + " does not have orders assigned");
		} else{
			return orderRepository.findOrdersByEmployeeId(employeeId);
		}
	}
	
	/**
	 * This method gets the best Employee by the sum of all completed sales and returns it's EmployeeSalesDTO
	 */
	@Override
	public EmployeeSalesDTO getBestEmployeeByTotalSales() {
		Map<UUID, Double> mapEmployeesTotalSales = getMapOfEmployeesIdAndTotalSales();
		UUID bestEmployeeId = Collections.max(mapEmployeesTotalSales.entrySet(), Map.Entry.comparingByValue()).getKey();
		Double totalSales = mapEmployeesTotalSales.get(bestEmployeeId);
		return mapEmployeeSalesDTO(bestEmployeeId, totalSales);
	}
	
	/**
	 * This method gets the worst Employee by the sum of all completed sales and returns it's EmployeeSalesDTO
	 */
	@Override
	public EmployeeSalesDTO getWorstEmployeeByTotalSales() {
		Map<UUID, Double> mapEmployeesTotalSales = getMapOfEmployeesIdAndTotalSales();
		UUID worstEmployeeId = Collections.min(mapEmployeesTotalSales.entrySet(), Map.Entry.comparingByValue()).getKey();
		Double totalSales = mapEmployeesTotalSales.get(worstEmployeeId);
		return mapEmployeeSalesDTO(worstEmployeeId, totalSales);
	}	
	
	/**
	 * This method returns a map of Employee Id as key and the sum of all completed sales by that employee as value
	 * @throws ArgumentNotFoundException if there are no orders completed
	 * @return Map<UUID, Double>
	 */
	private Map<UUID, Double> getMapOfEmployeesIdAndTotalSales() {
		OrderStatus status = OrderStatus.COMPLETED;
		if(orderRepository.findAllByStatus(status).isEmpty()) throw new ArgumentNotFoundException("There are no completed orders");
		Map<UUID, Double> mapEmployeesTotalSales = orderRepository.findAllByStatus(status).stream()
				.collect(
						Collectors.groupingBy(Order::getEmployeeId,
								Collectors.summingDouble(Order::getTotal)));
		return mapEmployeesTotalSales;
	}
	
	/**
	 * This method maps an Employee (from it's Id) and the total value of that employee's sales to an EmployeeSalesDTO
	 * @param employeeId Id of the Employee to map
	 * @param totalSales Sum of all of employee's completed sales
	 * @return EmployeeSalesDTO
	 */
	private EmployeeSalesDTO mapEmployeeSalesDTO(UUID employeeId, Double totalSales) {
		EmployeeSalesDTO employeeSalesDTO = new EmployeeSalesDTO();
		employeeSalesDTO.setEmployee(employeeService.findEmployeeById(employeeId));
		employeeSalesDTO.setTotalSales(totalSales);
		return employeeSalesDTO;
	}

}




