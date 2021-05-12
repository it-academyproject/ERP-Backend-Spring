package cat.itacademy.proyectoerp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "direct_discount")
public class Direct_discount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "id")
	private int id;
	
	@Column(name = "discount", nullable = false, unique = true, scale = 2, updatable = false)
	private double discount;
	
	
	public int getId() {
		return id;
	}

	public double getDiscount() {
		return discount;
	}
	
	
}
