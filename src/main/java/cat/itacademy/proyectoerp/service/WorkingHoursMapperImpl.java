package cat.itacademy.proyectoerp.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.WorkingHoursToStringDTO;

@Component
public class WorkingHoursMapperImpl implements IWorkingHoursMapper {

	@Override
	public List<WorkingHoursToStringDTO> workingHoursToAllDtos(List<WorkingHours> hours) {
		if (hours == null) {
			return null;
		}

		List<WorkingHoursToStringDTO> list = new ArrayList<WorkingHoursToStringDTO>(hours.size());
		for (WorkingHours workingHours : hours) {
			list.add(workingHoursToAllDto(workingHours));
		}

		return list;
	}

	@Override
	public WorkingHoursToStringDTO workingHoursToAllDto(WorkingHours workingHours) {
		if (workingHours == null) {
			return null;
		}

		WorkingHoursToStringDTO workingHoursToStringDTO = new WorkingHoursToStringDTO();
		workingHoursToStringDTO.setCheckIn(workingHours.checkInToString());
		workingHoursToStringDTO.setCheckOut(workingHours.checkOutToString());
		workingHoursToStringDTO.setDate(workingHours.localDateToString());
		workingHoursToStringDTO.setEmployeeId(workingHours.getEmployeeId());

		return workingHoursToStringDTO;
	}

}
