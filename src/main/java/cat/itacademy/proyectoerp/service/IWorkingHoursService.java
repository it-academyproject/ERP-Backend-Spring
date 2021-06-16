package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IWorkingHoursService {

  public WorkingHours createWorkingHours(LocalDateTime checkIn, Employee employee);

  public WorkingHours findWorkingHoursById(UUID id);

  public List<WorkingHours> findAllWorkingHours();

  public WorkingHours updateWorkingHours(WorkingHours workingHours) throws Exception;

  public void deleteWorkingHours(UUID id);

}
