package cat.itacademy.proyectoerp.controller;


import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.dto.MessageDTO;
import cat.itacademy.proyectoerp.dto.UserDTO;
import cat.itacademy.proyectoerp.services.UserServiceImpl;
/**
 * Class of User Controller 
 * @author Rubén Rodríguez 
 *
 */
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	/**
	 * Method for all url which don't exist
	 * @return a informative message
	 */
	@RequestMapping(value ="**")
	public ResponseEntity<MessageDTO> otherUrl(){
		MessageDTO messageDto = new MessageDTO("False","Url don't exist");
		
		return new ResponseEntity<>(messageDto, HttpStatus.NOT_FOUND);
	}

	
	/**
	 * Method for create a new user.
	 * @param user  JSON with User data
	 * @return Welcome String.
	 */
	@RequestMapping(value ="/users", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> newUser(@Valid @RequestBody User user) {
		
		UserDTO userDTO;
		
		userDTO= userService.registerNewUserAccount(user);
		if (userDTO.getSuccess() == "False")
			return new ResponseEntity<>(userDTO, HttpStatus.UNPROCESSABLE_ENTITY);	
		return 	 new ResponseEntity<>(userDTO, HttpStatus.OK);	

	}
	
	
	
	/**
	 * Method for user login
	 * @param user JSON with credentials.
	 * @return String with message: Succes or unauthorized.
	 */
	@RequestMapping(value ="/login", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> loginUser(@RequestBody User user) {
		
		
		return new ResponseEntity<>(userService.getByUsername(user.getUsername()).get(), HttpStatus.OK);
		
	}
	
	/**
	 * Method for list all users
	 * @return all users
	 */
	@GetMapping("/users") 
	public ResponseEntity<List<UserDTO>> listAllUsers( ) {
	    return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
	}
	
	
	/**
	 * Method for list all user with user_type = EMPLOYEE
	 * @return all employees
	 */
	@GetMapping("/users/employees") 
	public ResponseEntity<List<UserDTO>> listAllEmployeeUsers( ) {
	    return new ResponseEntity<>(userService.listAllEmployees(), HttpStatus.OK);
	}
	
	/**
	 * Method for list all user with user_type = CLIENTS
	 * @return all clients
	 */
	@GetMapping("/users/clients") 
	public ResponseEntity<List<UserDTO>> listAllClientsUsers( ) {
	    return new ResponseEntity<>(userService.listAllClients(), HttpStatus.OK);
	}
	
	/**
	 * Method for delete user by user ID.
	 * @param id    user ID
  	 * @return OK if user exist. Not OK if user don't exists.
	 */
	@DeleteMapping("/users")
	public ResponseEntity<UserDTO>  deleteUserById(@RequestBody User user) {
		Long id= user.getId();
		
		UserDTO userDto;
		userDto = userService.deleteUserById(id).get();
		if (userDto.getSuccess() == "False")
			return new ResponseEntity<>(userDto, HttpStatus.UNPROCESSABLE_ENTITY);	
		return 	 new ResponseEntity<>(userDto, HttpStatus.OK);	
	
	}
	
	/**
	 * Method for modify the type of user.
	 * @param id Id of user.
	 * @param String new type of user.  
	 * @return OK if user exists.
	 */
	@PutMapping("/users")
	public ResponseEntity<UserDTO> modifyTypeUser(@RequestBody User user) {
		
		UserDTO userDto;
		Long id = user.getId();
		userDto = userService.modifyUser(id, user).get();
		if (userDto.getSuccess() == "False")
			return new ResponseEntity<>(userDto, HttpStatus.UNPROCESSABLE_ENTITY);	
		return 	 new ResponseEntity<>(userDto, HttpStatus.OK);	
		}
		
	
}
