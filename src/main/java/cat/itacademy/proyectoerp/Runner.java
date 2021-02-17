package cat.itacademy.proyectoerp;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.service.ClientServiceImpl;
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

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		logger.info("initializing 2 users type client, 2 clients, 2 products and 2 orders");

		// Initialize two products
		Product productOne = new Product(1, "ejemplo 1", 100, "url image", "Bebidas", 3.00, 21.00, 2.50, 500);
		productService.createProduct(productOne);

		Product productTwo = new Product(2, "ejemplo 2", 100, "url image", "Bebidas", 3.00, 21.00, 2.50, 500);
		productService.createProduct(productTwo);

		// Initialize two Users type Client
		User userClientOne = new User("userclientone@example.com", "ReW9a0&+TP");
		userService.registerNewUserAccount(userClientOne);

		User userClientTwo = new User("userclienttwo@example.com", "ReW9a0&+ET");
		userService.registerNewUserAccount(userClientTwo);

		// Initialize two Clients
		Client clientOne = new Client("address example", "L1234567Z", "url image", userClientOne);
		clientService.createClient(clientOne);

		Client clientTwo = new Client("address example", "L7654321Z", "url image", userClientTwo);
		clientService.createClient(clientTwo);

	}

}
