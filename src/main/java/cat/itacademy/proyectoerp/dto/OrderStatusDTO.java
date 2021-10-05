package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;

import cat.itacademy.proyectoerp.domain.OrderStatus;

public class OrderStatusDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private OrderStatus status;
	
	public OrderStatusDTO() {}
	
	public OrderStatusDTO(OrderStatus status) {
		this.status = status;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderStatusDTO [status=" + status + "]";
	}
	
}
