package cat.itacademy.proyectoerp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ArgumentNotValidException extends RuntimeException {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor without parameters
	 */
	public ArgumentNotValidException() {

	}

	/**
	 * Constructor with parameters
	 * 
	 * @param message message that will appear when throwing the exception
	 */
	public ArgumentNotValidException(String message) {

		super(message);
	}

}
