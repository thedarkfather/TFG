package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Prediction extends DomainEntity{
	//los atributos pueden ser null porque el usuario no tiene porqu� poner todos los valores de una predicci�n
	private String simpleResult;
	private Double pSimpleResult;
	private String doubleResult;
	private Double pDoubleResult;
	private Integer homeGoals;
	private Double pHomeGoals;
	private Integer awayGoals;
	private Double pAwayGoals;
	private Boolean moreThan25;
	private Double pMoreThan25;
	
	public String getSimpleResult() {
		return simpleResult;
	}

	public void setSimpleResult(String simpleResult) {
		this.simpleResult = simpleResult;
	}
	
	@Range(min=0,max=1)
	@NotNull
	public Double getpSimpleResult() {
		return pSimpleResult;
	}
	
	public void setpSimpleResult(Double pSimpleResult) {
		this.pSimpleResult = pSimpleResult;
	}
	
	public String getDoubleResult() {
		return doubleResult;
	}
	
	public void setDoubleResult(String doubleResult) {
		this.doubleResult = doubleResult;
	}
	
	@Range(min=0,max=1)
	@NotNull
	public Double getpDoubleResult() {
		return pDoubleResult;
	}
	
	public void setpDoubleResult(Double pDoubleResult) {
		this.pDoubleResult = pDoubleResult;
	}
	
	@Range(min=0)
	public Integer getHomeGoals() {
		return homeGoals;
	}
	
	public void setHomeGoals(Integer homeGoals) {
		this.homeGoals = homeGoals;
	}
	
	@Range(min=0,max=1)
	@NotNull
	public Double getpHomeGoals() {
		return pHomeGoals;
	}
	
	public void setpHomeGoals(Double pHomeGoals) {
		this.pHomeGoals = pHomeGoals;
	}
	
	@Range(min=0)
	public Integer getAwayGoals() {
		return awayGoals;
	}
	
	public void setAwayGoals(Integer awayGoals) {
		this.awayGoals = awayGoals;
	}
	
	@Range(min=0,max=1)
	@NotNull
	public Double getpAwayGoals() {
		return pAwayGoals;
	}
	
	public void setpAwayGoals(Double pAwayGoals) {
		this.pAwayGoals = pAwayGoals;
	}
		
	public Boolean getMoreThan25() {
		return moreThan25;
	}
	
	public void setMoreThan25(Boolean moreThan25) {
		this.moreThan25 = moreThan25;
	}
	
	@Range(min=0,max=1)
	@NotNull
	public Double getpMoreThan25() {
		return pMoreThan25;
	}
	
	public void setpMoreThan25(Double pMoreThan25) {
		this.pMoreThan25 = pMoreThan25;
	}
	
	//Relaciones
	
	private User user;
	private Game game;
	private Collection<Comment>comments;
	
	@Valid
	@ManyToOne(optional = true)
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}

	@Valid
	@NotNull	
	@OneToMany(mappedBy="prediction")
	public Collection<Comment> getComments() {
		return comments;
	}
	
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	
	

}
