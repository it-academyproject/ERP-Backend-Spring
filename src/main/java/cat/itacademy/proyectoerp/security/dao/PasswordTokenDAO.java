package cat.itacademy.proyectoerp.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.proyectoerp.security.entity.PasswordResetToken;

public interface PasswordTokenDAO extends JpaRepository<PasswordResetToken, Long> {

}
