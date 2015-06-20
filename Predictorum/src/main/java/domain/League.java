package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class League extends DomainEntity{
	
	private String name;	
	
	@NotBlank
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	
	
	//Relaciones	
	private Collection<Season> seasons;	

	@Valid
	@NotNull
	@OneToMany(mappedBy="league")
	public Collection<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(Collection<Season> seasons) {
		this.seasons = seasons;
	}	

}
