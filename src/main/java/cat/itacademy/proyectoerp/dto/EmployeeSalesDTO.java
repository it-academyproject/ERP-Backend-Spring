package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EmployeeSalesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EmployeeDTO employee;
	private Double totalSales;
	
	

	

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}
	
	public Double getTotalSales() {
		return totalSales;
	}
	
	public void setTotalSales(Double totalSales) {
		this.totalSales = totalSales;
	}

	
}
