package cat.itacademy.proyectoerp.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.ChangeUserPassword;
import cat.itacademy.proyectoerp.domain.Client;
import cat.itacademy.proyectoerp.domain.StandarRegistration;
import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.dto.MessageDTO;
import cat.itacademy.proyectoerp.dto.UserDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.security.entity.JwtLogin;
import cat.itacademy.proyectoerp.security.entity.JwtResponse;
import cat.itacademy.proyectoerp.security.jwt.JwtUtil;
import cat.itacademy.proyectoerp.security.service.UserDetailServiceImpl;
import cat.itacademy.proyectoerp.service.ClientServiceImpl;
import cat.itacademy.proyectoerp.service.UserServiceImpl;

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

		UserDTO userDTO;

		userDTO = userService.registerNewUserAccount(user);

		if (userDTO.getSuccess() == "False") {

			return new ResponseEntity<>(userDTO, HttpStatus.UNPROCESSABLE_ENTITY);
		} else {

			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		}
	}
	
	/**
	 * Method for create a new user and client.
	 * 
	 * @param standar JSON with StandarRegistration data
	 * @return Welcome String.
	 */
	@RequestMapping(value = "/users/clients", method = RequestMethod.POST)
	public ResponseEntity<?> newUserAndClient(@Valid @RequestBody StandarRegistration standar) {
		
		User userRegistered = new User(standar.getUsername(),standar.getPassword());
		UserDTO userDTO;
		
		userDTO = userService.registerNewUserAccount(userRegistered);
		
		if (userDTO.getSuccess() == "False") {
			return new ResponseEntity<>(userDTO, HttpStatus.UNPROCESSABLE_ENTITY);
			
		} else {
			try {
				Client clientRegistered = new Client(standar.getAddress(),standar.getDni(),
						standar.getImage(),standar.getName_surname(),userRegistered);
	    		clientService.createClient(clientRegistered);
	    	} catch (ArgumentNotValidException e) {
	    		return ResponseEntity.unprocessableEntity().body(e.getMessage());
	    	}	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
		}
	
    }

	/**
	 * Method for user login
	 * 
	 * @param jwtLogin JSON with credentials.
	 * @return String with message: Success or unauthorized.
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> loginUser(@Valid @RequestBody JwtLogin jwtLogin){

		JwtResponse jwtResponse;
		UserDetails userDetails;

		try {
			/*
			SecurityContextHolder.getContext()
					.setAuthentication(authenticate(jwtLogin.getUsername(), jwtLogin.getPassword()));
			 */
			userDetails = userDetailService.loadUserByUsername(jwtLogin.getUsername());

			SecurityContextHolder.getContext()
					.setAuthentication(authenticate(jwtLogin.getUsername(), jwtLogin.getPassword()));

		} catch (Exception e) {
			MessageDTO messageDto = new MessageDTO("False", e.getMessage());
			return ResponseEntity.unprocessableEntity().body(messageDto);
		}

		final String token = jwtUtil.generateToken(userDetails);
		// Create JSON to Response to client.
		jwtResponse = new JwtResponse(token, jwtLogin.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
	}

	/**
	 * Method for list all users
	 * 
	 * @return all users
	 */
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> listAllUsers() {
		return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
	}

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
		userDto = userService.modifyUser(id, user).get();
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
			userService.recoverPassword(user.getUsername());

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
	 * @param user
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
