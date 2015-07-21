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
	
	private Integer sRPointsPossible;
	private Integer dRPointsPossible;
	private Integer sHRPointsPossible;
	private Integer dHRPointsPossible;
	private Integer hGPointsPossible;
	private Integer aGPointsPossible;
	private Integer hHGPointsPossible;
	private Integer hAGPointsPossible;
	private Integer mT25PointsPossible;
	
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
	
	@Range(min=0)
	@NotNull
	public Integer getsRPointsPossible() {
		return sRPointsPossible;
	}

	public void setsRPointsPossible(Integer sRPointsPossible) {
		this.sRPointsPossible = sRPointsPossible;
	}

	@Range(min=0)
	@NotNull
	public Integer getdRPointsPossible() {
		return dRPointsPossible;
	}

	public void setdRPointsPossible(Integer dRPointsPossible) {
		this.dRPointsPossible = dRPointsPossible;
	}

	@Range(min=0)
	@NotNull
	public Integer getsHRPointsPossible() {
		return sHRPointsPossible;
	}

	public void setsHRPointsPossible(Integer sHRPointsPossible) {
		this.sHRPointsPossible = sHRPointsPossible;
	}

	@Range(min=0)
	@NotNull
	public Integer getdHRPointsPossible() {
		return dHRPointsPossible;
	}

	public void setdHRPointsPossible(Integer dHRPointsPossible) {
		this.dHRPointsPossible = dHRPointsPossible;
	}

	@Range(min=0)
	@NotNull
	public Integer gethGPointsPossible() {
		return hGPointsPossible;
	}

	public void sethGPointsPossible(Integer hGPointsPossible) {
		this.hGPointsPossible = hGPointsPossible;
	}

	@Range(min=0)
	@NotNull
	public Integer getaGPointsPossible() {
		return aGPointsPossible;
	}

	public void setaGPointsPossible(Integer aGPointsPossible) {
		this.aGPointsPossible = aGPointsPossible;
	}

	@Range(min=0)
	@NotNull
	public Integer gethHGPointsPossible() {
		return hHGPointsPossible;
	}

	public void sethHGPointsPossible(Integer hHGPointsPossible) {
		this.hHGPointsPossible = hHGPointsPossible;
	}

	@Range(min=0)
	@NotNull
	public Integer gethAGPointsPossible() {
		return hAGPointsPossible;
	}

	public void sethAGPointsPossible(Integer hAGPointsPossible) {
		this.hAGPointsPossible = hAGPointsPossible;
	}

	@Range(min=0)
	@NotNull
	public Integer getmT25PointsPossible() {
		return mT25PointsPossible;
	}

	public void setmT25PointsPossible(Integer mT25PointsPossible) {
		this.mT25PointsPossible = mT25PointsPossible;
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
