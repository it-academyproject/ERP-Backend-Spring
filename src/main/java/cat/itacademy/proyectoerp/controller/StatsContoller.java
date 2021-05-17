package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.DatesTopEmployeePOJO;
import cat.itacademy.proyectoerp.dto.TopEmployeeDTO;
import cat.itacademy.proyectoerp.service.EmployeeServiceImpl;
import cat.itacademy.proyectoerp.service.IOrderService;
import cat.itacademy.proyectoerp.util.StringToOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/stats")
public class StatsContoller {

  @Autowired
  IOrderService orderService;
  
  @Autowired
  EmployeeServiceImpl employeeService;

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
  public Map<String, Object> getOrdersByClient(@PathVariable(value = "id") String id) throws Exception{
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
  
  @GetMapping("/employees/sells")
  public Map<String,Object> getEmployeeSales() throws Exception{
	  
    HashMap<String, Object> map = new HashMap<>();
   
    try {
    	List<Employee> employees = employeeService.findAllEmployees();
    	List<Order> orderList = orderService.findAllOrders();
    	if(employees.isEmpty() || orderList.isEmpty()) {
    		map.put("success", "true");
            map.put("message", "no employees or orders founded");
    	}else {
    		map.put("success", "true");
    	    map.put("message", "employees list found");
    	    orderList.removeIf(o -> !o.getStatus().toString().equalsIgnoreCase("COMPLETED"));
    	    List<Map<String,String>> list = new ArrayList();
    	    for(Employee e : employees) {
    	    	 List<Order> employeeOrders = orderList.stream()                
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
  @RequestMapping(value = "/employees/toptensales", method = RequestMethod.GET)
  public Map <String,Object> getTopTenEmployeesSales(@RequestBody DatesTopEmployeePOJO datestopemployee) {
    
	  HashMap<String, Object> map = new HashMap<>();
	  
	  if (datestopemployee.getBegin_date() == null) datestopemployee.setBegin_date(LocalDateTime.of(2020,01,01,00,01)); 
		
	  if (datestopemployee.getEnd_date() == null ) datestopemployee.setEnd_date(LocalDateTime.now());
	  
	  if (datestopemployee.getBegin_date().isBefore(datestopemployee.getEnd_date())) {
    
		  try {  
			List<TopEmployeeDTO> employeeList = orderService.findAllTopTen(datestopemployee);
							  
				  if(employeeList.isEmpty()) {
			    		map.put("success", "true");
			            map.put("message", "no employees or orders found between the dates");
				  } 
				  else {
					  map.put("succes","true");
					  map.put("message","top 10 employees found");
					  map.put("employees", employeeList);
				  }
					  
		  } catch(Exception e) {
		    	 map.put("success", "false");
		         map.put("message", "error: " + e.getMessage());
		  }
			  
	  }else {
		  map.put("success", "false");
	      map.put("message", "_error: invalid date ranges"); 
	  }
	  
	  
    return map;
	  
  }

  
}
