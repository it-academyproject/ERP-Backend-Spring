package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.dto.TopEmployeeDTO;
import cat.itacademy.proyectoerp.dto.EmployeeDTO;
import cat.itacademy.proyectoerp.dto.EmployeeSalesDTO;
import cat.itacademy.proyectoerp.dto.OrderDTO;
import cat.itacademy.proyectoerp.dto.ProductStatsDTO;
import cat.itacademy.proyectoerp.service.IEmployeeService;
import cat.itacademy.proyectoerp.service.IOrderService;
import cat.itacademy.proyectoerp.service.IProductService;
import cat.itacademy.proyectoerp.util.StringToOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/stats")
public class StatsController {

  @Autowired
  IOrderService orderService;
  
  @Autowired
  IEmployeeService employeeService;
  
  @Autowired
  IProductService productService;

  @GetMapping("/status/{status}")
  public Map<String, Object> getOrderBySatus(@PathVariable(value = "status") String status) throws Exception{
    HashMap<String, Object> map = new HashMap<>();

    try {
      List<Order> orderList = orderService.findOrdersByStatus(StringToOrderStatus.stringToOrderStatus(status));
      if(!orderList.isEmpty()){
        map.put("success", "true");
        map.put("message", "order list found");
        map.put("order", orderList);
      } else{
        map.put("success", "true");
        map.put("message", "order list empty");
        map.put("order", orderList);
      }
    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", "error: " + e.getMessage());
    }
    return map;
  }

