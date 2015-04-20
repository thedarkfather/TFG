package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Result extends DomainEntity {
	
	private Integer homeGoals;
	private Integer awayGoals;
	private Integer halfHomeGoals;
	private Integer halfAwayGoals;
	
	@Range(min=0)
	@NotNull
	public Integer getHomeGoals() {
		return homeGoals;
	}
	
	public void setHomeGoals(Integer homeGoals) {
		this.homeGoals = homeGoals;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getAwayGoals() {
		return awayGoals;
	}
	
	public void setAwayGoals(Integer awayGoals) {
		this.awayGoals = awayGoals;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getHalfHomeGoals() {
		return halfHomeGoals;
	}
	
	public void setHalfHomeGoals(Integer halfHomeGoals) {
		this.halfHomeGoals = halfHomeGoals;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getHalfAwayGoals() {
		return halfAwayGoals;
	}
	public void setHalfAwayGoals(Integer halfAwayGoals) {
		this.halfAwayGoals = halfAwayGoals;
	}
	
	//Relaciones
	private Game game;
	
	@Valid
	@NotNull
	@OneToOne
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}	

}
