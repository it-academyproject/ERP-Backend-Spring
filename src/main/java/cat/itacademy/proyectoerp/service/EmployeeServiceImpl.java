package cat.itacademy.proyectoerp.service;


import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IEmployeeRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
  public Optional<Employee> findEmployeeById(UUID id) {
      Optional<Employee> employeeDB = iEmployeeRepository.findById(id);
      if (employeeDB.isPresent())
          return employeeDB;
      else
            throw new ArgumentNotFoundException("Employee not found. The id " + id + " doesn't exist");
  }

  @Override
  public List<Employee> findAllEmployees() throws ArgumentNotFoundException {
    if(iEmployeeRepository.findAll().isEmpty()){
      throw new ArgumentNotFoundException("No employees found");
    }
    return iEmployeeRepository.findAll();
  }


  @Override
	public Employee updateEmployee(Employee employee) {
		return iEmployeeRepository.findById(employee.getId()).map(employeeDB -> {
			BeanUtils.copyProperties(employee, employeeDB);
			return iEmployeeRepository.save(employeeDB);
		}).orElseThrow(() -> new ArgumentNotFoundException("Employee not found. The id " + employee.getId() + " doesn't exist"));
	}

  @Override
  public void deleteEmployee(UUID id) {
    iEmployeeRepository.deleteById(id);
  }
}
