package cat.itacademy.proyectoerp.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;

/**
 * Interface repository for User class.
 * 
 * @author ItAcademy
 *
 */
@Repository
@Transactional
public interface IUserRepository extends JpaRepository<User, Long> {

	/**
	 * Method to search a username
	 * 
	 * @return boolean if username exists.
	 */
	boolean existsByUsername(String username);

	/**
	 * Method to search user by username.
	 * 
	 * @param username
	 * @return User if user exist or null.
	 */
	User findByUsername(String username);

	/**
	 * Method for search list of users by userType.
	 * 
	 * @param user_type
	 * @return list of users by userType.
	 */
	List<User> findByUserType(UserType user_type);

	/**
	 * Update the number of failed login attempts for a user
	 * 
	 * @param failAttempts
	 * @param username
	 */
	@Query("UPDATE User u SET u.failedLoginAttempts = ?1 WHERE u.username = ?2")
	@Modifying
	public void updateFailedLoginAttempts(int failAttempts, String username);

}