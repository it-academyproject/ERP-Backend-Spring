package cat.itacademy.proyectoerp.exceptions;

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
