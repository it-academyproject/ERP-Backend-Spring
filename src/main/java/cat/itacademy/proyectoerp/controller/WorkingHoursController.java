package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.Product;
import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;
import cat.itacademy.proyectoerp.service.WorkingHoursServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/workinghours")
public class WorkingHoursController {

  @Autowired
  WorkingHoursServiceImpl workingHoursService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping()
  public HashMap<String, Object> getWorkingHours(){
    HashMap<String, Object> map = new HashMap<String, Object>();
    try {
      List<WorkingHoursDTO> workingHoursList = workingHoursService.findAllWorkingHours();
      map.put("success", "true");
      map.put("message", "working hours found");
      map.put("workingHours", workingHoursList);
    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", e.getMessage());
    }
    return map;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public HashMap<String, Object> getWorkingHoursById(@PathVariable(name="id") UUID id){
    HashMap<String, Object> map = new HashMap<>();
    try {
      WorkingHours workingHours = workingHoursService.findWorkingHoursById(id);
      map.put("success", "true");
      map.put("message", "working hours found");
      map.put("workingHours", workingHours);
    } catch (Exception e){
      map.put("success", "false");
      map.put("message", e.getMessage());
    }
    return map;
  }
  
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping()
	public Map<String, Object> createWorkingHours(@RequestBody WorkingHoursDTO workingHoursDTO) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			workingHoursService.createWorkingHours(workingHoursDTO);

			map.put("success", "true");
			map.put("message", "New Working Hours created");
			map.put("workingHours", workingHoursDTO);

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}

		return map;
	}

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping()
  public HashMap<String, Object> deleteWorkingHours(@RequestBody WorkingHours workingHours) {
    HashMap<String, Object> map = new HashMap<>();
    try {
    	workingHoursService.findWorkingHoursById(workingHours.getId());
    	workingHoursService.deleteWorkingHours(workingHours.getId());
    	map.put("success", "true");
    	map.put("message", "Working Hours with id: " + workingHours.getId() + " have been deleted");
    } catch (Exception e) {
    	map.put("success", "false");
    	map.put("message", e.getMessage());
    }
    return map;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping()
  public HashMap<String, Object> updateEmployee(@RequestBody WorkingHours workingHours){
    HashMap<String, Object> map = new HashMap<String, Object>();
    try {
      WorkingHours workingHoursUpdated = workingHoursService.updateWorkingHours(workingHours);
      map.put("success", "true");
      map.put("message", "Working Hours with id: " + workingHours.getId() + " have been updated");
      map.put("employee", workingHoursUpdated);

    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", e.getMessage());
    }
    return map;
  }
}

