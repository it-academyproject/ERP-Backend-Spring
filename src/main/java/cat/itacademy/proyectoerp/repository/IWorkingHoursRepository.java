package cat.itacademy.proyectoerp.repository;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkingHoursRepository extends JpaRepository<WorkingHours, UUID> {
	
	List<WorkingHours> findAll();
		
	List<WorkingHours> findWorkingHoursByEmployeeId(UUID employeeId);
	
	List<WorkingHours> findWorkingHoursByDate(LocalDate date);
	
	WorkingHours findWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date);
	
	void deleteWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date);

}
