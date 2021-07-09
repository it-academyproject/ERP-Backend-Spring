package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.domain.WorkingHoursId;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;
import cat.itacademy.proyectoerp.helpers.Responsehelper;
import cat.itacademy.proyectoerp.service.WorkingHoursServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/workinghours")
public class WorkingHoursController {

  @Autowired
  WorkingHoursServiceImpl workingHoursService;
  
  @Autowired
  Responsehelper responsehelper;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping()
  public HashMap<String, Object> getWorkingHours(){
    HashMap<String, Object> map = new HashMap<String, Object>();
    try {
      List<WorkingHoursDTO> workingHoursList = workingHoursService.findAllWorkingHours();
      
      map.putAll(responsehelper.responseWasOkWithEntity("true", "working hours found", "workingHours", workingHoursList));
      
    } catch (Exception e) {
    	map.putAll(responsehelper.responsewaswrong("false", e.getMessage()));
    }
    return map;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/employeeid/{employee_id}/date/{date}")
  public HashMap<String, Object> getWorkingHoursByEmployeeIdAndDate(@PathVariable(name="employee_id") UUID employeeId, @PathVariable(name="date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
    HashMap<String, Object> map = new HashMap<>();
    try {
      WorkingHours workingHours = workingHoursService.findWorkingHoursByEmployeeIdAndDate(employeeId, date);
      
      map.putAll(responsehelper.responseWasOkWithEntity("true", "working hours found", "workingHours", workingHours));
      
    } catch (Exception e){
    	map.putAll(responsehelper.responsewaswrong("false", e.getMessage()));
    }
    return map;
  }
  
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/employeeid/{employee_id}")
  public HashMap<String, Object> getWorkingHoursByEmployeeId(@PathVariable(name="employee_id") UUID employeeId){
    HashMap<String, Object> map = new HashMap<>();
    try {
      List<WorkingHoursDTO> workingHoursList = workingHoursService.findWorkingHoursByEmployeeId(employeeId);
      map.putAll(responsehelper.responseWasOkWithEntity("true", "working hours found", "workingHours", workingHoursList));
      
    } catch (Exception e){
    	map.putAll(responsehelper.responsewaswrong("false", e.getMessage()));
    }
    return map;
  }
  
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/date/{date}")
  public HashMap<String, Object> getWorkingHoursByDate(@PathVariable(name="date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
    HashMap<String, Object> map = new HashMap<>();
    try {
      List<WorkingHoursDTO> workingHoursList = workingHoursService.findWorkingHoursByDate(date);
      
      map.putAll(responsehelper.responseWasOkWithEntity("true", "working hours found", "workingHours", workingHoursList));
      
    } catch (Exception e){
    	map.putAll(responsehelper.responsewaswrong("false", e.getMessage()));
    }
    return map;
  }
  
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping()
	public Map<String, Object> createWorkingHours(@RequestBody WorkingHoursDTO workingHoursDTO) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			workingHoursService.createWorkingHours(workingHoursDTO);
			map.putAll(responsehelper.responseWasOkWithEntity("true", "New Working Hours created", "workingHours", workingHoursDTO));
			

		} catch (Exception e) {

			map.putAll(responsehelper.responsewaswrong("false", e.getMessage()));
		}

		return map;
	}

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  @DeleteMapping()
  public HashMap<String, Object> deleteWorkingHours(@RequestBody WorkingHours workingHours) {
    HashMap<String, Object> map = new HashMap<>();
    try {
    	workingHoursService.findWorkingHoursByEmployeeIdAndDate(workingHours.getEmployeeId(), workingHours.getDate());
    	workingHoursService.deleteWorkingHoursByEmployeeIdAndDate(workingHours.getEmployeeId(), workingHours.getDate());
    	
    	map.putAll(responsehelper.responseSimpleWasOk("true", 
    			"Working Hours with Employee id: " + workingHours.getEmployeeId() + " and date " + workingHours.getDate() + " have been deleted"));
    	
    } catch (Exception e) {
    	map.putAll(responsehelper.responsewaswrong("false", e.getMessage()));
    }
    return map;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping()
  public HashMap<String, Object> updateEmployee(@RequestBody WorkingHoursDTO workingHoursDTO){
    HashMap<String, Object> map = new HashMap<String, Object>();
    try {
      WorkingHoursDTO workingHoursUpdated = workingHoursService.updateWorkingHoursByEmployeeIdAndDate(workingHoursDTO);
      
      map.putAll(responsehelper.responseWasOkWithEntity(
    		  "true", "Working Hours with Empployee id: " + workingHoursDTO.getEmployeeId() + " and " + workingHoursDTO.getDate() + " have been updated", 
    		  "employee", workingHoursUpdated));
      

    } catch (Exception e) {
    	map.putAll(responsehelper.responsewaswrong("false", e.getMessage()));
    }
    return map;
  }
}

