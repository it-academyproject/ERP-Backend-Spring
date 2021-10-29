package cat.itacademy.proyectoerp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.domain.Employee;
import cat.itacademy.proyectoerp.domain.Notification;
import cat.itacademy.proyectoerp.domain.NotificationType;
import cat.itacademy.proyectoerp.domain.WorkingHours;
import cat.itacademy.proyectoerp.dto.WorkingHoursToStringDTO;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.exceptions.ArgumentNotValidException;
import cat.itacademy.proyectoerp.repository.IEmployeeRepository;
import cat.itacademy.proyectoerp.repository.IWorkingHoursRepository;
import cat.itacademy.proyectoerp.util.NotificationBuilder;

@Service
public class WorkingHoursServiceImpl implements IWorkingHoursService {

	private static final String String = null;

	@Autowired
	IWorkingHoursRepository workingHoursRepository;

	@Autowired
	IEmployeeRepository employeeRepository;

	@Autowired
	INotificationService notificationService;

	@Autowired
	IWorkingHoursMapper iWorkingHoursMapper;
	ModelMapper modelMapper = new ModelMapper();

	@Autowired
	IEmailService emailService;

	@Override
	public WorkingHoursToStringDTO createWorkingHours(WorkingHoursToStringDTO dto) throws ArgumentNotValidException {

		WorkingHours entity = iWorkingHoursMapper.workingHoursStringDTOToEntity(dto);

		if (dto.getEmployeeId() == null) {
			throw new ArgumentNotValidException("An Employee ID is required");

		} else if ((dto.getCheckIn() == null) && (dto.getCheckOut() == null)) {
			throw new ArgumentNotValidException("Either the CheckIn time or Checkout time is required");

		} else if (dto.getDate() == null) {
			throw new ArgumentNotValidException("A Date is required");

		} else {

			workingHoursRepository.save(entity);

			// Notify all admins
			Optional<Employee> employee = employeeRepository.findById(entity.getEmployeeId());
			Notification notification = NotificationBuilder.build(NotificationType.EMPLOYEE_ENTRY, employee.get());
			notificationService.notifyAllAdmins(notification);
			// Mailing notification to admins
			emailService.sendEmployeeCheckin(notification);

		}

		return iWorkingHoursMapper.workingHoursToAllDto(entity);
	}

	@Override
	public WorkingHoursToStringDTO updateWorkingHoursByEmployeeIdAndDate(WorkingHoursToStringDTO dto)
			throws ArgumentNotValidException {

		WorkingHours entity = iWorkingHoursMapper.workingHoursStringDTOToEntity(dto);

		if (workingHoursRepository.findWorkingHoursByEmployeeIdAndDate(entity.getEmployeeId(),
				entity.getDate()) == null) {
			throw new ArgumentNotFoundException("No WorkingHours found for this Employee Id and date");

		} else {
			workingHoursRepository.save(entity);
		}

		return iWorkingHoursMapper.workingHoursToAllDto(entity);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public List<WorkingHoursToStringDTO> findAllWorkingHours() {

		if (workingHoursRepository.findAll().isEmpty()) {
			throw new ArgumentNotFoundException("No Working Hours found");
		} else {
			return iWorkingHoursMapper.workingHoursToAllDtos(workingHoursRepository.findAll());
		}
	}

	@Override
	public List<WorkingHoursToStringDTO> findWorkingHoursByEmployeeId(UUID employeeId) {

		if (workingHoursRepository.findWorkingHoursByEmployeeId(employeeId).isEmpty()) {
			throw new ArgumentNotFoundException("No Working Hours found for this Employee Id: " + employeeId);
		} else {
			return iWorkingHoursMapper
					.workingHoursToAllDtos(workingHoursRepository.findWorkingHoursByEmployeeId(employeeId));
		}
	}

	@Override
	public List<WorkingHoursToStringDTO> findWorkingHoursByDate(LocalDate date) {

		if (workingHoursRepository.findWorkingHoursByDate(date).isEmpty()) {
			throw new ArgumentNotFoundException("No Working Hours found for date: " + date);
		} else {
			return iWorkingHoursMapper.workingHoursToAllDtos(workingHoursRepository.findWorkingHoursByDate(date));
		}
	}

	@Override
	public WorkingHoursToStringDTO findWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date) {

		if (workingHoursRepository.findWorkingHoursByEmployeeIdAndDate(employeeId, date) == null) {
			throw new ArgumentNotFoundException("No WorkingHours found for this Employee Id and date");
		} else {

			return iWorkingHoursMapper
					.workingHoursToAllDto(workingHoursRepository.findWorkingHoursByEmployeeIdAndDate(employeeId, date));
		}
	}

	@Override
	public void deleteWorkingHoursByEmployeeIdAndDate(UUID employeeId, LocalDate date) {
		if (workingHoursRepository.findWorkingHoursByEmployeeIdAndDate(employeeId, date) == null) {
			throw new ArgumentNotFoundException("No WorkingHours found for this Employee Id and date");

		} else {
			workingHoursRepository.deleteWorkingHoursByEmployeeIdAndDate(employeeId, date);
		}

	}

}