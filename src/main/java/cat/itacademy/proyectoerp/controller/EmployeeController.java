package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
      map.put("message", "Error: " + e.getMessage());
    }
    return map;
  }

  @GetMapping("/{id}")
  public Map<String, Object> getEmployeeById(@PathVariable(name="id") UUID id){
    HashMap<String, Object> map = new HashMap<>();
    try {
      Employee employee = iEmployeeService.findOrderById(id);
      map.put("success", "true");
      map.put("message", "employee found");
      map.put("employee", employee);
    } catch (Exception e){
      map.put("success", "false");
      map.put("message", "error: " + e.getMessage());
    }
    return map;
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
}
