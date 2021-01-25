package cat.itacademy.proyectoerp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;

/**
 * Interface repository for User class.
 * @author Rubén Rodríguez
 *
 */
@Repository
public interface UserDao extends CrudRepository<User, Long> {
	
	
	/**
	 * Method for search a usename
	 * @return boolean if username exists.
	 */
	boolean existsByUsername(String username);
	
	/**
	 * Method for search user by username.
	 * @param username
	 * @return User if user exist or null.
	 */
	User findByUsername(String username);
	
	/**
	 * Method for search list of users by userType.
	 * @param user_type
	 * @return list of users by userType.
	 */
	List<User> findByUserType(UserType user_type);
	
	
}
