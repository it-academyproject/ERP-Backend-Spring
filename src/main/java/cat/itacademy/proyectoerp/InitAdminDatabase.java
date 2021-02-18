package cat.itacademy.proyectoerp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;
import cat.itacademy.proyectoerp.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class InitAdminDatabase {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() {

		userRepo.save(new User("administrator@admin.com", passwordEncoder.encode("Administrator1."), UserType.ADMIN));
	}
	
	

}
