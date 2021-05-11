package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import cat.itacademy.proyectoerp.domain.Address;
import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.domain.PaymentMethod;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDTO implements Serializable {

	private UUID id;
	private UUID employee_id;
	private UUID client_id;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDateTime date_created;
	private OrderStatus status;
	private PaymentMethod payment_method;
	private Address shipping_address;
	private Address billing_address;
	private Double total;
	
	private Set<OrderDetailDTO> order_details;
	
	private MessageDTO message;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(UUID employee_id) {
		this.employee_id = employee_id;
	}

	public UUID getClient_id() {
		return client_id;
	}

	public void setClient_id(UUID client_id) {
		this.client_id = client_id;
	}

	public LocalDateTime getDate_created() {
		return date_created;
	}

	public void setDate_created(LocalDateTime date_created) {
		this.date_created = date_created;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public PaymentMethod getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(PaymentMethod payment_method) {
		this.payment_method = payment_method;
	}

	public Address getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(Address shipping_address) {
		this.shipping_address = shipping_address;
	}

	public Address getBilling_address() {
		return billing_address;
	}

	public void setBilling_address(Address billing_address) {
		this.billing_address = billing_address;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Set<OrderDetailDTO> getOrder_details() {
		return order_details;
	}

	public void setOrder_details(Set<OrderDetailDTO> order_details) {
		this.order_details = order_details;
	}

	public MessageDTO getMessage() {
		return message;
	}

	public void setMessage(MessageDTO message) {
		this.message = message;
	}
	
}