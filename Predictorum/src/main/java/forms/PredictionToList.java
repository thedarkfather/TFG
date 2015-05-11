package forms;

import java.util.Date;



public class PredictionToList {

	private int predictionId;
	private int leagueId;
	private String leagueName;
	private int homeTeamId;
	private String homeTeamName;
	private int awayTeamId;
	private String awayTeamName;
	private Date gameDate;
	private String predictionResult;
	private Double probability;

	public int getPredictionId() {
		return predictionId;
	}

	public void setPredictionId(int predictionId) {
		this.predictionId = predictionId;
	}

	public int getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public int getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(int homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public int getAwayTeamId() {
		return awayTeamId;
	}

	public void setAwayTeamId(int awayTeamId) {
		this.awayTeamId = awayTeamId;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date date) {
		this.gameDate = date;
	}

	public String getPredictionResult() {
		return predictionResult;
	}

	public void setPredictionResult(String predictionResult) {
		this.predictionResult = predictionResult;
	}

	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}

}
