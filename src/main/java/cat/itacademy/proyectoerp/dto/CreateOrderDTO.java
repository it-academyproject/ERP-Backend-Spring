package cat.itacademy.proyectoerp.dto;

import java.util.Map;
import java.util.UUID;

import cat.itacademy.proyectoerp.domain.Address;
import cat.itacademy.proyectoerp.domain.PaymentMethod;

public class CreateOrderDTO {

	private UUID clientId;
	private PaymentMethod paymentMethod;
	private Address shippingAddress;
	private Address billingAddress;
	private Map<Integer, Integer> productsQuantity;
	
	public UUID getClientId() {
		return clientId;
	}
	public void setClientId(UUID clientId) {
		this.clientId = clientId;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Address getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public Address getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}
	public Map<Integer, Integer> getProductsQuantity() {
		return productsQuantity;
	}
	public void setProductsQuantity(Map<Integer, Integer> productsQuantity) {
		this.productsQuantity = productsQuantity;
	}
	
}
