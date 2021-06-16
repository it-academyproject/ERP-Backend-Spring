package cat.itacademy.proyectoerp.repository;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IWorkingHoursRepository extends JpaRepository<WorkingHours, UUID> {

}
