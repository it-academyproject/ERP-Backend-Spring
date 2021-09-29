package cat.itacademy.proyectoerp.repository;


import cat.itacademy.proyectoerp.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IClientRepository extends JpaRepository<Client,UUID> {
	

	/**
	 * Method for search a dni
	 * @return boolean if dni exists.
	 */
	boolean existsByDni(String dni);
	
	
	/**
	 * Method for search user by dni.
	 * @param dni
	 * @return Client if client exist or null.
	 */
	Client findByDni(String dni);
	
	/**
	 * Method for search a client by its user id
	 * @param id
	 * @return
	 */
	@Query(value = "SELECT * FROM clients c WHERE c.user_id = ?1", nativeQuery = true)
	Client findByUserId(Long userId);

}
