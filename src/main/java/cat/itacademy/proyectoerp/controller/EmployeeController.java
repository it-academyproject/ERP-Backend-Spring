package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  @Autowired
  IEmployeeService iEmployeeService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping()
  public Map<String, Object> getEmployees(){
    HashMap<String, Object> map = new HashMap<String, Object>();
    try {
      List<Employee> employeeList = iEmployeeService.findAllEmployees();
      map.put("success", "true");
      map.put("message", "employee found");
      map.put("employee", employeeList);
    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", e.getMessage());
    }
    return map;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public Map<String, Object> getEmployeeById(@PathVariable(name="id") UUID id){
    HashMap<String, Object> map = new HashMap<>();
    try {
      Employee employee = iEmployeeService.findEmployeeById(id);
      map.put("success", "true");
      map.put("message", "employee found");
      map.put("employee", employee);
    } catch (Exception e){
      map.put("success", "false");
      map.put("message", e.getMessage());
    }
    return map;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/")
  public Map<String, Object> deleteEmployee(@RequestBody ObjectNode objectNode) {
	  UUID id =UUID.fromString( objectNode.get("id").asText());
    HashMap<String, Object> map = new HashMap<>();
    try {
      iEmployeeService.deleteEmployee(id);
      map.put("success", "true");
      map.put("message", "Employee with id: " + id + " has been deleted");
    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", e.getMessage());
    }
    return map;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping()
  public HashMap<String, Object> updateEmployee(@RequestBody Employee employee){
    HashMap<String, Object> map = new HashMap<String, Object>();
    try {
      Employee employeeUpdated = iEmployeeService.updateEmployee(employee);
      map.put("success", "true");
      map.put("message", "Employee with id: " + employee.getId() + " has been updated");
      map.put("employee", employeeUpdated);

    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", e.getMessage());
    }
    return map;
  }
}

