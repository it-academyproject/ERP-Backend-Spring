package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

  @Autowired
  IEmployeeRepository iEmployeeRepository;

  @Override
  public Employee createEmployee(Employee employee) throws ArgumentNotValidException {

    if (employee.equals(null)) {
      throw new ArgumentNotValidException("The employee is null");
    } else if (employee.getEmail().isEmpty()) {
      throw new ArgumentNotValidException("Employee mail is empty");
    } else if (String.valueOf(employee.getPhone()).isEmpty()) {
      throw new ArgumentNotValidException("Employee phone is empty");
    } else if (String.valueOf(employee.getSalary()).isEmpty()) {
      throw new ArgumentNotValidException("Employee salary is empty");
    } else if (employee.getDni().isEmpty()) {
      throw new ArgumentNotValidException("Employee DNI is empty");
    } else if (employee.getDni().isEmpty()) {
      throw new ArgumentNotValidException("Employee DNI is empty");
    } else {
      return iEmployeeRepository.save(employee);
    }
  }

  @Override
  public Employee findOrderById(UUID id) {
    return null;
  }

  @Override
  public List<Employee> findAllEmployees() {
    return null;
  }

  @Override
  public Employee updateEmployee(Employee employee) {
    return null;
  }

  @Override
  public void deleteEmployee(UUID id) {

  }
}
