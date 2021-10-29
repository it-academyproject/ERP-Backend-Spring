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
	public WorkingHoursToStringDTO workingHoursToAllDto(WorkingHours entity) {
		if (entity == null) {
			return null;
		}

		WorkingHoursToStringDTO dto = new WorkingHoursToStringDTO();
		dto.setCheckIn(entity.getCheckInToString());
		dto.setCheckOut(entity.getCheckOutToString());
		dto.setDate(entity.getLocalDateToString());
		dto.setEmployeeId(entity.getEmployeeId());

		return dto;
	}

	@Override
	public WorkingHours workingHoursStringDTOToEntity(WorkingHoursToStringDTO dto) {
		if (dto == null) {
			return null;
		}

		WorkingHours entity = new WorkingHours();
		entity.setCheckInStringToDate(dto.getCheckIn());
		entity.setCheckOutStringToDate(dto.getCheckOut());
		entity.setLocalDateStringToDate(dto.getDate());
		entity.setEmployeeId(dto.getEmployeeId());

		return entity;
	}

}
