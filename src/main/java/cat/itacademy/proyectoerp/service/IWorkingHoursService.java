package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;

import java.util.List;
import java.util.UUID;

public interface IWorkingHoursService {

  public WorkingHoursDTO createWorkingHours(WorkingHours workingHours);

  public WorkingHoursDTO findWorkingHoursById(UUID id);

  public List<WorkingHoursDTO> findAllWorkingHours();

  public WorkingHoursDTO updateWorkingHours(WorkingHours workingHours) throws Exception;

  public void deleteWorkingHours(UUID id);

}
