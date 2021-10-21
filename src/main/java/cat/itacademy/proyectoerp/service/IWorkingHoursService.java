package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;
import cat.itacademy.proyectoerp.dto.WorkingHoursToStringDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IWorkingHoursService {

  public WorkingHoursDTO createWorkingHours(WorkingHoursDTO workingHoursDTO); // CREATE - create new Working Hours
  
  public List<WorkingHoursToStringDTO> findAllWorkingHours(); // READ - read Working Hours list
  
  public List<WorkingHoursToStringDTO> findWorkingHoursByEmployeeId(UUID employeeId);
  
  public List<WorkingHoursToStringDTO> findWorkingHoursByDate(LocalDate date);

  public WorkingHoursDTO updateWorkingHoursByEmployeeIdAndDate(WorkingHoursDTO workingHoursDTO); // UPDATE - update Working Hours data

  public void deleteWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date); // DELETE - delete product according to id
  
 WorkingHoursToStringDTO findWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date);

}
