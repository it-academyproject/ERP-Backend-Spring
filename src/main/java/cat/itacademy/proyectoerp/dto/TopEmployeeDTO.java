package cat.itacademy.proyectoerp.dto;



public class TopEmployeeDTO {

	
	private String employee_id;
	private Double total;
	
	
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public TopEmployeeDTO(String employee_id, Double total) {
		this.employee_id = employee_id;
		this.total = total;
	}
	public TopEmployeeDTO() {
		super();
	}
	

}