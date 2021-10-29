package cat.itacademy.proyectoerp.service;

import cat.itacademy.proyectoerp.dto.WorkingHoursToStringDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IWorkingHoursService {

	public List<WorkingHoursToStringDTO> findAllWorkingHours(); // READ - read Working Hours list

	public List<WorkingHoursToStringDTO> findWorkingHoursByEmployeeId(UUID employeeId);

	public List<WorkingHoursToStringDTO> findWorkingHoursByDate(LocalDate date);

	public void deleteWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date); // DELETE - delete product
																						// according to id

	WorkingHoursToStringDTO findWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date);

	public WorkingHoursToStringDTO createWorkingHours(WorkingHoursToStringDTO workingHoursToStringDTO)
			throws ArgumentNotValidException;

	public WorkingHoursToStringDTO updateWorkingHoursByEmployeeIdAndDate(WorkingHoursToStringDTO dto)
			throws ArgumentNotValidException;

}
