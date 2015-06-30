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
public class TeamStatistics extends DomainEntity{
	
	private String streak; //PUESTO
	private Integer wonMatchPercentage;//PUESTO
	private Integer lostMatchPercentage;//PUESTO
	private Integer drawMatchPercentage;//PUESTO
	private Integer moreThan25Percentage;//PUESTO
	private Integer moreThan25; //PUESTO
	//private Integer wonHalfMatchPercentage;
	//private Integer lostHalfMatchPercentage;
	//private Integer drawHalfMatchPercentage;
	private Integer homeWonMatches; //PUESTO
	private Integer awayWonMatches; //PUESTO
	private Integer homeLostMatches; //PUESTO
	private Integer awayLostMatches; //PUESTO
	private Integer homeGoals; //PUESTO
	private Integer awayGoals; //PUESTO
	private Integer leaguePosition;
	private Integer points; //PUESTO
	private Integer totalGames; //PUESTO
	
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
	
	
	
//	@Range(min=0,max=100)
//	@NotNull
//	public Integer getWonHalfMatchPercentage() {
//		return wonHalfMatchPercentage;
//	}
//	
//	public void setWonHalfMatchPercentage(Integer wonHalfMatchPercentage) {
//		this.wonHalfMatchPercentage = wonHalfMatchPercentage;
//	}
//	
//	@Range(min=0,max=100)
//	@NotNull
//	public Integer getLostHalfMatchPercentage() {
//		return lostHalfMatchPercentage;
//	}
//	
//	public void setLostHalfMatchPercentage(Integer lostHalfMatchPercentage) {
//		this.lostHalfMatchPercentage = lostHalfMatchPercentage;
//	}
//	
//	@Range(min=0,max=100)
//	@NotNull
//	public Integer getDrawHalfMatchPercentage() {
//		return drawHalfMatchPercentage;
//	}
//	
//	public void setDrawHalfMatchPercentage(Integer drawHalfMatchPercentage) {
//		this.drawHalfMatchPercentage = drawHalfMatchPercentage;
//	}
	
	@Range(min=0)
	@NotNull
	public Integer getMoreThan25() {
		return moreThan25;
	}

	public void setMoreThan25(Integer moreThan25) {
		this.moreThan25 = moreThan25;
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
	public Integer getHomeLostMatches() {
		return homeLostMatches;
	}

	public void setHomeLostMatches(Integer homeLostMatches) {
		this.homeLostMatches = homeLostMatches;
	}

	@Range(min=0)
	@NotNull
	public Integer getAwayLostMatches() {
		return awayLostMatches;
	}

	public void setAwayLostMatches(Integer awayLostMatches) {
		this.awayLostMatches = awayLostMatches;
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
	
	//Será 0 si la liga no ha empezado
	@Range(min=0)
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
	
	@Range(min=0)
	@NotNull
	public Integer getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(Integer totalGames) {
		this.totalGames = totalGames;
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
