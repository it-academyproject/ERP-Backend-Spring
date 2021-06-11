package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderDetail;
import cat.itacademy.proyectoerp.domain.Product;

public interface IOrderDetailService {
	
	public OrderDetail createOrderDetail(Order order, Product product, Integer quantity) throws Exception;
	 
	public OrderDetail findOrderDetailById(UUID id);
	 
	public List<OrderDetail> findAllOrderDetail();
	 
	public OrderDetail updateOrderDetail (OrderDetail orderDetail);

	public void deleteOrderDetail(UUID id);
}

