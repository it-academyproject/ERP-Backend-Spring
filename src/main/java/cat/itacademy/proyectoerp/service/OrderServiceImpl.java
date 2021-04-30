package cat.itacademy.proyectoerp.service;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.UUID;
import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.domain.TopEmployee;
import cat.itacademy.proyectoerp.domain.DatesTopEmployeePOJO;
import cat.itacademy.proyectoerp.dto.TopEmployeeDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.proyectoerp.repository.IClientRepository;
import cat.itacademy.proyectoerp.repository.IOrderDetailRepository;
import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.repository.IProductRepository;
import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.Employee;
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
				//if (clientRepository.findById(order.getClientId()).isEmpty()) {  //@Dapser75
				if (clientRepository.findById(order.getClient().getid()).isEmpty()) {  //@Dapser75
					throw new ArgumentNotFoundException("The client doesn't exist. The client with the id " + order.getClient() + "doesn't exist");
				} else if (order.getClient() == null) {

/*				if (clientRepository.findById((order.getClientId())).isEmpty()) {  //UUID.fromString //Codigo B49.
					throw new ArgumentNotFoundException("The client doesn't exist. The client with the id " + order.getClientId() + "doesn't exist");
				} else if (order.getClientId() == null) {*/

					throw new ArgumentNotValidException("Invalid Client ID");
				}
				//orderToUpdate.setClient_id(order.getClient()); //
				orderToUpdate.setClient(order.getClient());
				
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
	//public List<Order> findOrdersByClient(Client id) { //Dapser75
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

	@Override
	public List<TopEmployeeDTO> findAllTopTen(DatesTopEmployeePOJO topemployeepojo) {
		
		if (topemployeepojo.getBegin_date() == null) topemployeepojo.setBegin_date(LocalDateTime.of(2020,01,01,00,01)); 
		
		if (topemployeepojo.getEnd_date() == null ) topemployeepojo.setEnd_date(LocalDateTime.now());
		
		List<Object[]> TopEmpl = orderRepository.findEmployeesSalesBetweenDates(topemployeepojo.getBegin_date(),topemployeepojo.getEnd_date());  //Busqueda en BD
	
		TopEmployeeDTO TopEmployDTO = new TopEmployeeDTO();
	
		List<TopEmployeeDTO> topemployeelist = new ArrayList();
		
		for (Object[] object : TopEmpl) {
			TopEmployDTO.setEmployee_id( object[0].toString());
			TopEmployDTO.setTotal(Double.parseDouble(object[1].toString()));
			topemployeelist.add(TopEmployDTO);
			TopEmployDTO = new TopEmployeeDTO();
		}

		return topemployeelist; 
	}
}




