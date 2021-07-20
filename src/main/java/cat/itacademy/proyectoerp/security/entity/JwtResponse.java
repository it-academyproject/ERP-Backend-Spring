package cat.itacademy.proyectoerp.security.entity;

import java.io.Serializable;
import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JwtResponse implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1138581471978379303L;
	private String token;
    private String userName;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String token, String nombreUsuario, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.userName = nombreUsuario;
        this.authorities = authorities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
