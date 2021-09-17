package cat.itacademy.proyectoerp.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategoryDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String name;
	private String description;
	private OfferDTO offerDto;
	
	public CategoryDTO() {
	}
	
	public CategoryDTO(String name, String description, OfferDTO offerDto) {
		this.name = name;
		this.description = description;
		this.offerDto = offerDto;
	}
	
	public CategoryDTO(UUID id, String name, String description, OfferDTO offerDto) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.offerDto = offerDto;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setOfferDto(OfferDTO offerDto) {
		this.offerDto = offerDto;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public OfferDTO getOfferDto() {
		return offerDto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryDTO other = (CategoryDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
