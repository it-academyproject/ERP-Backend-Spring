package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
}
