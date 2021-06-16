package cat.itacademy.proyectoerp.service;


import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.dto.EmployeeDTO;
import cat.itacademy.proyectoerp.dto.MessageDTO;
import cat.itacademy.proyectoerp.dto.UserDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.repository.IEmployeeRepository;
import cat.itacademy.proyectoerp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

  @Autowired
  IEmployeeRepository iEmployeeRepository;

  @Autowired
  UserRepository userRepository;

  ModelMapper modelMapper = new ModelMapper();

  @Override
  public EmployeeDTO createEmployee(Employee employee){
    EmployeeDTO employeeDTO = new EmployeeDTO();
    UserDTO userDTO;
    MessageDTO messageDTO;
    Employee savedEmployee;

    User user = userRepository.findByUsername(employee.getUser().getUsername());
    try {
      savedEmployee = iEmployeeRepository.save(employee);
    }catch (Exception e){
      messageDTO = new MessageDTO("False", "Employee exist. Please, check the username");
      employeeDTO.setMessage(messageDTO);
      userDTO = modelMapper.map(user, UserDTO.class);
      employeeDTO.setUser(userDTO);
      return employeeDTO;
    }

    messageDTO = new MessageDTO("True","Employee created");
    employeeDTO = modelMapper.map(savedEmployee, EmployeeDTO.class);
    employeeDTO.setMessage(messageDTO);

    return employeeDTO;
  }

  @Override
  public EmployeeDTO findEmployeeById(UUID id) throws ArgumentNotFoundException  {
	Employee employee = iEmployeeRepository.findById(id).orElseThrow(() -> new ArgumentNotFoundException("Employee not found. The id " + id + " doesn't exist"));
	return modelMapper.map(employee, EmployeeDTO.class);
  }

  @Override
  public List<EmployeeDTO> findAllEmployees() throws ArgumentNotFoundException {
    if(iEmployeeRepository.findAll().isEmpty()){
      throw new ArgumentNotFoundException("No employees found");
    }
    List<EmployeeDTO> employeesDTO = iEmployeeRepository.findAll().stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
    return employeesDTO;
  }

  @Override
  public EmployeeDTO updateEmployee(Employee employee) throws Exception {
    Employee employeeById = iEmployeeRepository.findById(employee.getId()).orElseThrow(
            () -> new ArgumentNotFoundException("Employee not found. The id " + employee.getId() + " doesn't exist"));

    validateEmployeeToUpdate(employee, employeeById);

    validateUserEmployeeToUpdate(employee, employeeById);

    Employee employeeUpdated;
    try {
      employeeUpdated = iEmployeeRepository.save(employee);
    }catch (Exception e){
      throw new Exception("The username already exists. Please, choose another.");
    }
    return modelMapper.map(employeeUpdated, EmployeeDTO.class);
  }

  private void validateEmployeeToUpdate(Employee employee, Employee employeeById) {
    employee.setSalary(null == employee.getSalary()? employeeById.getSalary(): employee.getSalary());
    employee.setDni(null == employee.getDni()? employeeById.getDni(): employee.getDni());
    employee.setPhone(null == employee.getPhone()? employeeById.getPhone(): employee.getPhone());
    employee.setInDate(null == employee.getInDate()? employeeById.getInDate(): employee.getInDate());

    if(employeeById.getOutDate() != null && employee.getOutDate() == null){
      employee.setOutDate(employeeById.getOutDate());
    }
    else{
      employee.setOutDate(employee.getOutDate());
    }
  }

  private void validateUserEmployeeToUpdate(Employee employee, Employee employeeById) {
    if(employee.getUser() != null){
      employee.getUser().setId(employeeById.getUser().getId());
      employee.getUser().setUsername(null == employee.getUser().getUsername()? employeeById.getUser().getUsername():
              employee.getUser().getUsername());
      employee.getUser().setPassword(null == employee.getUser().getPassword()? employeeById.getUser().getPassword():
              employee.getUser().getPassword());
      employee.getUser().setUserType(employeeById.getUser().getUserType());
    }else{
      User user = new User(employeeById.getUser().getUsername(), employeeById.getUser().getPassword(), UserType.EMPLOYEE);
      user.setId(employeeById.getUser().getId());
      employee.setUser(user);
    }
  }

  @Override
  public void deleteEmployee(UUID id) throws ArgumentNotFoundException {
    iEmployeeRepository.findById(id).orElseThrow(
            () -> new ArgumentNotFoundException("Employee not found. The id " + id + " doesn't exist"));

    iEmployeeRepository.deleteById(id);
  }

	@Override
	public double getSalariesByYear() {
		return iEmployeeRepository.getTotalSalariesForYear();
	}

	@Override
	public double getSalariesByMonth() {
		//Assuming employees have 12 payments per year.
		return iEmployeeRepository.getTotalSalariesForYear()/12;
	}
}
