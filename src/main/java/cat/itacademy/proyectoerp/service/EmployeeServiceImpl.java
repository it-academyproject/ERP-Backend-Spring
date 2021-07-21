package cat.itacademy.proyectoerp.service;


import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.dto.EmployeeDTO;
import cat.itacademy.proyectoerp.dto.MessageDTO;
import cat.itacademy.proyectoerp.dto.UserDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.repository.IEmployeeRepository;
import cat.itacademy.proyectoerp.repository.IUserRepository;
import cat.itacademy.proyectoerp.repository.IOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	IEmployeeRepository employeeRepository;
	
	@Autowired
	IUserRepository IUserRepository;
	
	@Autowired
	IOrderRepository iOrderRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public EmployeeDTO createEmployee(Employee employee){
		EmployeeDTO employeeDTO = new EmployeeDTO();
		UserDTO userDTO;
		MessageDTO messageDTO;
		Employee savedEmployee;
		
		User user = IUserRepository.findByUsername(employee.getUser().getUsername());
		
		try {
			savedEmployee = employeeRepository.save(employee);
		} catch (Exception e) {
			messageDTO = new MessageDTO("False", "Employee exist. Please, check the username");
			employeeDTO.setMessage(messageDTO);
			userDTO = modelMapper.map(user, UserDTO.class);
			employeeDTO.setUser(userDTO);
			return employeeDTO;
		}
		
		messageDTO = new MessageDTO("True", "Employee created");
		employeeDTO = modelMapper.map(savedEmployee, EmployeeDTO.class);
		employeeDTO.setMessage(messageDTO);
		
		return employeeDTO;
	}
	
	@Override
	public EmployeeDTO findEmployeeById(UUID id) throws ArgumentNotFoundException  {
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ArgumentNotFoundException("Employee not found. The id " + id + " doesn't exist"));
		return modelMapper.map(employee, EmployeeDTO.class);
	}
	
	@Override
	public List<EmployeeDTO> findAllEmployees() throws ArgumentNotFoundException {
		if(employeeRepository.findAll().isEmpty())
			throw new ArgumentNotFoundException("No employees found");
		
		List<EmployeeDTO> employeesDTO = employeeRepository.findAll().stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
		return employeesDTO;
	}
	
	@Override
	public EmployeeDTO updateEmployee(Employee employee) throws Exception {
		Employee employeeById = employeeRepository.findById(employee.getId()).orElseThrow(
				() -> new ArgumentNotFoundException("Employee not found. The id " + employee.getId() + " doesn't exist"));
		
		validateEmployeeToUpdate(employee, employeeById);
		validateUserEmployeeToUpdate(employee, employeeById);
		
		Employee employeeUpdated;
		
		try {
			employeeUpdated = employeeRepository.save(employee);
		} catch (Exception e) {
			throw new Exception("The username already exists. Please, choose another.");
		}
		
		return modelMapper.map(employeeUpdated, EmployeeDTO.class);
	}
	
	private void validateEmployeeToUpdate(Employee employee, Employee employeeById) {
		employee.setName(null == employee.getName() ? employeeById.getName() : employee.getName());
		employee.setSurname(null == employee.getSurname() ? employeeById.getSurname() : employee.getSurname());
		employee.setSalary(null == employee.getSalary() ? employeeById.getSalary() : employee.getSalary());
		employee.setDni(null == employee.getDni() ? employeeById.getDni() : employee.getDni());
		employee.setPhone(null == employee.getPhone() ? employeeById.getPhone() : employee.getPhone());
		employee.setInDate(null == employee.getInDate() ? employeeById.getInDate() : employee.getInDate());
		
		if(employeeById.getOutDate() != null && employee.getOutDate() == null)
			employee.setOutDate(employeeById.getOutDate());
		else
			employee.setOutDate(employee.getOutDate());
	}
	
	private void validateUserEmployeeToUpdate(Employee employee, Employee employeeById) {
		if (employee.getUser() != null) {
			employee.getUser().setId(employeeById.getUser().getId());
			employee.getUser().setUsername(null == employee.getUser().getUsername() ? employeeById.getUser().getUsername() : employee.getUser().getUsername());
			employee.getUser().setPassword(null == employee.getUser().getPassword() ? employeeById.getUser().getPassword() : employee.getUser().getPassword());
			employee.getUser().setUserType(employeeById.getUser().getUserType());
		} else {
			User user = new User(employeeById.getUser().getUsername(), employeeById.getUser().getPassword(), UserType.EMPLOYEE);
			user.setId(employeeById.getUser().getId());
			employee.setUser(user);
		}
	}
	
	@Override
	public void deleteEmployee(UUID id) throws ArgumentNotFoundException {
		employeeRepository.findById(id).orElseThrow(
				() -> new ArgumentNotFoundException("Employee not found. The id " + id + " doesn't exist"));
		
		employeeRepository.deleteById(id);
	}
	
	@Override
	public double getSalariesByYear() {
		return employeeRepository.getTotalSalariesForYear();
	}
	
	@Override
	public double getSalariesByMonth() {
		//Assuming employees have 12 payments per year.
		return employeeRepository.getTotalSalariesForYear()/12;
	}

	@Override
	public List<EmployeeDTO> findAllEmployeesAndTotalSalesAndTotalOrdersAttended(List<EmployeeDTO> listEmployees) {	
		for (EmployeeDTO e : listEmployees) {
			List<Order> listOrdersOneEmployee = iOrderRepository.findByEmployeeId(e.getId());
			double totalSalesEmployee = listOrdersOneEmployee.stream().mapToDouble(Order:: getTotal).sum();
			int totoalOrdersAttended = (int) listOrdersOneEmployee.stream().count();
			e.setTotalSales(totalSalesEmployee);
			e.setTotalOrdersAttended(totoalOrdersAttended);
		}
		
		return listEmployees;
	}
	
}
