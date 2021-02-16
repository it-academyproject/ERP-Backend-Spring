package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.domain.Order;

@Service
public class OrderServiceImpl implements IOrderService{

	@Autowired
	IOrderRepository iOrderRepository;
	
	@Override
	public List<String> getAllProducts(UUID orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order createOrder(Order order) {
		return iOrderRepository.save(order);
	}

	@Override
	public Order findOrderById(UUID id) {
		return iOrderRepository.getOne(id);
	}

	@Override
	public List<Order> findAllOrders() {
		return iOrderRepository.findAll();
	}
	
	@Override
	public Order updateOrder(Order order) {
		Order orderToUpdate = findOrderById(order.getId());
		orderToUpdate.setClientId(order.getClientId());
		orderToUpdate.setEmployeeId(order.getEmployeeId());
		orderToUpdate.setStatus(order.getStatus());
		orderToUpdate.setProducts(order.getProducts());
		return createOrder(orderToUpdate);
	}

	@Override
	public void deleteOrder(UUID id) {
		iOrderRepository.deleteById(id);
		
	}



}
