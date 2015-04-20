package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class TeamStatistics extends DomainEntity{
	
	private String streak;
	private Integer wonMatchPercentage;
	private Integer lostMatchPercentage;
	private Integer drawMatchPercentage;
	private Integer moreThan25Percentage;
	private Integer wonHalfMatchPercentage;
	private Integer lostHalfMatchPercentage;
	private Integer drawHalfMatchPercentage;
	private Integer homeWonMatches;
	private Integer awayWonMatches;
	private Integer homeGoals;
	private Integer awayGoals;
	private Integer leaguePosition;
	private Integer points;
	
	@NotBlank
	public String getStreak() {
		return streak;
	}
	
	public void setStreak(String streak) {
		this.streak = streak;
	}
	
	@Range(min=0,max=100)
	@NotNull
	public Integer getWonMatchPercentage() {
		return wonMatchPercentage;
	}
	
	public void setWonMatchPercentage(Integer wonMatchPercentage) {
		this.wonMatchPercentage = wonMatchPercentage;
	}
	
	@Range(min=0,max=100)
	@NotNull
	public Integer getLostMatchPercentage() {
		return lostMatchPercentage;
	}
	
	public void setLostMatchPercentage(Integer lostMatchPercentage) {
		this.lostMatchPercentage = lostMatchPercentage;
	}
		
	@Range(min=0,max=100)
	@NotNull
	public Integer getDrawMatchPercentage() {
		return drawMatchPercentage;
	}
	
	public void setDrawMatchPercentage(Integer drawMatchPercentage) {
		this.drawMatchPercentage = drawMatchPercentage;
	}
	
	@Range(min=0,max=100)
	@NotNull
	public Integer getMoreThan25Percentage() {
		return moreThan25Percentage;
	}
	
	public void setMoreThan25Percentage(Integer moreThan25Percentage) {
		this.moreThan25Percentage = moreThan25Percentage;
	}
	
	@Range(min=0,max=100)
	@NotNull
	public Integer getWonHalfMatchPercentage() {
		return wonHalfMatchPercentage;
	}
	
	public void setWonHalfMatchPercentage(Integer wonHalfMatchPercentage) {
		this.wonHalfMatchPercentage = wonHalfMatchPercentage;
	}
	
	@Range(min=0,max=100)
	@NotNull
	public Integer getLostHalfMatchPercentage() {
		return lostHalfMatchPercentage;
	}
	
	public void setLostHalfMatchPercentage(Integer lostHalfMatchPercentage) {
		this.lostHalfMatchPercentage = lostHalfMatchPercentage;
	}
	
	@Range(min=0,max=100)
	@NotNull
	public Integer getDrawHalfMatchPercentage() {
		return drawHalfMatchPercentage;
	}
	
	public void setDrawHalfMatchPercentage(Integer drawHalfMatchPercentage) {
		this.drawHalfMatchPercentage = drawHalfMatchPercentage;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getHomeWonMatches() {
		return homeWonMatches;
	}
	
	public void setHomeWonMatches(Integer homeWonMatches) {
		this.homeWonMatches = homeWonMatches;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getAwayWonMatches() {
		return awayWonMatches;
	}
	
	public void setAwayWonMatches(Integer awayWonMatches) {
		this.awayWonMatches = awayWonMatches;
	}
	
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
	
	@Range(min=1)
	@NotNull
	public Integer getLeaguePosition() {
		return leaguePosition;
	}
	
	public void setLeaguePosition(Integer leaguePosition) {
		this.leaguePosition = leaguePosition;
	}
	
	@Range(min=0)
	@NotNull
	public Integer getPoints() {
		return points;
	}
	
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	//Relaciones
	
	private Team team;
	
	@Valid
	@NotNull
	@OneToOne
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
	

}
