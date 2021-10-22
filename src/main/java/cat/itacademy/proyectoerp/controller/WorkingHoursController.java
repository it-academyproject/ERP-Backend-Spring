package cat.itacademy.proyectoerp.controller;

import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.WorkingHoursDTO;
import cat.itacademy.proyectoerp.dto.WorkingHoursToStringDTO;
import cat.itacademy.proyectoerp.service.WorkingHoursServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping()
	public HashMap<String, Object> getWorkingHours() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			List<WorkingHoursToStringDTO> workingHoursList = workingHoursService.findAllWorkingHours();
			map.put("success", "true");
			map.put("message", "working hours found");
			map.put("working_hours", workingHoursList);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", e.getMessage());
		}
		return map;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/employeeid/{employee_id}/date/{date}")
	public HashMap<String, Object> getWorkingHoursByEmployeeIdAndDate(
			@PathVariable(name = "employee_id") UUID employeeId,
			@PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			WorkingHoursToStringDTO workingHours = workingHoursService.findWorkingHoursByEmployeeIdAndDate(employeeId,
					date);
			map.put("success", "true");
			map.put("message", "working hours found");
			map.put("working_hours", workingHours);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", e.getMessage());
		}
		return map;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/employeeid/{employee_id}")
	public HashMap<String, Object> getWorkingHoursByEmployeeId(@PathVariable(name = "employee_id") UUID employeeId) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			List<WorkingHoursToStringDTO> workingHoursList = workingHoursService
					.findWorkingHoursByEmployeeId(employeeId);
			map.put("success", "true");
			map.put("message", "working hours found");
			map.put("working_hours", workingHoursList);
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", e.getMessage());
		}
		return map;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/date/{date}")
	public HashMap<String, Object> getWorkingHoursByDate(
			@PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			List<WorkingHoursToStringDTO> workingHoursList = workingHoursService.findWorkingHoursByDate(date);
			map.put("success", "true");
			map.put("message", "working hours found");
			map.put("working_hours", workingHoursList);
		} catch (Exception e) {
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
			map.put("working_hours", workingHoursDTO);

		} catch (Exception e) {

			map.put("success", "false");
			map.put("message", "Error: " + e.getMessage());
		}

		return map;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	@DeleteMapping()
	public HashMap<String, Object> deleteWorkingHours(@RequestBody WorkingHours workingHours) {
		HashMap<String, Object> map = new HashMap<>();
		try {
			workingHoursService.findWorkingHoursByEmployeeIdAndDate(workingHours.getEmployeeId(),
					workingHours.getDate());
			workingHoursService.deleteWorkingHoursByEmployeeIdAndDate(workingHours.getEmployeeId(),
					workingHours.getDate());
			map.put("success", "true");
			map.put("message", "Working Hours with Employee id: " + workingHours.getEmployeeId() + " and date "
					+ workingHours.getDate() + " have been deleted");
		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", e.getMessage());
		}
		return map;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping()
	public HashMap<String, Object> updateEmployee(@RequestBody WorkingHoursDTO workingHoursDTO) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			WorkingHoursDTO workingHoursUpdated = workingHoursService
					.updateWorkingHoursByEmployeeIdAndDate(workingHoursDTO);
			map.put("success", "true");
			map.put("message", "Working Hours with Empployee id: " + workingHoursDTO.getEmployeeId() + " and "
					+ workingHoursDTO.getDate() + " have been updated");
			map.put("working_hours", workingHoursUpdated);

		} catch (Exception e) {
			map.put("success", "false");
			map.put("message", e.getMessage());
		}
		return map;
	}

	/**
	 * @param LocalDate
	 * @return String
	 */
	public static String localDateToString(LocalDate localDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String strDate = localDate.format(formatter);

		return strDate;
	}

	/**
	 * @param LocalTime
	 * @return String
	 */
	public static String localTimeToString(LocalTime localTime) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String strDate = localTime.format(formatter);

		return strDate;
	}

}
