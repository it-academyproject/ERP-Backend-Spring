package cat.itacademy.proyectoerp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.proyectoerp.domain.User;

@RestController
@RequestMapping("/api")
public class UserController {
	
		
	@RequestMapping(value ="/user", method = RequestMethod.POST)
	public String NewUser( ) {
		return "hola";
		    		
	}
	
	@GetMapping("/prova")
	public String prova() {
		return "hola";
	}

	
}
