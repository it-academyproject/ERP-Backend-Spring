package cat.itacademy.proyectoerp.service;

import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.security.auth.message.AuthException;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.DatesTopEmployeePOJO;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderDetail;
import cat.itacademy.proyectoerp.domain.OrderStatus;
import cat.itacademy.proyectoerp.domain.PaymentMethod;
import cat.itacademy.proyectoerp.dto.CreateOrderDTO;
import cat.itacademy.proyectoerp.dto.EmployeeSalesDTO;
import cat.itacademy.proyectoerp.dto.OrderDTO;
import cat.itacademy.proyectoerp.dto.TopEmployeeDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IAddressRepository;
import cat.itacademy.proyectoerp.repository.IClientRepository;
import cat.itacademy.proyectoerp.repository.IOrderDetailRepository;
import cat.itacademy.proyectoerp.repository.IOrderRepository;
import cat.itacademy.proyectoerp.repository.IProductRepository;
import cat.itacademy.proyectoerp.security.jwt.JwtUtil;

@Service
public class OrderServiceImpl implements IOrderService{
	
	@Autowired
	IOrderRepository orderRepository;
	
	@Autowired
	IOrderDetailRepository orderDetailRepository;
	
	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	IClientRepository clientRepository;
	
	@Autowired
	IAddressRepository addressRepository;
	
	@Autowired
	IOrderDetailService orderDetailService;
	
	@Autowired
	IProductService productService;
	
	@Autowired
	ClientServiceImpl clientService;
	
	@Autowired
	IEmployeeService employeeService;
	
