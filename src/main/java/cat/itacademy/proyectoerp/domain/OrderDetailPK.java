package cat.itacademy.proyectoerp.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Embeddable
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="order")
public class OrderDetailPK implements Serializable{

	private static final long serialVersionUID = 1L;

		
	    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	    @JoinColumn(name = "order_id")
	    private Order order;

	    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	    @JoinColumn(name = "product_id")
	    private Product product;

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}
	    
	    

}
