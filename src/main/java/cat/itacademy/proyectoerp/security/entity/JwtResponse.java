package cat.itacademy.proyectoerp.security.entity;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5163349182982321779L;
	
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}

