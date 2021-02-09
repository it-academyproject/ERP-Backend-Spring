package cat.itacademy.proyectoerp.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.proyectoerp.exceptions.ArgumentNotFoundException;
import cat.itacademy.proyectoerp.security.dao.IPasswordTokenDAO;
import cat.itacademy.proyectoerp.security.entity.PasswordResetToken;

@Service
public class PasswordResetTokenServiceImpl {

	@Autowired
	IPasswordTokenDAO passwordTokenDao;

	/**
	 * Method to verify if the token exist in ddbb
	 * 
	 * @param token
	 * @return token
	 * @throws ArgumentNotFoundException
	 */
	public PasswordResetToken findByToken(String token) throws ArgumentNotFoundException {
		
		PasswordResetToken newToken = passwordTokenDao.findByToken(token);
		if (newToken == null) {
			throw new ArgumentNotFoundException("No token found");
		}

		return newToken;
	}
}
