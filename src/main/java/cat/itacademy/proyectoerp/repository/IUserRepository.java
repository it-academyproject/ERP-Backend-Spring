package cat.itacademy.proyectoerp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;

/**
 * Interface repository for User class.
 * @author ItAcademy 
 *
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
	
	
	/**
	 * Method for search a usename
	 * @return boolean if username exists.
	 */
	boolean existsWithUsername(String username);
	
	/**
	 * Method for search user by username.
	 * @param username
	 * @return User if user exist or null.
	 */
	User withUsername(String username);
	
	/**
	 * Method for search list of users by userType.
	 * @param userType
	 * @return list of users by userType.
	 */
	List<User> withUserType(UserType user_type);
	
	
}
