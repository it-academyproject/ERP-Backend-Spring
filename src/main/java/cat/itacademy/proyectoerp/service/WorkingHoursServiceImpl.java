package cat.itacademy.proyectoerp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.Order;
import cat.itacademy.proyectoerp.domain.OrderDetail;
import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.repository.IOrderDetailRepository;
import cat.itacademy.proyectoerp.repository.IWorkingHoursRepository;

@Service
public class WorkingHoursServiceImpl implements IWorkingHoursService{

	@Autowired
	IWorkingHoursRepository iWorkingHoursRepository;

	@Override
	public WorkingHours createWorkingHours(LocalDateTime checkIn, Employee employee) {
		return new WorkingHours(checkIn, employee);
	}

	@Override
	public WorkingHours findWorkingHoursById(UUID id) {
		
		return iWorkingHoursRepository.findById(id)
	            .orElseThrow(() -> new ArgumentNotFoundException("WorkingHours not found. The id " + id + " doesn't exist"));
	}

	@Override
	public List<WorkingHours> findAllWorkingHours() {
		
		if(iWorkingHoursRepository.findAll().isEmpty()){
		      throw new ArgumentNotFoundException("No WorkingHours found");
		    }
		    return iWorkingHoursRepository.findAll();
	}

	@Override
	public WorkingHours updateWorkingHours(WorkingHours workingHours) {
		if(iWorkingHoursRepository.findById(workingHours.getId()) == null){
		      throw new ArgumentNotFoundException("No WorkingHours found");
		    }
		    return iWorkingHoursRepository.save(workingHours);
	}

	@Override
	public void deleteWorkingHours(UUID id) {
		iWorkingHoursRepository.deleteById(id);
		
	}
	
}