  @GetMapping("/ordersByClient/{id}")
  public Map<String, Object> getOrdersByClient(@PathVariable(value = "id") UUID id) throws Exception{
    HashMap<String, Object> map = new HashMap<>();

    try {
      List<Order> orderList = orderService.findOrdersByClient(id);
      if(!orderList.isEmpty()){
        map.put("success", "true");
        map.put("message", "order by client list found");
        map.put("order", orderList);
      } else{
        map.put("success", "true");
        map.put("message", "order list by client is empty");
        map.put("order", orderList);
      }
    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", "error: " + e.getMessage());
    }
    return map;
  }
  
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/employees/sells")
  public Map<String,Object> getEmployeeSales() throws Exception{
	  
    HashMap<String, Object> map = new HashMap<>();
   
    try {
    	List<EmployeeDTO> employees = employeeService.findAllEmployees();
    	List<OrderDTO> orderList = orderService.findAllOrders();
    	if(employees.isEmpty() || orderList.isEmpty()) {
    		map.put("success", "true");
            map.put("message", "no employees or orders founded");
    	}else {
    		map.put("success", "true");
    	    map.put("message", "employees list found");
    	    orderList.removeIf(o -> !o.getStatus().toString().equalsIgnoreCase("COMPLETED"));
    	    List<Map<String,String>> list = new ArrayList<>();
    	    for(EmployeeDTO e : employees) {
    	    	 List<OrderDTO> employeeOrders = orderList.stream()                
    	                 .filter(order -> order.getEmployeeId().toString()
    	                		 .equalsIgnoreCase(e.getId().toString()))    
    	                 .collect(Collectors.toList()); 
    	    	 
    	    	 Map<String, String> employee = new HashMap<>();
    	    	 employee.put("name", e.getUser().getUsername());
    	    	 employee.put("dni" , e.getDni());
    	    	 employee.put("orders", String.valueOf(employeeOrders.size()));
    	    	 list.add(employee);
    	    	 
    	    }
    	    map.put("employee", list);
    	      
    	}
    } catch(Exception e) {
    	 map.put("success", "false");
         map.put("message", "error: " + e.getMessage());
    }
    
    return map;
  }
  
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/employeestop/{year}/{month}")
	public Map<String, Object> getEmployeesTop(@PathVariable("year") int year, @PathVariable("month") int month) {
		HashMap<String, Object> map = new LinkedHashMap<>();
		try {
			List<TopEmployeeDTO> employeeList = orderService.getTopTen(year,month);
			String monthName = new DateFormatSymbols().getMonths()[month-1];
			if (employeeList.isEmpty()) {
				map.put("success", "false");
				map.put("message", "orders for " + monthName + " " + year + " not found");
			} else {
				map.put("success", "true");
				map.put("message", "topten for " + monthName + " " + year + " found");
				map.put("month", month);
				map.put("year", year);
				map.put("top ten employees", employeeList);
			}
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		return map;
	}
  
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/employees/bestsales")
  public Map<String, Object> getBestEmployeeByTotalSales() {
	  HashMap<String, Object> map = new HashMap<>();
	  try {
		  EmployeeSalesDTO bestEmployee = orderService.getBestEmployeeByTotalSales();
		  map.put("success", "true");
		  map.put("message", "best employee found");
		  map.put("employee", bestEmployee);
	  }catch(Exception e) {
		  map.put("success", "false");
		  map.put("message", "error: " + e.getMessage());
	  }
	  return map;
  }
  
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/employees/worstsales")
  public Map<String, Object> getWorstEmployeeBySalesRate() {
	  HashMap<String, Object> map = new HashMap<>();
	  try {
		  EmployeeSalesDTO worstEmployee = orderService.getWorstEmployeeByTotalSales();
		  map.put("success", "true");
		  map.put("message", "worst employee found");
		  map.put("employee", worstEmployee);
	  }catch(Exception e) {
		  map.put("success", "false");
		  map.put("message", "error: " + e.getMessage());
	  }
	  return map;
  }
  
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/profits/{year}")
	public Map<String, Object> getTotalProfitByYear(@PathVariable("year") int year) {
		HashMap<String, Object> map = new LinkedHashMap<>();
		try {
			double profit = orderService.getProfitByYear(year);
			map.put("success", "true");
			map.put("message", "profits for year " + year + " found");
			map.put("year", year);
			map.put("profit", profit);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/profits/{year}/{month}")
	public Map<String, Object> getTotalProfitByMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
		HashMap<String, Object> map = new LinkedHashMap<>();
		try {
			double profit = orderService.getProfitByMonth(year,month);
			String monthName = new DateFormatSymbols().getMonths()[month-1];
			map.put("success", "true");
			map.put("message", "profits for " + monthName + " " + year + " found");
			map.put("month", month);
			map.put("year", year);
			map.put("profit", profit);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/salaries/year")
	public Map<String, Object> getTotalSalariesByYear() {
		HashMap<String, Object> map = new LinkedHashMap<>();
		try {
			double salaries = employeeService.getSalariesByYear();
			map.put("success", "true");
			map.put("message", "Total salaries for a year found");
			map.put("salaries", salaries);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		return map;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/salaries/month")
	public Map<String, Object> getTotalSalariesByMonth() {
		HashMap<String, Object> map = new LinkedHashMap<>();
		try {
			double salaries = employeeService.getSalariesByMonth();
			map.put("success", "true");
			map.put("message", "Total salaries for a month found");
			map.put("salaries", salaries);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		return map;
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/count/{field}")
	public Map<String, Object> getCountOrderByField(@PathVariable("field") String field) throws Exception {		

		HashMap<String, Object> map = new HashMap<>();
		
		if (!field.equals("status" ) && !field.equals("payment" ) ) {
			   map.put("success", "false");
		      map.put("message", "error, the parameter: '"+ field + "' its wrong " );
		      return map;
		}			  
		try {
			HashMap<String, Long> countOrder = orderService.countOrdersByfield(field);
			
		      if(!countOrder.isEmpty()){
		        map.put("success", "true");
		  		map.put("message", "order list found");
		  		map.put("count_by", field);
		  		map.put("orders",countOrder);
		      } else{
		        map.put("success", "true");
		        map.put("message", "order list empty");		        
		      }
		    } catch (Exception e) {
		      map.put("success", "false");
		      map.put("message", "error: " + e.getMessage());
		    }
		return map;
	}
	
	@GetMapping("/products/maxprice")
	public Map<String, Object> getMaxPriceProduct(){
		
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			ProductStatsDTO dto = productService.getMaxPrice();
			map.put("success", "true");
			map.put("message", "max price product found");
			map.put("product", dto);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
	@GetMapping("/products/minprice")
	public Map<String, Object> getMinPriceProduct(){
		
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			ProductStatsDTO dto = productService.getMinPrice();
			map.put("success", "true");
			map.put("message", "min price product found");
			map.put("product", dto);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", "error: " + e.getMessage());
		}
		
		return map;
	}
	
}
