package cat.itacademy.proyectoerp.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

import cat.itacademy.proyectoerp.repository.IProductRepository;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderDetail;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.repository.IOrderDetailRepository;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService{

	@Autowired
	IOrderDetailRepository iOrderDetailRepository;

	@Autowired
	IProductRepository iProductRepository;

	@Override
	public OrderDetail createOrderDetail(Order order, Product product, Integer quantity) throws Exception {
		// Get product in db just in case if the given product is malformed (for security)
		product = iProductRepository.findByName(product.getName());
		if (quantity > product.getStock())
			throw new Exception("Quantity is larger than stock!");
		Double subtotal = calculateSubtotalFromProductAndQuantity(product, quantity);
		product.setStock(product.getStock() - quantity);
		iProductRepository.save(product);
		return new OrderDetail(product, order, quantity, subtotal);
	}

	private Double calculateSubtotalFromProductAndQuantity(Product product, Integer quantity) {
		double price;
		if(quantity >= product.getWholesale_quantity())
			price = product.getWholesale_price();
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
	public void deleteOrderDetail(UUID id) {
		iOrderDetailRepository.deleteById(id);
		
	}
	
}