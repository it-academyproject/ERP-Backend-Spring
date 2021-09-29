package cat.itacademy.proyectoerp.repository;

import cat.itacademy.proyectoerp.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, UUID> {

	@Query(value = "select sum(salary) as salary from employee ", nativeQuery = true)
	double getTotalSalariesForYear();

	Optional<Employee> getEmployeeByUserId(Long id);

}
