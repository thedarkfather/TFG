package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Diary extends DomainEntity{
	
	private Integer startYearSeason;
	private String team1;
	private Integer homeWonMatches1;
	private Integer awayWonMatches1;
	private Integer homeLostMatches1;
	private Integer awayLostMatches1;
	private Integer totalHomeGoals1;
	private Integer totalAwayGoals1;
	private Integer totalMoreThan251;
	private Integer points1;
	private Integer totalGames1;
	
	private String 	team2;
	private Integer homeWonMatches2;
	private Integer awayWonMatches2;
	private Integer homeLostMatches2;
	private Integer awayLostMatches2;
	private Integer totalHomeGoals2;
	private Integer totalAwayGoals2;
	private Integer totalMoreThan252;
	private Integer points2;
	private Integer totalGames2;
	
	private String winner;	
	private Integer homeGoals;
	private Integer awayGoals;
	private String moreThan25;
	
	public Integer getStartYearSeason() {
		return startYearSeason;
	}
	
	public void setStartYearSeason(Integer startYearSeason) {
		this.startYearSeason = startYearSeason;
	}
	
	public String getTeam1() {
		return team1;
	}
	
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	
	public Integer getHomeWonMatches1() {
		return homeWonMatches1;
	}
	
	public void setHomeWonMatches1(Integer homeWonMatches1) {
		this.homeWonMatches1 = homeWonMatches1;
	}
	
	public Integer getAwayWonMatches1() {
		return awayWonMatches1;
	}
	
	public void setAwayWonMatches1(Integer awayWonMatches1) {
		this.awayWonMatches1 = awayWonMatches1;
	}
	
	public Integer getHomeLostMatches1() {
		return homeLostMatches1;
	}
	
	public void setHomeLostMatches1(Integer homeLostMatches1) {
		this.homeLostMatches1 = homeLostMatches1;
	}
	
	public Integer getAwayLostMatches1() {
		return awayLostMatches1;
	}
	
	public void setAwayLostMatches1(Integer awayLostMatches1) {
		this.awayLostMatches1 = awayLostMatches1;
	}
	
	public Integer getTotalHomeGoals1() {
		return totalHomeGoals1;
	}
	
	public void setTotalHomeGoals1(Integer totalHomeGoals1) {
		this.totalHomeGoals1 = totalHomeGoals1;
	}
	
	public Integer getTotalAwayGoals1() {
		return totalAwayGoals1;
	}
	
	public void setTotalAwayGoals1(Integer totalAwayGoals1) {
		this.totalAwayGoals1 = totalAwayGoals1;
	}
	
	public Integer getTotalMoreThan251() {
		return totalMoreThan251;
	}
	
	public void setTotalMoreThan251(Integer totalMoreThan251) {
		this.totalMoreThan251 = totalMoreThan251;
	}
	
	public Integer getPoints1() {
		return points1;
	}
	
	public void setPoints1(Integer points1) {
		this.points1 = points1;
	}
	
	public Integer getTotalGames1() {
		return totalGames1;
	}
	
	public void setTotalGames1(Integer totalGames1) {
		this.totalGames1 = totalGames1;
	}
	
	public String getTeam2() {
		return team2;
	}
	
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
	
	public Integer getHomeWonMatches2() {
		return homeWonMatches2;
	}
	
	public void setHomeWonMatches2(Integer homeWonMatches2) {
		this.homeWonMatches2 = homeWonMatches2;
	}
	
	public Integer getAwayWonMatches2() {
		return awayWonMatches2;
	}
	
	public void setAwayWonMatches2(Integer awayWonMatches2) {
		this.awayWonMatches2 = awayWonMatches2;
	}
	
	public Integer getHomeLostMatches2() {
		return homeLostMatches2;
	}
	
	public void setHomeLostMatches2(Integer homeLostMatches2) {
		this.homeLostMatches2 = homeLostMatches2;
	}
	
	public Integer getAwayLostMatches2() {
		return awayLostMatches2;
	}
	
	public void setAwayLostMatches2(Integer awayLostMatches2) {
		this.awayLostMatches2 = awayLostMatches2;
	}
	
	public Integer getTotalHomeGoals2() {
		return totalHomeGoals2;
	}
	
	public void setTotalHomeGoals2(Integer totalHomeGoals2) {
		this.totalHomeGoals2 = totalHomeGoals2;
	}
	
	public Integer getTotalAwayGoals2() {
		return totalAwayGoals2;
	}
	
	public void setTotalAwayGoals2(Integer totalAwayGoals2) {
		this.totalAwayGoals2 = totalAwayGoals2;
	}
	
	public Integer getTotalMoreThan252() {
		return totalMoreThan252;
	}
	
	public void setTotalMoreThan252(Integer totalMoreThan252) {
		this.totalMoreThan252 = totalMoreThan252;
	}
	
	public Integer getPoints2() {
		return points2;
	}
	
	public void setPoints2(Integer points2) {
		this.points2 = points2;
	}
	
	public Integer getTotalGames2() {
		return totalGames2;
	}
	
	public void setTotalGames2(Integer totalGames2) {
		this.totalGames2 = totalGames2;
	}
	
	public String getWinner() {
		return winner;
	}
	
	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	public Integer getHomeGoals() {
		return homeGoals;
	}
	
	public void setHomeGoals(Integer homeGoals) {
		this.homeGoals = homeGoals;
	}
	
	public Integer getAwayGoals() {
		return awayGoals;
	}
	
	public void setAwayGoals(Integer awayGoals) {
		this.awayGoals = awayGoals;
	}
	
	public String getMoreThan25() {
		return moreThan25;
	}
	
	public void setMoreThan25(String moreThan25) {
		this.moreThan25 = moreThan25;
	}
	
	
	
}
