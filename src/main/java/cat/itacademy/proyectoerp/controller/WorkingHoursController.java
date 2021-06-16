package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;
import cat.itacademy.proyectoerp.service.IWorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/workinghours")
public class WorkingHoursController {

  @Autowired
  IWorkingHoursService iWorkingHoursService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping()
  public Map<String, Object> getWorkingHours(){
    HashMap<String, Object> map = new HashMap<String, Object>();
    try {
      List<WorkingHoursDTO> workingHoursList = iWorkingHoursService.findAllWorkingHours();
      map.put("success", "true");
      map.put("message", "WorkingHours found");
      map.put("workingHours", workingHoursList);
    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", e.getMessage());
    }
    return map;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public Map<String, Object> getWorkingHoursById(@PathVariable(name="id") UUID id){
    HashMap<String, Object> map = new HashMap<>();
    try {
      WorkingHoursDTO workingHoursDTO = iWorkingHoursService.findWorkingHoursById(id);
      map.put("success", "true");
      map.put("message", "WorkingHours found");
      map.put("workingHours", workingHoursDTO);
    } catch (Exception e){
      map.put("success", "false");
      map.put("message", e.getMessage());
    }
    return map;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping()
  public Map<String, Object> deleteWorkingHours(@RequestBody ObjectNode objectNode) {
	  UUID id =UUID.fromString( objectNode.get("id").asText());
    HashMap<String, Object> map = new HashMap<>();
    try {
      iWorkingHoursService.deleteWorkingHours(id);
      map.put("success", "true");
      map.put("message", "WorkingHours with id: " + id + " have been deleted");
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
      WorkingHoursDTO workingHoursUpdated = iWorkingHoursService.updateWorkingHours(workingHours);
      map.put("success", "true");
      map.put("message", "WorkingHours with id: " + workingHours.getId() + " have been updated");
      map.put("workingHours", workingHoursUpdated);

    } catch (Exception e) {
      map.put("success", "false");
      map.put("message", e.getMessage());
    }
    return map;
  }
}
