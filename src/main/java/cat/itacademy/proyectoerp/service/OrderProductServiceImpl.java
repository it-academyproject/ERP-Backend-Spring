package cat.itacademy.proyectoerp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.proyectoerp.domain.OrderProduct;
import cat.itacademy.proyectoerp.repository.IOrderProductRepository;


@Service
@Transactional
public class OrderProductServiceImpl implements IOrderProductService {
	
	@Autowired
	private IOrderProductRepository orderProductRepository;

	@Override
	public void addOrderedProducts(OrderProduct orderProduct) {
		orderProductRepository.save(orderProduct);
	}
	
	

}
