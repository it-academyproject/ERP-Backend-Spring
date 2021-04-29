package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.OrderDetail;

public interface IOrderDetailService {
	
	public OrderDetail createOrderDetail(OrderDetail orderDetail);
	 
	public OrderDetail findOrderDetailById(UUID id);
	 
	public List<OrderDetail> findAllOrderDetail();
	 
	public OrderDetail updateOrderDetail (OrderDetail orderDetail);

	public void deleteOrderDetail(UUID id);
}

