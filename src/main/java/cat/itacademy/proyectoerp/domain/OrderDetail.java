package cat.itacademy.proyectoerp.domain;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
@Entity
public class OrderDetail {


	@EmbeddedId
    @JsonIgnore
    private OrderDetailPK pk;

    @Column(nullable = false)
	private Integer quantity;

    // default constructor
    public OrderDetail() {
    	super();
    }

    public OrderDetail(Order order, Product product, Integer quantity) {
        pk = new OrderDetailPK();
        pk.setOrder(order);
        pk.setProduct(product);
        this.quantity = quantity;
    }

    @Transient
    public Product getProduct() {
        return this.pk.getProduct();
    }

    @Transient
    public Double getTotalPrice() {
        return getProduct().getPrice() * getQuantity();
    }

	public OrderDetailPK getPk() {
		return pk;
	}

	public void setPk(OrderDetailPK pk) {
		this.pk = pk;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
    
    
}