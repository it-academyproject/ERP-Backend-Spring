package cat.itacademy.proyectoerp.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;
import cat.itacademy.proyectoerp.dto.WorkingHoursToStringDTO;
@Component
public class WorkingHoursMapperImpl implements IWorkingHoursMapper {

	@Override
	public List<WorkingHoursToStringDTO> map(List<WorkingHoursDTO> hours) {
		if (hours == null) {
			return null;
		}

		List<WorkingHoursToStringDTO> list = new ArrayList<WorkingHoursToStringDTO>(hours.size());
		for (WorkingHoursDTO hour : hours) {
			list.add(toDto(hour));
		}

		return list;
	}

	protected WorkingHoursToStringDTO toDto(WorkingHoursDTO workingHoursDTO) {

		if (workingHoursDTO == null) {
			return null;
		}

		WorkingHoursToStringDTO workingHoursToStringDTO = new WorkingHoursToStringDTO
				.WorkingHoursToStringDTOBuilder()
				.withCheckIn(DateTimeFormatter.ofPattern("HH:mm:ss").format(workingHoursDTO.getCheckIn())
				.withCheckOut(DateTimeFormatter.ofPattern("HH:mm:ss").format(workingHoursDTO.getCheckOut())
				.withDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(workingHoursDTO.getDate())
				.withEmployeeId(workingHoursDTO.getEmployeeId())
				.build();

		return workingHoursToStringDTO;

	}

}
