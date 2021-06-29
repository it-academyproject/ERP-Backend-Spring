package cat.itacademy.proyectoerp.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.WorkingHours;
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
	public WorkingHours findWorkingHoursById(UUID id) {
		
		return workingHoursRepository.findById(id)
	            .orElseThrow(() -> new ArgumentNotFoundException("WorkingHours not found. The id " + id + " doesn't exist"));
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
	public WorkingHours updateWorkingHours(WorkingHours workingHours) {
		if(workingHoursRepository.findById(workingHours.getId()) == null){
		      throw new ArgumentNotFoundException("No WorkingHours found");
		    }
		    return workingHoursRepository.save(workingHours);
	}

	@Override
	public void deleteWorkingHours(UUID id) {
		workingHoursRepository.deleteById(id);
		
	}
	
}