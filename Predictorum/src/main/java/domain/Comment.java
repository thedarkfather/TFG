package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity{
	
	private String text;
	private Integer posPoints;
	private Integer negPoints;
	private Date date;	
	
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@NotBlank
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getPosPoints() {
		return posPoints;
	}
	
	public void setPosPoints(Integer posPoints) {
		this.posPoints = posPoints;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getNegPoints() {
		return negPoints;
	}
	
	public void setNegPoints(Integer negPoints) {
		this.negPoints = negPoints;
	}
	
	//Relaciones
	
	private Comment parent;
	private Collection<Comment> children;
	private Collection<Evaluation> evaluations;
	private Prediction prediction;
	private User user;
	
	@Valid
	@ManyToOne(optional=true)
	public Comment getParent() {
		return parent;
	}

	public void setParent(Comment parent) {
		this.parent = parent;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy="parent")
	public Collection<Comment> getChildren() {
		return children;
	}
	public void setChildren(Collection<Comment> children) {
		this.children = children;
	}
	
	@NotNull
	@Valid
	@OneToMany(mappedBy="comment")
	public Collection<Evaluation> getEvaluations() {
		return evaluations;
	}
	
	public void setEvaluations(Collection<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Prediction getPrediction() {
		return prediction;
	}
	public void setPrediction(Prediction prediction) {
		this.prediction = prediction;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
