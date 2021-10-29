package cat.itacademy.proyectoerp.service;

import java.util.List;

import org.mapstruct.Mapper;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.WorkingHoursToStringDTO;
@Mapper
public interface IWorkingHoursMapper {
	
		List<WorkingHoursToStringDTO> workingHoursToAllDtos(List<WorkingHours> hours);

		WorkingHoursToStringDTO workingHoursToAllDto(WorkingHours workingHours);

		WorkingHours workingHoursStringDTOToEntity(WorkingHoursToStringDTO workingHoursToStringDTO);
}
