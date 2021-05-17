package cat.itacademy.proyectoerp.dto;



public class TopEmployeeDTO {

	
	private String id;
	private Double total;
	
	
	public TopEmployeeDTO(String id, Double total) {
		this.id = id;
		this.total = total;
	}
	
	public TopEmployeeDTO() {
		super();
	}
	
	
	//GETTERS & SETTERS
	
	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	

	

}