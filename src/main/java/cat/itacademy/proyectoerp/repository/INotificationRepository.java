package cat.itacademy.proyectoerp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.proyectoerp.domain.Notification;
import cat.itacademy.proyectoerp.domain.User;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, UUID>{

	public List<Notification> findByUser(User user);
	
}
