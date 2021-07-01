package cat.itacademy.proyectoerp.repository;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.domain.WorkingHoursId;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkingHoursRepository extends JpaRepository<WorkingHours, UUID> {
	
	List<WorkingHoursDTO> findByEmployeeId(UUID employeeId);
	
	WorkingHours findByEmployeeIdDate(WorkingHoursId workingHoursId);
	
	void deleteByEmployeeIdDate(WorkingHoursId workingHoursId);

}
