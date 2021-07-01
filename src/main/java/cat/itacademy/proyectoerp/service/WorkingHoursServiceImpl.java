package cat.itacademy.proyectoerp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.domain.WorkingHoursId;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IWorkingHoursRepository;

@Service
public class WorkingHoursServiceImpl implements IWorkingHoursService{

	@Autowired
	IWorkingHoursRepository workingHoursRepository;
	
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

	@Override
	public WorkingHours findWorkingHoursByEmployeeIdDate(WorkingHoursId workingHoursId) {

		return workingHoursRepository.findByEmployeeIdDate(workingHoursId); //.orElseThrow(() -> new ArgumentNotFoundException("WorkingHours not found. The id " + id + " doesn't exist"));
	}
	
	@Override
	public List<WorkingHoursDTO> findWorkingHoursByEmployeeId(UUID employeeId) {

		if (workingHoursRepository.findByEmployeeId(employeeId).isEmpty()) {
			throw new ArgumentNotFoundException("No Working Hours found for this Employee Id: " + employeeId);
		} else {
			return workingHoursRepository.findByEmployeeId(employeeId).stream().map(workingHours -> modelMapper.map(workingHours, WorkingHoursDTO.class)).collect(Collectors.toList());
		
		}
	}

	@Override
	public List<WorkingHoursDTO> findAllWorkingHours() {
		
		if(workingHoursRepository.findAll().isEmpty()){
			throw new ArgumentNotFoundException("No Working Hours found");
		} else {
			return workingHoursRepository.findAll().stream().map(workingHours -> modelMapper.map(workingHours, WorkingHoursDTO.class)).collect(Collectors.toList());
		
		}
	}

	@Override
	public WorkingHoursDTO updateWorkingHours(WorkingHoursDTO workingHoursDTO) throws ArgumentNotValidException {
		WorkingHours workingHours = modelMapper.map(workingHoursDTO, WorkingHours.class);
		
		if(workingHoursRepository.findByEmployeeIdDate(new WorkingHoursId(workingHours.getEmployeeId(), workingHours.getDate())) == null) {
		      throw new ArgumentNotFoundException("No WorkingHours found for this Employee Id and date");
		
		} else {
			workingHoursRepository.save(workingHours);
		}
		    
		return modelMapper.map(workingHours, WorkingHoursDTO.class);
	}

	@Override
	public void deleteWorkingHours(WorkingHoursId workingHoursId) {
		
		workingHoursRepository.deleteByEmployeeIdDate(workingHoursId);
		
	}
	
}