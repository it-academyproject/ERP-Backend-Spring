package cat.itacademy.proyectoerp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "free_products", uniqueConstraints= {@UniqueConstraint(columnNames= {"products_to_buy","products_to_pay"})})
public class FreeProducts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id")
	private int id;
	
	@Column(name = "products_to_buy", nullable = false, updatable = false)
	private int products_to_buy;
	
	@Column(name = "products_to_pay", nullable = false, updatable = false)
	private int products_to_pay;
	
	
	public FreeProducts() {
		
	}
	
	public FreeProducts(int products_to_buy, int products_to_pay) {
		this.products_to_buy = products_to_buy;
		this.products_to_pay = products_to_pay;
			
	}

	
	
	//GETTER & SETTERS
	public int getProducts_to_buy() {
		return products_to_buy;
	}

	public void setProducts_to_buy(int products_to_buy) {
		this.products_to_buy = products_to_buy;
	}

	public int getProducts_to_pay() {
		return products_to_pay;
	}

	public void setProducts_to_pay(int products_to_pay) {
		this.products_to_pay = products_to_pay;
	}

	public int getId() {
		return id;
	}

	
	
	
}
