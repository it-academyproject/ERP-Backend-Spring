package cat.itacademy.proyectoerp.service;

import java.util.List;

import org.mapstruct.Mapper;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;
import cat.itacademy.proyectoerp.dto.WorkingHoursToStringDTO;
@Mapper
public interface IWorkingHoursMapper {
	
		 List<WorkingHoursToStringDTO> map(List<WorkingHoursDTO> workingHoursDTO);
}
