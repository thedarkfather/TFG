package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Evaluation extends DomainEntity{
	
	private Boolean type;

	public Boolean getType() {
		return type;
	}

	public void setType(Boolean type) {
		this.type = type;
	}
	
	//Relaciones
	private User user;
	private Comment comment;

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
	

}
