package cat.itacademy.proyectoerp.repository;

import cat.itacademy.proyectoerp.domain.WorkingHours;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkingHoursRepository extends JpaRepository<WorkingHours, UUID> {

}
