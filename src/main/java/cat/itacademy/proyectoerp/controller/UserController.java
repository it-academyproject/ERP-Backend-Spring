package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.*;
import cat.itacademy.proyectoerp.dto.*;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;
import cat.itacademy.proyectoerp.security.entity.JwtResponse;
import cat.itacademy.proyectoerp.security.jwt.JwtUtil;
import cat.itacademy.proyectoerp.security.service.UserDetailServiceImpl;
import cat.itacademy.proyectoerp.service.ClientServiceImpl;
import cat.itacademy.proyectoerp.service.IClientService;
import cat.itacademy.proyectoerp.service.IEmployeeService;
import cat.itacademy.proyectoerp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * Class of User Controller
 * 
 * @author
 *
 */

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserDetailServiceImpl userDetailService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	ClientServiceImpl clientService;

	@Autowired
	IEmployeeService iEmployeeService;

	@Autowired
	IClientService iClientService;

	/**
	 * Method for all url which don't exist
	 * 
	 * @return a informative message
	 */
	@RequestMapping(value = "**")
	public ResponseEntity<MessageDTO> otherUrl() {
		MessageDTO messageDto = new MessageDTO("False", "Url don't exist");

		return new ResponseEntity<>(messageDto, HttpStatus.NOT_FOUND);
	}

	/**
	 * Method for create a new user.
	 * 
	 * @param user JSON with User data
	 * @return Welcome String.
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> newUser(@Valid @RequestBody User user) {
		UserDTO userDTO = userService.registerNewUserAccount(user);

		if (userDTO.getSuccess() == "False")
			return new ResponseEntity<>(userDTO, HttpStatus.UNPROCESSABLE_ENTITY);
		else
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	/**
	 * Method for create a new user and client.
	 * 
	 * It is not required to be authenticated to use this method. Opened access in
	 * issue B-101.
	 * 
	 * @param standard JSON with StandarRegistration data
	 * @return Welcome String.
	 */
	@RequestMapping(value = "/users/clients", method = RequestMethod.POST)
	public ResponseEntity<?> newUserAndClient(@Valid @RequestBody StandardRegistration standard) {
		ClientDTO clientDTO;

		MessageDTO errorMessageDTO = getErrorMessage(standard);
		if (errorMessageDTO.getSuccess().equalsIgnoreCase("False")) {
			return new ResponseEntity<>(errorMessageDTO, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Client newClient = getClient(standard);

		try {
			UserDTO userDTO = userService.registerNewUserAccount(newClient.getUser());
			clientDTO = clientService.createClient(newClient);
			clientDTO.setUser(userDTO);
		} catch (Exception e) {
			// rollback
			userService.deleteUserById(newClient.getUser().getId());
			errorMessageDTO = new MessageDTO("False", e.getMessage());
			return ResponseEntity.unprocessableEntity().body(errorMessageDTO);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(clientDTO);
	}

	private MessageDTO getErrorMessage(StandardRegistration standard) {
		MessageDTO errorMessageDTO = new MessageDTO("True", "");
		MessageDTO errorMessageDni = clientService.getErrorMessageDniExists(standard.getDni());
		MessageDTO errorMessageUsername = userService.getErrorMessageUsernameExists(standard.getUsername());

		if (null != errorMessageDni) {
			errorMessageDTO.setSuccess("False");
			errorMessageDTO.setMessage(errorMessageDni.getMessage());
		} else if (null != errorMessageUsername) {
			errorMessageDTO.setSuccess("False");
			errorMessageDTO.setMessage(errorMessageUsername.getMessage());
		}

		return errorMessageDTO;
	}

	private Client getClient(StandardRegistration standard) {
		User user = new User(standard.getUsername(), standard.getPassword());

		Client client = new Client(standard.getDni(), standard.getImage(), standard.getNameAndSurname(),
				standard.getAddress(), standard.getShippingAddress(), user);

		return client;
	}

	/**
	 * Create a new user and employee
	 * 
	 * @param employee
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/users/employees")
	public ResponseEntity<?> newUserAndEmployee(@Valid @RequestBody Employee employee) {
		EmployeeDTO employeeDTO;

		User user = new User(employee.getUser().getUserName(), employee.getUser().getPassword(), UserType.EMPLOYEE);
		userService.registerNewUserAccount(user);

		employee.setOutDate(null != employee.getOutDate() ? employee.getOutDate() : null);
		Employee newEmployee = new Employee(employee.getName(), employee.getSurname(), employee.getSalary(),
				employee.getDni(), employee.getPhone(), employee.getInDate(), employee.getOutDate(), user);

		try {
			employeeDTO = iEmployeeService.createEmployee(newEmployee);
		} catch (Exception e) {
			// MessageDTO messageDTO = new MessageDTO("False", "Unexpected error");
			MessageDTO messageDTO = new MessageDTO("False", e.getMessage());
			return ResponseEntity.unprocessableEntity().body(messageDTO);
		}

		if (employeeDTO.getMessage().getSuccess().equalsIgnoreCase("True"))
			return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTO);

		return new ResponseEntity<>(employeeDTO, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	 * Method for user login
	 * 
	 * @param jwtLogin JSON with credentials.
	 * @return String with message: Success or unauthorized.
	 * @throws BadCredentialsException, when login fails due to wrong password
	 * @throws Exception
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> loginUser(@Valid @RequestBody JwtLogin jwtLogin) {
		JwtResponse jwtResponse;
		UserDetails userDetails;
		String userName = jwtLogin.getUsername();
		Long userId = userService.findByUsername(userName).getId(); 
		String password = jwtLogin.getPassword();
		try {
			userDetails = userDetailService.loadUserByUsername(userName);
			SecurityContextHolder.getContext()
					.setAuthentication(authenticate(userName, password));

			/* Update User lastSession after successful login 
			 * or throw LockException if user account is locked */
			userService.updateLastSession(userName);
		} catch (BadCredentialsException badCredentsEx) {
			String message = userService.handlePasswordFail(jwtLogin.getUsername());
			MessageDTO messageDto = new MessageDTO("False", message);
			return ResponseEntity.unprocessableEntity().body(messageDto);
		} catch (Exception e) {
			MessageDTO messageDto = new MessageDTO("False", e.getMessage());
			return ResponseEntity.unprocessableEntity().body(messageDto);
		}
		
		final String token = jwtUtil.generateToken(userDetails);
		// Create JSON to Response to client.
		jwtResponse = new JwtResponse(token, userName, userId, userDetails.getAuthorities());
		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
	}

	/**
	 * Method for list all users
	 * 
	 * @return all users
	 */
//	@GetMapping("/users")
//	public ResponseEntity<List<UserDTO>> listAllUsers() {
//		return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
//		
//	}

//---------------------------------------------------------------------------	
	
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> listAllUsers() {
		return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
		
	}
	
   	
	
	/**
	 * Method for search list of users by userType.
	 * 
	 * @param user_type
	 * @return list of users by userType.
	 */
//	List<User> findByUserType(UserType user_type);
	
	

//---------------------------------------------------------------------------	
	
	
	
	/**
	 * Method for list all user with userType = EMPLOYEE
	 * 
	 * @return all employees
	 */
	@GetMapping("/users/employees")
	public ResponseEntity<List<UserDTO>> listAllEmployeeUsers() {
		return new ResponseEntity<>(userService.listAllEmployees(), HttpStatus.OK);
		
	}

	/**
	 * Method for list all user with userType = CLIENTS
	 * 
	 * @return all clients
	 */
	@GetMapping("/users/clients")
	public ResponseEntity<List<UserDTO>> listAllClientsUsers() {
		return new ResponseEntity<>(userService.listAllClients(), HttpStatus.OK);
	}

	/**
	 * Method for delete user by user ID.
	 * 
	 * @param user user ID
	 * @return OK if user exist. Not OK if user don't exists.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/users")
	public ResponseEntity<UserDTO> deleteUserById(@RequestBody User user) {
		Long id = user.getId();

		UserDTO userDto;
		userDto = userService.deleteUserById(id).get();
		if (userDto.getSuccess() == "False")
			return new ResponseEntity<>(userDto, HttpStatus.UNPROCESSABLE_ENTITY);
		return new ResponseEntity<>(userDto, HttpStatus.OK);

	}

	/**
	 * Method for modify the type of user.
	 *
	 * @param user new type of user.
	 * @return OK if user exists.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/users")
	public ResponseEntity<UserDTO> modifyTypeUser(@Valid @RequestBody User user) {

		UserDTO userDto;
		Long id = user.getId();
		userDto = userService.setUser(id, user).get();
		if (userDto.getSuccess() == "False")
			return new ResponseEntity<>(userDto, HttpStatus.UNPROCESSABLE_ENTITY);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	/**
	 * Method to set user subscription to inactive
	 * 
	 * @param user
	 * @return user "active" field updated
	 */

	@PutMapping("/users/unsubscribe")
	public ResponseEntity<UserDTO> unsubscribeUser(@RequestBody User user) {

		UserDTO userDto = userService.setSubscription(user);
		if (userDto.getSuccess() == "False")
			return new ResponseEntity<>(userDto, HttpStatus.UNPROCESSABLE_ENTITY);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	/**
	 * Method to recover password (Generate a new password and send it by email to
	 * the user)
	 * 
	 * @param user username of user
	 * @return password
	 */
	@PutMapping("/users/recoverpassword")
	public HashMap<String, Object> recoverPassword(@RequestBody User user) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			userService.recoverPassword(user.getUserName());

			map.put("success", "true");
			map.put("message", "the new password has been sent");

		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", e.getMessage());
		}

		return map;
	}

	private Authentication authenticate(String username, String password) throws Exception {

		try {
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new DisabledException("User disabled");
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid user credentials");
		}
	}

	/**
	 * Method to reset password
	 * 
	 * @param changeuserpassword
	 * @return user updated
	 */
	@PutMapping("/users/resetpassword")

	public HashMap<String, Object> resetPassword(@Valid @RequestBody ChangeUserPassword changeuserpassword) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			userService.updatePassword(changeuserpassword);

			map.put("success", "true");
			map.put("message", "User password updated");

		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", e.getMessage());
		}

		return map;
	}

}