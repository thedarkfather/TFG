package domain;



import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;


@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor{
	
	private Integer sRPoints;
	private Integer dRPoints;
	private Integer sHRPoints;
	private Integer dHRPoints;
	private Integer hGPoints;
	private Integer aGPoints;
	private Integer hHGPoints;
	private Integer hAGPoints;
	private Integer mT25Points;
	
	@Range(min=0)
	@NotNull
	public Integer getsRPoints() {
		return sRPoints;
	}
	
	public void setsRPoints(Integer sRPoints) {
		this.sRPoints = sRPoints;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getdRPoints() {
		return dRPoints;
	}
	
	public void setdRPoints(Integer dRPoints) {
		this.dRPoints = dRPoints;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getsHRPoints() {
		return sHRPoints;
	}
	
	public void setsHRPoints(Integer sHRPoints) {
		this.sHRPoints = sHRPoints;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getdHRPoints() {
		return dHRPoints;
	}
	
	public void setdHRPoints(Integer dHRPoints) {
		this.dHRPoints = dHRPoints;
	}
	
	@Range(min=0)
	@NotNull
	public Integer gethGPoints() {
		return hGPoints;
	}
	
	public void sethGPoints(Integer hGPoints) {
		this.hGPoints = hGPoints;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getaGPoints() {
		return aGPoints;
	}
	
	public void setaGPoints(Integer aGPoints) {
		this.aGPoints = aGPoints;
	}
	
	@Range(min=0)
	@NotNull
	public Integer gethHGPoints() {
		return hHGPoints;
	}
	
	public void sethHGPoints(Integer hHGPoints) {
		this.hHGPoints = hHGPoints;
	}
	
	@Range(min=0)
	@NotNull
	public Integer gethAGPoints() {
		return hAGPoints;
	}
	
	public void sethAGPoints(Integer hAGPoints) {
		this.hAGPoints = hAGPoints;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getmT25Points() {
		return mT25Points;
	}
	
	public void setmT25Points(Integer mT25Points) {
		this.mT25Points = mT25Points;
	}
	
	//Relaciones
	private Collection<User> following;
	private Collection<User> followers;
	private Collection<Team> teams;
	private Collection<Comment> comments;
	private Collection<Evaluation> evaluations;
	private Collection<Prediction> predictions;

	@Valid
	@NotNull
	@ManyToMany(mappedBy="followers")
	public Collection<User> getFollowing() {
		return following;
	}
	
	public void setFollowing(Collection<User> following) {
		this.following = following;
	}
	
	@Valid
	@NotNull
	@ManyToMany
	public Collection<User> getFollowers() {
		return followers;
	}
	
	public void setFollowers(Collection<User> followers) {
		this.followers = followers;
	}
	
	@Valid
	@NotNull
	@ManyToMany(mappedBy="users")
	public Collection<Team> getTeams() {
		return teams;
	}
	
	public void setTeams(Collection<Team> teams) {
		this.teams = teams;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="user")
	public Collection<Comment> getComments() {
		return comments;
	}
	
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="user")
	public Collection<Evaluation> getEvaluations() {
		return evaluations;
	}
	
	public void setEvaluations(Collection<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy="user")
	public Collection<Prediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(Collection<Prediction> predictions) {
		this.predictions = predictions;
	}	

}
