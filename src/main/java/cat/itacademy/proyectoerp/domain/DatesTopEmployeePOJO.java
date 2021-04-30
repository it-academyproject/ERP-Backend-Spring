package cat.itacademy.proyectoerp.domain;

import java.time.LocalDateTime;

public class DatesTopEmployeePOJO {
	
	private LocalDateTime begin_date;
	
	private LocalDateTime end_date;
	
	private OrderStatus status;

	public LocalDateTime getBegin_date() {
		return begin_date;
	}

	public LocalDateTime getEnd_date() {
		return end_date;
	}

	public void setBegin_date(LocalDateTime begin_date) {
		this.begin_date = begin_date;
	}

	public void setEnd_date(LocalDateTime end_date) {
		this.end_date = end_date;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	
	

}
