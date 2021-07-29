package cat.itacademy.proyectoerp.exceptions;

public class ArgumentNotFoundException extends RuntimeException {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor without parameters
	 */
	public ArgumentNotFoundException() {
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param message message that will appear when throwing the exception
	 */
	public ArgumentNotFoundException(String message) {
		super(message);
	}

}
