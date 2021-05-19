package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.dto.EmployeeDTO;

import java.util.List;
import java.util.UUID;

public interface IEmployeeService {

  public EmployeeDTO createEmployee(Employee employee);

  public EmployeeDTO findEmployeeById(UUID id);

  public List<Employee> findAllEmployees();

  public Employee updateEmployee(Employee employee) throws Exception;

  public void deleteEmployee(UUID id);

}
