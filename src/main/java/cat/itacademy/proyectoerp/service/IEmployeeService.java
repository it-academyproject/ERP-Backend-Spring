package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.Employee;

import java.util.List;
import java.util.UUID;

public interface IEmployeeService {

  public Employee createEmployee(Employee employee);

  public Employee findEmployeeById(UUID id);

  public List<Employee> findAllEmployees();

  public Employee updateEmployee(Employee employee);

  public void deleteEmployee(UUID id);

}
