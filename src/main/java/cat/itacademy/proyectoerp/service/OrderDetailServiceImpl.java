package cat.itacademy.proyectoerp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cat.itacademy.proyectoerp.domain.OrderDetail;
import cat.itacademy.proyectoerp.repository.IOrderDetailRepository;


@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
	
	@Autowired
	private IOrderDetailRepository orderDetailRepository;

	
	@Override
	public OrderDetail create(OrderDetail orderDetail) {
			return this.orderDetailRepository.save(orderDetail);
	}

}