	@Autowired
	EmailServiceImpl emailService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public OrderDTO createOrder(CreateOrderDTO createOrderDTO, String token) throws  AuthException{
		String auxToken;
		auxToken=token.substring(7);
		
		String authClient= jwtUtil.getNameOfUser(auxToken);
		
		if (createOrderDTO.getClientId() == null)
			setAddressesForUnregisteredClient(createOrderDTO);
		else {
			Client client = clientService.findClientById(createOrderDTO.getClientId());
			
			if (client.getUser().getUsername().equals(authClient))
				setAddressesForRegisteredClient(client, createOrderDTO);
			else
				throw new AuthException();
		}
		Order order = modelMapper.map(createOrderDTO, Order.class);
		Set<OrderDetail> orderDetails = createOrderDetailFromProductQuantity(order, createOrderDTO.getProductsQuantity());
//		order.setOrderDetails(orderDetails);
		order.setTotal(calculateTotalFromOrderDetail(orderDetails));
		orderRepository.save(order);
		
		if (createOrderDTO.getClientId() != null) {
			emailService.sendOrderConfirmationEmail(clientService.findClientById(createOrderDTO.getClientId()));
		}
		
		modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE).setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
		return modelMapper.map(order, OrderDTO.class);
	}
	
	private double calculateTotalFromOrderDetail(Set<OrderDetail> orderDetails) {
		return orderDetails.stream().collect(Collectors.summingDouble(OrderDetail::getSubtotal));
	}
	
	private Set<OrderDetail> createOrderDetailFromProductQuantity(Order order, Map<Integer, Integer> productQuantity) {
		return productQuantity.entrySet().stream().map(set -> orderDetailService.createOrderDetail(set.getValue(), productService.findProductById(set.getKey()), order)).collect(Collectors.toSet());
	}
	
	private void setAddressesForUnregisteredClient(CreateOrderDTO order) {
		if(order.getBillingAddress() == null) throw new ArgumentNotFoundException("A billing address is necessary to process a new order from an unregistered client");
		if(order.getShippingAddress() == null) {
			order.setShippingAddress(order.getBillingAddress());
		}
	}
	
	private void setAddressesForRegisteredClient(Client client, CreateOrderDTO order) {
		if(order.getBillingAddress() == null)
			order.setBillingAddress(client.getAddress());
		
		if(order.getShippingAddress() == null & client.getShippingAddress() == null)
			order.setShippingAddress(order.getBillingAddress());
		else if (order.getShippingAddress() == null)
			order.setShippingAddress(client.getShippingAddress());
	}
	
	@Override
	@Transactional(readOnly = true)
	public Order findOrderById(UUID id) {
		return orderRepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OrderDTO> findAllOrders() {
		/*if (orderRepository.findAll().isEmpty()) {
			throw new ArgumentNotFoundException("No orders found");
		} else {*/
			return orderRepository.findAll().stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
		//}
	}
	
	@Override
	@Transactional
	public void updateOrder(Order order) {
		// Checks if it's an existing order
		if (orderRepository.findById(order.getId()).isEmpty())
			throw new ArgumentNotFoundException("The order doesn't exists. The order with id "+ order.getId()+ " doesn't exist.");
		
		Order orderToUpdate = findOrderById(order.getId());
		
		// Checks if parameters are valid and updates them
		if (clientRepository.findById(order.getClientId()).isEmpty())
			throw new ArgumentNotFoundException("The client doesn't exist. The client with the id " + order.getClientId() + "doesn't exist");
		else if (order.getClientId() == null) {
			/*if (clientRepository.findById((order.getClientId())).isEmpty()) {  //UUID.fromString //Codigo B49.
				throw new ArgumentNotFoundException("The client doesn't exist. The client with the id " + order.getClientId() + "doesn't exist");
			} else if (order.getClientId() == null) {*/

			throw new ArgumentNotValidException("Invalid Client ID");
		}
		
		orderToUpdate.setclientId(order.getClientId());
		
		//TODO: Once Employee is implemented it should check if it exists.1
		orderToUpdate.setEmployeeId(order.getEmployeeId());
		
		if (order.getStatus() == null)
			throw new ArgumentNotValidException("Status can't be null");
		
		/*orderToUpdate.setStatus(order.getStatus());
		if (!order.getOrderProducts().isEmpty())
			orderToUpdate.setOrderProducts(order.getOrderProducts());
		else
			throw new ArgumentNotValidException("Invalid products in the order");*/
		
		orderRepository.save(orderToUpdate);
	}
	
	@Override
	public void deleteOrder(UUID id) {
		orderRepository.deleteById(id);
	}
	
	@Override
	public List<Order> findOrdersByStatus(OrderStatus status) {
		if(orderRepository.findByStatus(status) == null)
			throw new ArgumentNotFoundException("No orders found");
		else
			return orderRepository.findByStatus(status);
	}
	
	@Override
	public List<Order> findOrdersByClient(UUID id) {
		if (orderRepository.findByClientId(id).isEmpty())
			throw new ArgumentNotFoundException("No orders found for this client");
		else
			return orderRepository.findByClientId(id);
	}
	
	@Override
	public List<Order> findOrdersByEmployeeId(UUID employeeId) {
		if (orderRepository.findByEmployeeId(employeeId).isEmpty()) {
			throw new ArgumentNotFoundException("No orders found for this employee");
		} else {
			return orderRepository.findByEmployeeId(employeeId);
		}
	}
	
	@Override
	public List<TopEmployeeDTO> findTopTenMonth(int year, int month) {
				List<Object[]> repositoryQueryList = orderRepository.findEmployeesSalesBetweenDates(

						LocalDateTime.of(year, month, 1, 1, 0, 0),
						LocalDateTime.of(year, month, 31, 12, 59, 59, 59));  //Busqueda en BD
		
		List<TopEmployeeDTO> topEmployeeList = new ArrayList<>();
		
		for (Object[] object : repositoryQueryList) {
			TopEmployeeDTO topEmployeeDTO = new TopEmployeeDTO();
			
			topEmployeeDTO.setId(UUID.fromString(object[0].toString()));
			topEmployeeDTO.setTotal(Double.parseDouble(object[1].toString()));
			topEmployeeDTO.setDni((object[2].toString()));
			topEmployeeList.add(topEmployeeDTO);
		}
		
		return topEmployeeList;
	}
	
	@Override
	public List<TopEmployeeDTO> findAllTopTen(DatesTopEmployeePOJO datestopemployee) {
		List<Object[]> repositoryQueryList = orderRepository.findEmployeesSalesBetweenDates(datestopemployee.getBegin_date(),datestopemployee.getEnd_date());  //Busqueda en BD
		
		List<TopEmployeeDTO> topEmployeeList = new ArrayList<>();
		
		for (Object[] object : repositoryQueryList) {
			TopEmployeeDTO topEmployeeDTO = new TopEmployeeDTO();
			
			topEmployeeDTO.setId(UUID.fromString(object[0].toString()));
			topEmployeeDTO.setTotal(Double.parseDouble(object[1].toString()));
			topEmployeeDTO.setDni((object[2].toString()));
			topEmployeeList.add(topEmployeeDTO);
		}
		
		return topEmployeeList;
	}
	
	/**
	 * This method gets the best Employee by the sum of all completed sales and returns it's EmployeeSalesDTO
	 */
	
	@Override
	public EmployeeSalesDTO getBestEmployeeByTotalSales() {
		Map<UUID, Double> mapEmployeesTotalSales = getMapOfEmployeesIdAndTotalSales();
		
		UUID bestEmployeeId = Collections.max(mapEmployeesTotalSales.entrySet(), Map.Entry.comparingByValue()).getKey();
		double totalSales = mapEmployeesTotalSales.get(bestEmployeeId);
		
		return mapEmployeeSalesDTO(bestEmployeeId, totalSales);
	}
	
	/**
	 * This method gets the worst Employee by the sum of all completed sales and returns it's EmployeeSalesDTO
	 */
	
	@Override
	public EmployeeSalesDTO getWorstEmployeeByTotalSales() {
		Map<UUID, Double> mapEmployeesTotalSales = getMapOfEmployeesIdAndTotalSales();
		
		UUID worstEmployeeId = Collections.min(mapEmployeesTotalSales.entrySet(), Map.Entry.comparingByValue()).getKey();
		double totalSales = mapEmployeesTotalSales.get(worstEmployeeId);
		
		return mapEmployeeSalesDTO(worstEmployeeId, totalSales);
	}	
	
	/**
	 * This method returns a map of Employee Id as key and the sum of all completed sales by that employee as value
	 * @throws ArgumentNotFoundException if there are no orders completed
	 * @return Map<UUID, Double>
	 */
	
	private Map<UUID, Double> getMapOfEmployeesIdAndTotalSales() {
		OrderStatus status = OrderStatus.COMPLETED;
		
		if (orderRepository.findAllByStatus(status).isEmpty())
			throw new ArgumentNotFoundException("There are no completed orders");
		
		Map<UUID, Double> mapEmployeesTotalSales = orderRepository.findAllByStatus(status)
			.stream()
			.collect(Collectors.groupingBy(Order::getEmployeeId,
				Collectors.summingDouble(Order::getTotal)));
		
		return mapEmployeesTotalSales;
	}
	
	/**
	 * This method maps an Employee (from it's Id) and the total value of that employee's sales to an EmployeeSalesDTO
	 * @param employeeId Id of the Employee to map
	 * @param totalSales Sum of all of employee's completed sales
	 * @return EmployeeSalesDTO
	 */
	
	private EmployeeSalesDTO mapEmployeeSalesDTO(UUID employeeId, double totalSales) {
		EmployeeSalesDTO employeeSalesDTO = new EmployeeSalesDTO();
		
		employeeSalesDTO.setEmployee(employeeService.findEmployeeById(employeeId));
		employeeSalesDTO.setTotalSales(totalSales);
		
		return employeeSalesDTO;
	}
	
	/**
	 * This method request the sum of all completed Orders for a year
	 * @return double
	 */
	
	@Override
	public double getProfitByYear(int year) {
		double profit = 0;
		
		try {
			profit = orderRepository.findProfitBetweenDates(
					LocalDateTime.of(year, 1, 1, 0, 0),
					LocalDateTime.of(year, 12, 31, 12, 59, 59, 59));
		} catch (AopInvocationException e) {
			if (profit== 0) throw new ArgumentNotFoundException("There are no completed orders for year " + year);
		}
		
		return profit;
	}
	
	/**
	 * This method request the sum of all completed Orders for a month
	 * @return double
	 */
	
	@Override
	public double getProfitByMonth(int year, int month) {
		double profit = 0;
		
		try {
			profit = orderRepository.findProfitBetweenDates(
				LocalDateTime.of(year, month, 1, 0, 0),
				LocalDateTime.of(year, month, 1, 23, 59,59,59).with(TemporalAdjusters.lastDayOfMonth()));
		} catch (AopInvocationException e) {
			if (profit== 0) throw new ArgumentNotFoundException("There are no completed orders for " +  new DateFormatSymbols().getMonths()[month-1] + " " + year);
		}
		
		return profit;
	}
	
	@Override
	public HashMap<String, Long> countOrdersByfield(String field) {
		if (field.equals("status"))		
			return calculateCountOrdersByfield(orderRepository.findAllOrdersByStatus(), OrderStatus.values() );
		else 
			return calculateCountOrdersByfield(orderRepository.findAllOrdersByPayment(), PaymentMethod.values() );						
	}
	
	private <T> HashMap<String, Long> calculateCountOrdersByfield(List<String> ordersByField, T[] enumFieldValues) {
		List<String> ordersNotNull = ordersByField.stream().filter(c -> c != null).collect(Collectors.toList());
		HashMap<String, Long> map = new HashMap<>();
		
		if (ordersByField.isEmpty())
			throw new ArgumentNotFoundException("No orders found");
		else {
			long count = 0;
			for (T value : enumFieldValues) {
				count = ordersNotNull.stream().filter(c -> (c.equals(value.toString()))).count();
				map.put(value.toString().toLowerCase(), count);
				count = 0;
			}
		}
		
		return map;
	}


	
}