package cat.itacademy.proyectoerp.service;


import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import cat.itacademy.proyectoerp.domain.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cat.itacademy.proyectoerp.repository.IClientRepository;
import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.repository.IProductRepository;
import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderDetail;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;

@Service
public class OrderServiceImpl implements IOrderService{

	@Autowired
	IOrderRepository iOrderRepository;
	
	@Autowired
	IClientRepository iClientRepository;
	
	@Autowired
	IProductRepository iProductRepository;
	

	@Override
	public Order createOrder(Order order) {
		Optional<Client> tempClient = iClientRepository.findById(UUID.fromString(order.getClientId()));
		long miliseconds = System.currentTimeMillis();
		 Date actualDate = new Date(miliseconds);
		 //checks if data is valid
		if (order.getClientId() == null || tempClient.isEmpty()) {
			throw new ArgumentNotValidException("Order must have a client.");

		} else if (order.getDate().after(actualDate)) {
			throw new ArgumentNotValidException("Order's date can't be set in the future.");

		} else if (order.getDate() == null) {
			throw new ArgumentNotValidException("Order must have a date.");

		} else if (order.getOrderDetails() == null) {
			throw new ArgumentNotValidException("Orders must have a product list.");

		} else if (!listIsValid(order.getOrderDetails())){
			throw new ArgumentNotValidException("Invalid products in the order");
		} else {
			return iOrderRepository.save(order);
		}
		
	}

	@Override
	public Order findOrderById(UUID id) {
		return iOrderRepository.findById(id)
				.orElseThrow(() -> new ArgumentNotFoundException("Order not found. The id " + id + " doesn't exist"));
	}

	@Override
	public List<Order> findAllOrders() {
		if (iOrderRepository.findAll().isEmpty()) {
			throw new ArgumentNotFoundException("No orders found");
		} else {
			return iOrderRepository.findAll();
		}
	}
	
	@Override
	public Order updateOrder(Order order) {
		//checks if it's an existing order
		if (iOrderRepository.findById(order.getId()).isEmpty()) {
			throw new ArgumentNotFoundException("The order doesn't exists. The order with id "+ order.getId()+ " doesn't exist.");
		}
		Order orderToUpdate = findOrderById(order.getId());
		//checks if parameters are valid and updates them
		if (iClientRepository.findById(UUID.fromString(order.getClientId())).isEmpty()) {
			throw new ArgumentNotFoundException("The client doesn't exist. The client with the id " + order.getClientId() + "doesn't exist");
		} else if (order.getClientId() == null) {
			throw new ArgumentNotValidException("Invalid Client ID");
		}
		orderToUpdate.setClientId(order.getClientId());
		
		//TODO: Once Employee is implemented it should check if it exists.1
		orderToUpdate.setEmployeeId(order.getEmployeeId());
		if (order.getStatus() == null) {
			throw new ArgumentNotValidException("Status can't be null");
		}
		orderToUpdate.setStatus(order.getStatus());
		if (listIsValid(order.getOrderDetails())) {
			orderToUpdate.setOrderDetail(order.getOrderDetails());
		} else {
			throw new ArgumentNotValidException("Invalid products in the order");
		}
		
		return createOrder(orderToUpdate);
	}

	@Override
	public void deleteOrder(UUID id) {
		iOrderRepository.deleteById(id);
		
	}

	@Override
	public boolean listIsValid(List<OrderDetail> orderDetails) {
		boolean answer = true;
		for (OrderDetail product : orderDetails) {
			Optional<Product> tempProduct = iProductRepository.findById(product.getProduct().getId());
			if (tempProduct.isEmpty()) {
				answer = false;
			}
		}
		return answer;
	}

	@Override
	public List<Order> findOrdersByStatus(OrderStatus status) {
		if(iOrderRepository.findOrdersByStatus(status) == null){
			throw new ArgumentNotFoundException("No orders found");
		} else{
			return iOrderRepository.findOrdersByStatus(status);
		}
	}

	@Override
	public List<Order> findOrdersByClient(String id) {
		if(iOrderRepository.findOrdersByClientId(id) == null){
			throw new ArgumentNotFoundException("No orders with client " + id + " found");
		} else{
			return iOrderRepository.findOrdersByClientId(id);
		}
	}
}


