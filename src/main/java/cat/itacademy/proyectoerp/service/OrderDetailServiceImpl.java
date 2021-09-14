package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderDetail;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.repository.IOrderDetailRepository;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
	
	@Autowired
	IOrderDetailRepository iOrderDetailRepository;
	
	@Override
	public OrderDetail createOrderDetail(int quantity, Product product, Order order) {
		double subtotal = calculateSubtotalFromProductAndQuantity(quantity, product);
		return new OrderDetail(quantity, subtotal, product, order);
	}
	
	private double calculateSubtotalFromProductAndQuantity(int quantity, Product product) {
		double price;
		if(quantity >= product.getWholesaleQuantity())
			price = product.getWholesalePrice();
		else
			price = product.getPrice();
		return (price + price * product.getVat() / 100) * quantity;
	}
	
	@Override
	public OrderDetail findOrderDetailById(UUID id) {
		return iOrderDetailRepository.findById(id)
				.orElseThrow(() -> new ArgumentNotFoundException("OrderDetail not found. The id " + id + " doesn't exist"));
	}
	
	@Override
	public List<OrderDetail> findAllOrderDetail() {
		if(iOrderDetailRepository.findAll().isEmpty()){
			throw new ArgumentNotFoundException("No OrderDetails found");
		}
		return iOrderDetailRepository.findAll();
	}
	
	@Override
	public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
		if(iOrderDetailRepository.findById(orderDetail.getId()) == null){
			throw new ArgumentNotFoundException("No OrderDetail found");
		}
		return iOrderDetailRepository.save(orderDetail);
	}
	
	@Override
	public void deleteOrderDetailById(UUID id) {
		iOrderDetailRepository.deleteById(id);
	}
	
}
