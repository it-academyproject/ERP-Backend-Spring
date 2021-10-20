package cat.itacademy.proyectoerp.service;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;
import cat.itacademy.proyectoerp.dto.WorkingHoursToStringDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IWorkingHoursRepository;

@Service
public class WorkingHoursServiceImpl implements IWorkingHoursService{

	private static final String String = null;
	

	@Autowired
	IWorkingHoursRepository workingHoursRepository;
	IWorkingHoursMapper iWorkingHoursMapper;
	ModelMapper modelMapper = new ModelMapper();
	
	

	@Override
	public WorkingHoursDTO createWorkingHours(WorkingHoursDTO workingHoursDTO) throws ArgumentNotValidException {
		WorkingHours workingHours = modelMapper.map(workingHoursDTO, WorkingHours.class);
		if (workingHours.getEmployeeId() == null) {
			throw new ArgumentNotValidException("An Employee ID is required");
			
		} else if ((workingHours.getCheckIn() == null) && (workingHours.getCheckOut() == null)) {
			throw new ArgumentNotValidException("Either the CheckIn time or Checkout time is required");
			
		} else if (workingHours.getDate() == null) {
			throw new ArgumentNotValidException("A Date is required");
									
		} else {
			workingHoursRepository.save(workingHours);
			
		}
		
		return modelMapper.map(workingHours, WorkingHoursDTO.class);
	}
	@Bean
	public ModelMapper modelMapper(){
	    return new ModelMapper();
	}
	@Override
	public  List<WorkingHoursToStringDTO> findAllWorkingHours() {
	
		
		if(workingHoursRepository.findAll().isEmpty()){
			throw new ArgumentNotFoundException("No Working Hours found");
		} else {
						
			return iWorkingHoursMapper.map(workingHoursRepository.findAll().stream().map(workingHours -> modelMapper.map(workingHours, WorkingHoursDTO.class)).collect(Collectors.toList()));
		}
	}
	
	@Override
	public List<WorkingHoursDTO> findWorkingHoursByEmployeeId(UUID employeeId) {

		if (workingHoursRepository.findWorkingHoursByEmployeeId(employeeId).isEmpty()) {
			throw new ArgumentNotFoundException("No Working Hours found for this Employee Id: " + employeeId);
		} else {
			return workingHoursRepository.findWorkingHoursByEmployeeId(employeeId).stream().map(workingHours -> modelMapper.map(workingHours, WorkingHoursDTO.class)).collect(Collectors.toList());
		
		}
	}
	
	@Override
	public List<WorkingHoursDTO> findWorkingHoursByDate(LocalDate date) {

		if (workingHoursRepository.findWorkingHoursByDate(date).isEmpty()) {
			throw new ArgumentNotFoundException("No Working Hours found for date: " + date);
		} else {
			return workingHoursRepository.findWorkingHoursByDate(date).stream().map(workingHours -> modelMapper.map(workingHours, WorkingHoursDTO.class)).collect(Collectors.toList());
		
		}
	}

	@Override
	public WorkingHours findWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date) {
		
		if (workingHoursRepository.findWorkingHoursByEmployeeIdAndDate(employeeId, date) == null) {
			throw new ArgumentNotFoundException("No WorkingHours found for this Employee Id and date");
		} else {

			return workingHoursRepository.findWorkingHoursByEmployeeIdAndDate(employeeId, date);
		}
	}

	@Override
	public WorkingHoursDTO updateWorkingHoursByEmployeeIdAndDate(WorkingHoursDTO workingHoursDTO) throws ArgumentNotValidException {
		WorkingHours workingHours = modelMapper.map(workingHoursDTO, WorkingHours.class);
		
		if(workingHoursRepository.findWorkingHoursByEmployeeIdAndDate(workingHours.getEmployeeId(), workingHours.getDate()) == null) {
		      throw new ArgumentNotFoundException("No WorkingHours found for this Employee Id and date");
		
		} else {
			workingHoursRepository.save(workingHours);
		}
		    
		return modelMapper.map(workingHours, WorkingHoursDTO.class);
	}

	@Override
	public void deleteWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date) {
		if(workingHoursRepository.findWorkingHoursByEmployeeIdAndDate(employeeId, date) == null) {
		      throw new ArgumentNotFoundException("No WorkingHours found for this Employee Id and date");
		
		} else {
			workingHoursRepository.deleteWorkingHoursByEmployeeIdAndDate(employeeId, date);
		}
		
	}
	
}