package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;

import java.util.List;
import java.util.UUID;

public interface IWorkingHoursService {

  public WorkingHoursDTO createWorkingHours(WorkingHoursDTO workingHoursDTO); // CREATE - create new Working Hours

  public WorkingHours findWorkingHoursById(UUID id); // READ - read data of a Working Hour according to id

  public List<WorkingHoursDTO> findAllWorkingHours(); // READ - read Working Hours list

  public WorkingHours updateWorkingHours(WorkingHours workingHours); // UPDATE - update Working Hours data

  public void deleteWorkingHours(UUID id); // DELETE - delete product according to id

}