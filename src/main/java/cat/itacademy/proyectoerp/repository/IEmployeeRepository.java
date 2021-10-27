package cat.itacademy.proyectoerp.repository;

import cat.itacademy.proyectoerp.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, UUID> {

	@Query(value = "select sum(salary) as salary from employee ", nativeQuery = true)
	double getTotalSalariesForYear();

	Employee getEmployeeByUserId(Long id);
	
	
	//finds an employee from its user id
	@Query(value = "SELECT * FROM employees c WHERE c.user_id = ?1", nativeQuery = true)
	Employee findByUserId(Long userId);

}
