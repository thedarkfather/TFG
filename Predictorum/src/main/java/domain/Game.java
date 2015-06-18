package domain;

import java.util.Collection;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class Game extends DomainEntity {
	
	

	//Relaciones
	private Result result;
	private Collection<Prediction> predictions;
	private Team homeTeam;
	private Team awayTeam;
	private Season season;

	@Valid
	@OneToOne(mappedBy="game")
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	
	@NotNull
	@Valid
	@OneToMany(mappedBy="game")
	public Collection<Prediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(Collection<Prediction> predictions) {
		this.predictions = predictions;
	}

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}
	
	
	
}
