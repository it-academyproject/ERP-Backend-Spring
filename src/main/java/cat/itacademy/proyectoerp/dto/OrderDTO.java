package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.domain.PaymentMethod;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private UUID employeeId;
	private UUID clientId;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDateTime dateCreated;
	private OrderStatus status;
	private PaymentMethod paymentMethod;
	private AddressDTO shippingAddress;
	private AddressDTO billingAddress;
	private Double total;
	
	private Set<OrderDetailDTO> orderDetails;
	
	private MessageDTO message;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(UUID employeeId) {
		this.employeeId = employeeId;
	}

	public UUID getClientId() {
		return clientId;
	}

	public void setClientId(UUID clientId) {
		this.clientId = clientId;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public AddressDTO getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(AddressDTO shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public AddressDTO getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressDTO billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Set<OrderDetailDTO> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetailDTO> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public MessageDTO getMessage() {
		return message;
	}

	public void setMessage(MessageDTO message) {
		this.message = message;
	}
	
}