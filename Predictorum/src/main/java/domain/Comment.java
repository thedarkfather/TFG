package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity{
	
	private String text;
	private Integer posPoints;
	private Integer negPoints;
	
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
	
	private Collection<Comment> parents;
	private Collection<Comment> children;
	private Collection<Evaluation> evaluations;
	private Prediction prediction;
	private User user;
	
	@Valid
	@NotNull
	@ManyToMany
	public Collection<Comment> getParents() {
		return parents;
	}
	public void setParents(Collection<Comment> parents) {
		this.parents = parents;
	}
	
	@Valid
	@NotNull
	@ManyToMany(mappedBy="parents")
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
