package cat.itacademy.proyectoerp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.User;
/**
 * Class of User Controller 
 * @author Rubén Rodríguez 
 *
 */
@RestController
@RequestMapping("/api")
public class UserController {
	
	/**
	 * Method for create a new user.
	 * @param user  JSON with User data
	 * @return Welcome String.
	 */
	@RequestMapping(value ="/users", method = RequestMethod.POST)
	public String newUser(@RequestBody User user ) {
		
		return "Bienvenido "+user.getUsername();	    		
	}
	
	/**
	 * Method for user login
	 * @param user JSON with credentials.
	 * @return String with message: Succes or unauthorized.
	 */
	@RequestMapping(value ="/login", method = RequestMethod.POST)
	public String loginUser(@RequestBody User user) {
		if (user.getUsername().equals("foo") && user.getPassword().equals("foo"))
			return "Success";
		else return "unauthorized";
		
	}
	
	/**
	 * Method for list all users
	 * @return all users
	 */
	@GetMapping("/users") 
	public String listAllUsers( ) {
	    return "lista";
	}
	
	
	/**
	 * Method for list all user with user_type = EMPLOYEE
	 * @return all employees
	 */
	@GetMapping("/users/employees") 
	public String listAllEmployeeUsers( ) {
	    return "lista de todos los Employees";
	}
	
	/**
	 * Method for list all user with user_type = CLIENTS
	 * @return all clients
	 */
	@GetMapping("/users/clients") 
	public String listAllClientsUsers( ) {
	    return "lista de todos los Clients";
	}
	
	/**
	 * Method for delete user by user ID.
	 * @param id    user ID
  	 * @return OK if user exist. Not OK if user don't exists.
	 */
	@DeleteMapping("/users/{id}")
	public String  deleteUserById(@PathVariable(name="id") Long id) {
		return "Usuario eliminado";
	}
	
	/**
	 * Method for modify the type of user.
	 * @param id Id of user.
	 * @param String new type of user.  
	 * @return OK if user exists.
	 */
	@PutMapping("/users/{id}")
	public String modifyTypeUser(@PathVariable(name="id") Long id, @RequestBody String newType) {
		
		return "type modified";
	}
		
	
}
