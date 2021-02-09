package cat.itacademy.proyectoerp.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.proyectoerp.security.entity.PasswordResetToken;

public interface IPasswordTokenDAO extends JpaRepository<PasswordResetToken, Long> {

	// method to search by token
	public PasswordResetToken findByToken(String token);
}
