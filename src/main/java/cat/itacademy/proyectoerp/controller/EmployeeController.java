package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  @Autowired
  IEmployeeService iEmployeeService;

  @PostMapping()
  public Map<String, Object> createEmployee(@RequestBody Employee employee){
    HashMap<String, Object> map = new HashMap<>();

    try {
      iEmployeeService.createEmployee(employee);
      map.put("success", "true");
      map.put("message", "Employee created");
      map.put("employee", employee);
    } catch (Exception e){
      map.put("success", "false");
      map.put("message", "error: " + e.getMessage());
    }
    return map;
  }

  @GetMapping("/{id}")
  public Optional<Employee> getEmployeeById(@PathVariable UUID id) {
      return iEmployeeService.findEmployeeById(id);
  }

  @GetMapping()
  public ResponseEntity<?> getAllEmployees() {
      List<Employee> list = new ArrayList<>();
		try {
			list = iEmployeeService.findAllEmployees();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
		}
      return ResponseEntity.ok().body(list);
  }


  @DeleteMapping("/{id}")
  public Map<String, Object> deleteEmployee(@PathVariable(name="id") UUID id) {
    HashMap<String, Object> map = new HashMap<>();
    try {
      iEmployeeService.deleteEmployee(id);
      map.put("success", "true");
      map.put("message", "Employee with id: " + id + " has been deleted");
    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", "error: " + e.getMessage());
    }
    return map;
  }

  @PutMapping()
  public ResponseEntity<?> updateEmployeeById(@RequestBody Employee employeeToUpdate) {
	  try {
	        iEmployeeService.updateEmployee(employeeToUpdate);
	        return ResponseEntity.status(HttpStatus.CREATED).body(employeeToUpdate);
	    }  catch(Exception e) {
	    	return ResponseEntity.badRequest().body(e.getMessage());
	    }  
  }	  
}	  
	  
	  
  	

