package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.dto.EmployeeDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

import java.util.List;
import java.util.UUID;

public interface IEmployeeService {

  public EmployeeDTO createEmployee(Employee employee);

  public Employee findEmployeeById(UUID id);

  public List<Employee> findAllEmployees();

  public Employee updateEmployee(Employee employee) throws Exception;

  public void deleteEmployee(UUID id);

}
