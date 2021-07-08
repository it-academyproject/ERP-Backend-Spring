package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.domain.WorkingHoursId;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IWorkingHoursService {

  public WorkingHoursDTO createWorkingHours(WorkingHoursDTO workingHoursDTO); // CREATE - create new Working Hours
  
  public List<WorkingHoursDTO> findAllWorkingHours(); // READ - read Working Hours list
  
  public List<WorkingHoursDTO> findWorkingHoursByEmployeeId(UUID employeeId);
  
  public List<WorkingHoursDTO> findWorkingHoursByDate(LocalDate date);

  public WorkingHoursDTO updateWorkingHoursByEmployeeIdAndDate(WorkingHoursDTO workingHoursDTO); // UPDATE - update Working Hours data

  public void deleteWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date); // DELETE - delete product according to id
  
  public WorkingHours findWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date);

}
