package cat.itacademy.proyectoerp.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import cat.itacademy.proyectoerp.domain.User;
import cat.itacademy.proyectoerp.domain.UserType;

public class UserNoPasswordDTO {
	  private Long id;
	  private String username;
	  
	  private UserType userType;
	  
	  public UserNoPasswordDTO(User user) {
		  this.id = user.getId();
		  this.username = user.getUsername();
		  this.userType = user.getUserType();
	  }
	  
	  public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public UserType getUserType() {
			return userType;
		}

		public void setUserType(UserType userType) {
			this.userType = userType;
		}




}
