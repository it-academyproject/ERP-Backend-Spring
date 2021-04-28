package cat.itacademy.proyectoerp;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.proyectoerp.domain.Address;
import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.domain.PaymentMethod;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.repository.UserRepository;
import cat.itacademy.proyectoerp.service.AddressServiceImpl;
import cat.itacademy.proyectoerp.service.ClientServiceImpl;
import cat.itacademy.proyectoerp.service.EmployeeServiceImpl;
import cat.itacademy.proyectoerp.service.OrderServiceImpl;
import cat.itacademy.proyectoerp.service.ProductServiceImpl;
import cat.itacademy.proyectoerp.service.UserServiceImpl;

/**
 * This class establishes that when the application is started, the run method
 * creates the following users, products, clients and orders in the database.
 * 
 */

@Component
public class Runner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	ProductServiceImpl productService;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	ClientServiceImpl clientService;

	@Autowired
	OrderServiceImpl orderService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmployeeServiceImpl employeeService;
	
	@Autowired
	AddressServiceImpl addressService;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		//checks if there is already an admin created, if not, it creates one.
		if(!userRepository.existsByUsername("admin@erp.com")){
			userRepository.save(new User("admin@erp.com", passwordEncoder.encode("ReW9a0&+TP"), UserType.ADMIN));
		}
		
		//checks if there is already an employee created, if not, it creates one
		if(!userRepository.existsByUsername("employee@erp.com")) {
			User userEmployee = new User("employee@erp.com", "ReW9a0&+TP", UserType.EMPLOYEE);
			userService.registerNewUserAccount(userEmployee);
			Employee employee = new Employee(18000.00, "employee@erp.com", "C1234567Z", 667999997, userEmployee);
			employeeService.createEmployee(employee);
		}
		
		// Initialize 5 addresses
		
		Address address1 = new Address ("Calle Maldivas", "1 C", "Barcelona", "Spain", "08016",null);
		addressService.createAddress(address1);
		Address address2 = new Address ("Calle Azores", "1 C", "Barcelona", "Spain", "08016",null);
		addressService.createAddress(address2);
		Address address3 = new Address ("Calle Canarias", "1 C", "Barcelona", "Spain", "08016",null);
		addressService.createAddress(address3);
		Address address4 = new Address ("Calle Bahamas", "1 C", "Barcelona", "Spain", "08016",null);
		addressService.createAddress(address4);
		
		Address addressExample = new Address ("Calle Ejemplo", "1 A", "Barcelona", "Spain", "08018",null);
		addressService.createAddress(addressExample);
		
		//checks if there is already a client created, if not, it creates one
		if(!userRepository.existsByUsername("client@erp.com")) {
			User userClient = new User("client@erp.com", "ReW9a0&+TP", UserType.CLIENT);
			userService.registerNewUserAccount(userClient);
			Client client = new Client(addressExample, "L1234567Z", "url image","Random Name", userClient);
			clientService.createClient(client);
		}
		
		//X is the variable of the amount of clients do you want to have. 
        //that excludes two separate clients that are made to create two test orders.
		int x = 18;
		
		
		//Checks if there is less data than the asked about and adds the amount left.
		if(userService.listAllUsers().size() < x+1) {
			
			//Subtracts the amount of users already created
			x = x - userService.listAllUsers().size()+1;
			
			logger.info("initializing " + x+2 +" users type client, " + x+2 + " clients, 2 products and 2 orders");
			
			//Initialize the amount of users and clients marked by x
			for (int i = 0;i < x; i++) {
				Random rand = new Random();
				String random = String.valueOf(rand.nextInt(10000000)); //beware of exception!
				String dni = "L"+random+"Z";
				String mail = "userclient"+i+"@example.com";
				User userClient = new User(mail, "ReW9a0&+TP", UserType.CLIENT);
				userService.registerNewUserAccount(userClient);
				Client client = new Client(addressExample, dni, "url image","Random Name", userClient);
				clientService.createClient(client);
			}
						
			//Initialize two users for the clients for the orders
			User userClientOne = new User("userclientone@example.com", "ReW9a0&+TP", UserType.CLIENT);
			userService.registerNewUserAccount(userClientOne);

			User userClientTwo = new User("userclienttwo@example.com", "ReW9a0&+ET", UserType.CLIENT);
			userService.registerNewUserAccount(userClientTwo);
						
			// Initialize two Clients for the orders
			Client clientOne = new Client(address1, "L1554567Z", "url image","Random Name", userClientOne);
			clientService.createClient(clientOne);

			Client clientTwo = new Client(address3, "B7654321C", "url image","Random Name", userClientTwo);
			clientService.createClient(clientTwo);
			
			
			//Initialize two users for the employees for the orders
			User userEmployeeOne = new User("useremployeeone@example.com", "ReW9a0&+TP", UserType.EMPLOYEE);
			userService.registerNewUserAccount(userEmployeeOne);

			User userEmployeeTwo = new User("useremployeetwo@example.com", "ReW9a0&+ET", UserType.EMPLOYEE);
			userService.registerNewUserAccount(userEmployeeTwo);
			
			
			// Initialize two Employees for the orders
			Employee employeeOne = new Employee(24000.00,"employee1@company.com", "A1234567Z",667999999, userEmployeeOne);
			employeeService.createEmployee(employeeOne);

			Employee employeeTwo = new Employee(14000.00,"employee2@company.com", "B1236767Z",667999998, userEmployeeTwo);
			employeeService.createEmployee(employeeTwo);
			

			// Initialize 3 products
			Product productOne = new Product(1, "ejemplo 1", 100, "url image", "Bebidas", 3.00, 21.00, 2.50, 500);
			productService.createProduct(productOne);

			Product productTwo = new Product(2, "ejemplo 2", 100, "url image", "Bebidas", 3.00, 21.00, 2.50, 500);
			productService.createProduct(productTwo);
			
			Product productThree = new Product(3, "ejemplo 3", 100, "url image", "Bebidas", 3.00, 21.00, 2.50, 500);
			productService.createProduct(productThree);

			
			// Initialize four orders, we create 4 sets of products first
			Set<Product> productsOrder1 = Stream.of(productOne, productTwo)
					.collect(Collectors.toCollection(HashSet::new));
			Set<Product> productsOrder2 = Stream.of(productTwo, productThree)
					.collect(Collectors.toCollection(HashSet::new));
			Set<Product> productsOrder3 = Stream.of(productOne, productThree)
					.collect(Collectors.toCollection(HashSet::new));
			Set<Product> productsOrder4 = Stream.of(productOne, productTwo, productThree)
					.collect(Collectors.toCollection(HashSet::new));
		
			
			Order orderOne = new Order(UUID.randomUUID(), employeeOne.getId().toString(), 
					clientOne.getid().toString(), LocalDateTime.now(), OrderStatus.COMPLETED, 
					PaymentMethod.CREDIT_CARD, address1, address1, productsOrder1);
			orderService.createOrder(orderOne);
			
			Order orderTwo = new Order(UUID.randomUUID(), employeeOne.getId().toString(), 
					clientOne.getid().toString(), LocalDateTime.now(), OrderStatus.IN_DELIVERY, 
					PaymentMethod.CASH, address2, address1, productsOrder2);
			orderService.createOrder(orderTwo);
			
			Order orderThree = new Order(UUID.randomUUID(), employeeOne.getId().toString(), 
					clientTwo.getid().toString(), LocalDateTime.now(), OrderStatus.PENDING_DELIVERY,
					PaymentMethod.PAYPAL, address3, address3, productsOrder3);
			orderService.createOrder(orderThree);
			
			Order orderFour = new Order(UUID.randomUUID(), employeeOne.getId().toString(), 
					clientTwo.getid().toString(), LocalDateTime.now(), OrderStatus.ASSIGNED,
					PaymentMethod.PAYPAL, address4, address3, productsOrder4);
			orderService.createOrder(orderFour);
 
		}

	}

}
